package korlibs.audio.sound.backend

import com.sun.jna.*
import korlibs.annotations.*
import korlibs.audio.sound.*
import korlibs.audio.sound.backend.JVMCoreAudioNativeSoundProvider.AudioQueueNewOutputCallback
import korlibs.ffi.*
import kotlinx.atomicfu.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong

object JVMCoreAudioNativeSoundProvider : NativeSoundProvider() {
    @ExperimentalStdlibApi
    override fun createNewPlatformAudioOutput(nchannels: Int, freq: Int, gen: AudioPlatformOutputGen): AudioPlatformOutput {
        return AudioPlatformOutput(this, nchannels, freq, gen) {
            val id = lastId.incrementAndGet()

            var queue: Pointer? = null
            val info = AudioInfo(nchannels, { genSafe(it) }, atomic(AudioSamplesInterleaved(nchannels, 0)), false)

            newAudioOutputsById[id] = info
            val queueRef = Memory(16).also { it.clear() }
            val format = AudioStreamBasicDescription(Memory(40).also { it.clear() })

            format.mSampleRate = frequency.toDouble()
            format.mFormatID = CoreAudioKit.kAudioFormatLinearPCM
            format.mFormatFlags = CoreAudioKit.kLinearPCMFormatFlagIsSignedInteger or CoreAudioKit.kAudioFormatFlagIsPacked
            format.mBitsPerChannel = (8 * Short.SIZE_BYTES)
            format.mChannelsPerFrame = channels
            format.mBytesPerFrame = (Short.SIZE_BYTES * format.mChannelsPerFrame)
            format.mFramesPerPacket = 1
            format.mBytesPerPacket = format.mBytesPerFrame * format.mFramesPerPacket
            format.mReserved = 0

            val userDefinedPtr = Pointer(id.toLong())

            CoreAudioKit.AudioQueueNewOutput(
                format.ptr, jnaNewCoreAudioCallback, userDefinedPtr, null, null, 0, queueRef
            ).also {
                if (it != 0) println("CoreAudioKit.AudioQueueNewOutput -> $it")
            }
            queue = queueRef.getPointer(0L)
            //println("result=$result, queue=$queue")
            val buffersArray = Memory((8 * numBuffers).toLong()).also { it.clear() }
            for (buf in 0 until numBuffers) {
                val bufferPtr = Pointer(buffersArray.address + 8 * buf)
                CoreAudioKit.AudioQueueAllocateBuffer(queue, bufferSizeInBytes, bufferPtr).also {
                    if (it != 0) println("CoreAudioKit.AudioQueueAllocateBuffer -> $it")
                }
                val ptr = AudioQueueBuffer(bufferPtr.getPointer(0))
                //println("AudioQueueAllocateBuffer=$res, ptr.pointer=${ptr.pointer}")
                ptr.mAudioDataByteSize = bufferSizeInBytes
                jnaNewCoreAudioCallback.callback(userDefinedPtr, queue, ptr.ptr)
            }
            CoreAudioKit.AudioQueueStart(queue, null).also {
                if (it != 0) println("CoreAudioKit.AudioQueueStart -> $it")
            }

            try {
                suspendWhileRunning()
            } finally {
                info.completed = true

                if (queue != null) {
                    CoreAudioKit.AudioQueueFlush(queue)
                    CoreAudioKit.AudioQueueStop(queue, false)
                    CoreAudioKit.AudioQueueDispose(queue, false)
                    queue = null
                }
                newAudioOutputsById.remove(id)

            }
        }
    }

    class AudioInfo(
        val channels: Int,
        val genSafe: (AudioSamplesInterleaved) -> Unit,
        val buffer: AtomicRef<AudioSamplesInterleaved>,
        var completed: Boolean
    )

    private var lastId = AtomicLong(0L)
    const val bufferSizeInBytes = 2048
    const val numBuffers = 3

