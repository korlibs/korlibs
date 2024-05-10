package korlibs.io.async

import korlibs.io.stream.*
import korlibs.memory.*
import kotlinx.coroutines.channels.*

interface IAsyncRingBuffer : AsyncOutputStream, AsyncInputStream

class AsyncRingBuffer(private val bufferSize: Int = 1024) : IAsyncRingBuffer {
    var name: String? = null
	private val notifyRead = Channel<Unit>(Channel.CONFLATED)
	private val notifyWrite = Channel<Unit>(Channel.CONFLATED)
	private val temp = SimpleBytesDeque(ilog2(bufferSize) + 1)
	private var completed = false

	override suspend fun write(buffer: ByteArray, offset: Int, len: Int) {
		if (len <= 0) return
        if (completed) error("Trying to write to a completed $this")
        //println("$this.write[0]: len=$len")

        notifyRead.receive()

        //println("$this.write[1]")
		temp.write(buffer, offset, len)
        //println("$this.write[2]")
		notifyWrite.send(Unit)
        //println("$this.write[3]")
	}

	override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
		if (len <= 0) return len
        if (temp.availableRead > 0) {
            return temp.read(buffer, offset, len)
        }

		notifyRead.send(Unit)
        //println("$this:read.completed=$completed")
		while (!completed && temp.availableRead == 0) notifyWrite.receive()
		if (completed && temp.availableRead == 0) return -1
		return temp.read(buffer, offset, len)
	}

	override suspend fun close() {
        //println("AsyncByteArrayDeque.close[$this]")
		completed = true
		notifyWrite.send(Unit)
	}

    override fun toString(): String = "AsyncByteArrayDeque($name)"
}

private fun ilog2(v: Int): Int = if (v == 0) (-1) else (31 - v.countLeadingZeroBits())
