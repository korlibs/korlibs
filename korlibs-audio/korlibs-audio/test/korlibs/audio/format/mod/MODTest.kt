package korlibs.audio.format.mod

import korlibs.io.stream.*
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class MODTest {

    @Test
    fun testMODFormatValidation() = runTest {
        ("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxM.K.xxxxx"
        ).encodeToByteArray().openAsync().also { stream ->
            val result = MOD.fastValidate(stream)
            assertEquals(true, result)
        }
        ("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
                ).encodeToByteArray().openAsync().also { stream ->
            val result = MOD.fastValidate(stream)
            assertEquals(false, result)
        }
    }

    @Test
    fun testPeriodTable() {
        val protracker = Protracker()
        assertEquals(36, protracker.baseperiodtable.size)
        // Check first and last values
        assertEquals(856f, protracker.baseperiodtable[0])
        assertEquals(113f, protracker.baseperiodtable[35])
    }

    @Test
    fun testFineTuneTable() {
        val protracker = Protracker()
        assertEquals(16, protracker.finetunetable.size)
        // Center value should be 1.0 (no fine tuning adjustment)
        assertEquals(1.0f, protracker.finetunetable[8], 0.0001f)
        // Check symmetry
        assertTrue(protracker.finetunetable[7] < 1.0f)
        assertTrue(protracker.finetunetable[9] > 1.0f)
    }

    @Test
    fun testVibratoTables() {
        val protracker = Protracker()
        assertEquals(4, protracker.vibratotable.size)
        // Each vibrato waveform should have 64 samples
        for (i in 0 until 4) {
            assertEquals(64, protracker.vibratotable[i].size)
        }
    }

    @Test
    fun testEffectJumpTables() {
        val protracker = Protracker()
        assertEquals(16, protracker.effects_t0.size)
        assertEquals(16, protracker.effects_t0_e.size)
        assertEquals(16, protracker.effects_t1.size)
        assertEquals(16, protracker.effects_t1_e.size)
    }

    @Test
    fun testSampleInitialization() {
        val sample = Protracker.Sample()
        assertEquals("", sample.name)
        assertEquals(0, sample.length)
        assertEquals(0, sample.finetune)
        assertEquals(64, sample.volume)
        assertEquals(0, sample.loopstart)
        assertEquals(0, sample.looplength)
        assertTrue(sample.data.isEmpty())
    }

    @Test
    fun testChannelInitialization() {
        val channel = Protracker.Channel()
        assertEquals(0, channel.sample)
        assertEquals(214, channel.period)
        assertEquals(214.0, channel.voiceperiod)
        assertEquals(24, channel.note)
        assertEquals(64, channel.volume)
        assertEquals(0, channel.command)
        assertEquals(0, channel.data)
        assertEquals(0.0, channel.samplepos)
        assertEquals(0.0, channel.samplespeed)
        assertEquals(0, channel.flags)
        assertEquals(0, channel.noteon)
    }

    @Test
    fun testProttrackerInitialization() {
        val protracker = Protracker()
        assertEquals(4, protracker.channels)
        assertEquals(31, protracker.samples)
        assertEquals(1, protracker.songlen)
        assertEquals(0, protracker.repeatpos)
        assertEquals(0, protracker.tick)
        assertEquals(0, protracker.position)
        assertEquals(0, protracker.row)
        assertEquals(0, protracker.offset)
        assertEquals(0, protracker.flags)
        assertEquals(6, protracker.speed)
        assertEquals(125, protracker.bpm)
    }

    @Test
    fun testEffectT0Arpeggio() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.data = 0x24 // Arpeggio data (add 2 semitones, then 4 semitones)

        protracker.effect_t0_0(protracker, 0)
        assertEquals(0x24, channel.arpeggio)
    }

    @Test
    fun testEffectT0SlideUp() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.data = 20

        protracker.effect_t0_1(protracker, 0)
        assertEquals(20, channel.slidespeed)
    }

    @Test
    fun testEffectT0SlideDown() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.data = 15

        protracker.effect_t0_2(protracker, 0)
        assertEquals(15, channel.slidespeed)
    }

    @Test
    fun testEffectT0SlideToNote() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.data = 10

        protracker.effect_t0_3(protracker, 0)
        assertEquals(10, channel.slidetospeed)
    }

    @Test
    fun testEffectT0SetVolume() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.data = 32

        protracker.effect_t0_c(protracker, 0)
        assertEquals(32, channel.volume)
    }

    @Test
    fun testEffectT0SetSpeed() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.data = 4

        protracker.effect_t0_f(protracker, 0)
        assertEquals(4, protracker.speed)
    }

    @Test
    fun testEffectT0SetBPM() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.data = 140

        protracker.effect_t0_f(protracker, 0)
        assertEquals(140, protracker.bpm)
    }

    @Test
    fun testEffectT0SetSampleOffset() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.data = 5

        protracker.effect_t0_9(protracker, 0)
        assertEquals(1280.0, channel.samplepos)
    }

    @Test
    fun testEffectT0PatternJump() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.data = 3

        protracker.effect_t0_b(protracker, 0)
        assertEquals(3, protracker.patternjump)
        assertEquals(0, protracker.breakrow)
        assertEquals(16, protracker.flags and 16)
    }

    @Test
    fun testEffectT0PatternBreak() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.data = 0x32 // Break at row 32 (3*10 + 2)

        protracker.effect_t0_d(protracker, 0)
        assertEquals(32, protracker.breakrow)
        assertEquals(16, protracker.flags and 16)
    }

    @Test
    fun testEffectT0E0FilterOnOff() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.data = 0x00 // Filter on

        protracker.effect_t0_e0(protracker, 0)
        assertTrue(protracker.filter)

        channel.data = 0x01 // Filter off
        protracker.effect_t0_e0(protracker, 0)
        assertFalse(protracker.filter)
    }

    @Test
    fun testEffectT0E1FineSlideUp() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.period = 500
        channel.data = 0x05

        protracker.effect_t0_e1(protracker, 0)
        assertEquals(495, channel.period)
    }

    @Test
    fun testEffectT0E1FineSlideUpBoundary() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.period = 115
        channel.data = 0x0F // Slide up by 15

        protracker.effect_t0_e1(protracker, 0)
        assertEquals(113, channel.period) // Should clamp at 113
    }

    @Test
    fun testEffectT0E2FineSlideDown() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.period = 500
        channel.data = 0x05

        protracker.effect_t0_e2(protracker, 0)
        assertEquals(505, channel.period)
    }

    @Test
    fun testEffectT0E2FineSlideDownBoundary() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.period = 850
        channel.data = 0x0F // Slide down by 15

        protracker.effect_t0_e2(protracker, 0)
        assertEquals(856, channel.period) // Should clamp at 856
    }

    @Test
    fun testEffectT0E4SetVibratoWaveform() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.data = 0x02

        protracker.effect_t0_e4(protracker, 0)
        assertEquals(2, channel.vibratowave)
    }

    @Test
    fun testEffectT0E6LoopPattern() {
        val protracker = Protracker()
        protracker.initialize()
        protracker.row = 20
        val channel = protracker.channel[0]
        channel.data = 0x00 // Set loop start

        protracker.effect_t0_e6(protracker, 0)
        assertEquals(20, protracker.looprow)
    }

    @Test
    fun testEffectT0EAFineVolSlideUp() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.volume = 32
        channel.data = 0x05

        protracker.effect_t0_ea(protracker, 0)
        assertEquals(37, channel.volume)
    }

    @Test
    fun testEffectT0EAFineVolSlideUpBoundary() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.volume = 60
        channel.data = 0x0F

        protracker.effect_t0_ea(protracker, 0)
        assertEquals(64, channel.volume)
    }

    @Test
    fun testEffectT0EBFineVolSlideDown() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.volume = 32
        channel.data = 0x05

        protracker.effect_t0_eb(protracker, 0)
        assertEquals(27, channel.volume)
    }

    @Test
    fun testEffectT0EBFineVolSlideDownBoundary() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.volume = 5
        channel.data = 0x0F

        protracker.effect_t0_eb(protracker, 0)
        assertEquals(0, channel.volume)
    }

    @Test
    fun testEffectT1SlideUp() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.period = 500
        channel.slidespeed = 20

        protracker.effect_t1_1(protracker, 0)
        assertEquals(480, channel.period)
        assertEquals(3, channel.flags and 3) // recalc speed flag set
    }

    @Test
    fun testEffectT1SlideUpBoundary() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.period = 120
        channel.slidespeed = 20

        protracker.effect_t1_1(protracker, 0)
        assertEquals(113, channel.period)
    }

    @Test
    fun testEffectT1SlideDown() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.period = 500
        channel.slidespeed = 20

        protracker.effect_t1_2(protracker, 0)
        assertEquals(520, channel.period)
        assertEquals(3, channel.flags and 3) // recalc speed flag set
    }

    @Test
    fun testEffectT1SlideDownBoundary() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.period = 840
        channel.slidespeed = 20

        protracker.effect_t1_2(protracker, 0)
        assertEquals(856, channel.period)
    }

    @Test
    fun testEffectT1VolumeSlideUp() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.volume = 32
        channel.data = 0x50 // slide up by 5

        protracker.effect_t1_a(protracker, 0)
        assertEquals(37, channel.volume)
    }

    @Test
    fun testEffectT1VolumeSlideDown() {
        val protracker = Protracker()
        protracker.initialize()
        val channel = protracker.channel[0]
        channel.volume = 32
        channel.data = 0x05 // slide down by 5

        protracker.effect_t1_a(protracker, 0)
        assertEquals(27, channel.volume)
    }

    @Test
    fun testEffectT1E9RetrigSample() {
        val protracker = Protracker()
        protracker.initialize()
        protracker.tick = 3
        val channel = protracker.channel[0]
        channel.data = 0x03 // retrig every 3 ticks
        channel.samplepos = 100.0

        protracker.effect_t1_e9(protracker, 0)
        assertEquals(0.0, channel.samplepos)
    }

    @Test
    fun testEffectT1E9RetrigSampleNotOnTick() {
        val protracker = Protracker()
        protracker.initialize()
        protracker.tick = 2
        val channel = protracker.channel[0]
        channel.data = 0x03 // retrig every 3 ticks
        channel.samplepos = 100.0

        protracker.effect_t1_e9(protracker, 0)
        assertEquals(100.0, channel.samplepos) // Should not change
    }

    @Test
    fun testEffectT1ECCutSample() {
        val protracker = Protracker()
        protracker.initialize()
        protracker.tick = 2
        val channel = protracker.channel[0]
        channel.data = 0x02 // cut at tick 2
        channel.volume = 32

        protracker.effect_t1_ec(protracker, 0)
        assertEquals(0, channel.volume)
    }

    @Test
    fun testEffectT1ECCutSampleNotOnTick() {
        val protracker = Protracker()
        protracker.initialize()
        protracker.tick = 1
        val channel = protracker.channel[0]
        channel.data = 0x02 // cut at tick 2
        channel.volume = 32

        protracker.effect_t1_ec(protracker, 0)
        assertEquals(32, channel.volume) // Should not change
    }

    @Test
    fun testClearSong() {
        val protracker = Protracker()
        protracker.title = "Test Song"
        protracker.bpm = 150
        protracker.speed = 8

        protracker.clearsong()
        assertEquals("", protracker.title)
        assertEquals("", protracker.signature)
        assertEquals(1, protracker.songlen)
        assertEquals(0, protracker.repeatpos)
        assertEquals(4, protracker.channels)
        assertEquals(31, protracker.samples)
        assertEquals(31, protracker.sample.size)
    }

    @Test
    fun testInitialize() {
        val protracker = Protracker()
        protracker.tick = 50
        protracker.position = 10
        protracker.speed = 8
        protracker.bpm = 150

        protracker.initialize()
        assertEquals(0, protracker.tick)
        assertEquals(0, protracker.position)
        assertEquals(0, protracker.row)
        assertEquals(0, protracker.offset)
        assertEquals(0, protracker.flags)
        assertEquals(6, protracker.speed)
        assertEquals(125, protracker.bpm)
        assertEquals(4, protracker.channel.size)
    }
}