    private val newAudioOutputsById = ConcurrentHashMap<Long, AudioInfo>()
    private val jnaNewCoreAudioCallback by lazy {
        AudioQueueNewOutputCallback { inUserData, inAQ, inBuffer ->
            try {
                val output = newAudioOutputsById[(inUserData?.address ?: 0L).toLong()] ?: return@AudioQueueNewOutputCallback 0
                val nchannels = output.channels

                //val tone = AudioTone.generate(1.seconds, 41000.0)
                val queue = AudioQueueBuffer(inBuffer)
                val ptr = queue.mAudioData
                val samplesCount = (queue.mAudioDataByteSize / Short.SIZE_BYTES) / nchannels
                //println("samplesCount=$samplesCount")

                if (ptr != null) {
                    // Reuse instances as much as possible
                    if (output.buffer.value.totalSamples != samplesCount) output.buffer.value = AudioSamplesInterleaved(nchannels, samplesCount)
                    val samples = output.buffer.value
                    output.genSafe(samples)

                    val samplesData = samples.data
                    for (n in 0 until samplesCount * nchannels) {
                        ptr[n] = samplesData[n].short
                    }
                }
                //println("queue.mAudioData=${queue.mAudioData}")

                if (!output.completed) {
                    CoreAudioKit.AudioQueueEnqueueBuffer(inAQ, queue.ptr, 0, null).also {
                        if (it != 0) println("CoreAudioKit.AudioQueueEnqueueBuffer -> $it")
                    }
                } else {
                    Unit
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            0
        }.also {
            Native.setCallbackThreadInitializer(it, CallbackThreadInitializer(false, false))
        }
    }
    private class AudioQueueBuffer(p: FFIPointer? = null) : FFIStructure(p) {
        var mAudioDataBytesCapacity by int()
        var mAudioData by pointer<Short>()
        var mAudioDataByteSize by int()
        var mUserData by pointer<Byte>()
        var mPacketDescriptionCapacity by pointer<Int>()
        //const UInt32                    mPacketDescriptionCapacity;
        //AudioStreamPacketDescription * const __nullable mPacketDescriptions;
        //UInt32                          mPacketDescriptionCount;
    }
//private class AudioTimeStamp(p: Pointer? = null) : KStructure(p) {
//    var mSampleTime by double()
//    var mHostTime by long()
//    var mRateScalar by double()
//    var mWordClockTime by long()
//    //SMPTETime           mSMPTETime;
//    //AudioTimeStampFlags mFlags;
//    //UInt32              mReserved;
//}

    private class AudioStreamBasicDescription(p: FFIPointer? = null) : FFIStructure(p) {
        var mSampleRate by double()
        var mFormatID by int()
        var mFormatFlags by int()
        var mBytesPerPacket by int()
        var mFramesPerPacket by int()
        var mBytesPerFrame by int()
        var mChannelsPerFrame by int()
        var mBitsPerChannel by int()
        var mReserved by int()
    }

//private object CoreAudio {
//    @JvmStatic external fun AudioComponentInstanceNew(): Int
//    init {
//        Native.register("CoreAudio")
//    }
//}

    private fun interface AudioQueueNewOutputCallback : Callback {
        fun callback(inUserData: Pointer?, inAQ: Pointer?, inBuffer: Pointer?): Int
    }

    @Keep
    private object CoreAudioKit {
        @JvmStatic external fun AudioQueueNewOutput(
            inFormat: Pointer?,
            inCallbackProc: Callback?,
            inUserData: Pointer?,
            inCallbackRunLoop: Pointer?,
            inCallbackRunLoopMode: Pointer?,
            inFlags: Int,
            outAQ: Pointer?
        ): Int
        @JvmStatic external fun AudioQueueAllocateBuffer(inAQ: Pointer?, inBufferByteSize: Int, buffer: Pointer?): Int
        @JvmStatic external fun AudioQueueStart(inAQ: Pointer?, inStartTime: Pointer?): Int
        @JvmStatic external fun AudioQueueFlush(inAQ: Pointer?): Int
        @JvmStatic external fun AudioQueueStop(inAQ: Pointer?, immediate: Boolean): Int
        @JvmStatic external fun AudioQueueDispose(inAQ: Pointer?, immediate: Boolean): Int
        @JvmStatic external fun AudioQueueEnqueueBuffer(inAQ: Pointer?, inBuffer: Pointer?, inNumPacketDescs: Int, inPacketDescs: Pointer?): Int

        const val kAudioFormatLinearPCM: Int = 0x6C70636D
        const val kLinearPCMFormatFlagIsSignedInteger: Int = 4
        const val kAudioFormatFlagIsPacked: Int = 8

        init {
            Native.register("CoreAudioKit")
        }
    }
}
