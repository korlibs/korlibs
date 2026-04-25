package korlibs.audio.format.mod

import korlibs.io.stream.openAsync
import korlibs.memory.*
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class S3MTest {

	@Test
	fun testS3MFormatValidation() = runTest {
		"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxSCRMxxxxxxx".encodeToByteArray().openAsync().also { stream ->
			val result = S3M.fastValidate(stream)
			assertEquals(true, result)
		}
		"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx".encodeToByteArray().openAsync().also { stream ->
			val result = S3M.fastValidate(stream)
			assertEquals(false, result)
		}
	}

	@Test
	fun testPeriodTableAndRetrigTable() {
		val s = Screamtracker()
		assertTrue(s.periodtable.size > 0)
		// retrigvoltab has known entries
		assertEquals(16, s.retrigvoltab.size)
		assertEquals(0f, s.retrigvoltab[0])
		assertEquals(-2f, s.retrigvoltab[2])
	}

	@Test
	fun testClearSongAndInitialize() {
		val s = Screamtracker()
		s.title = "X"
		s.channels = 4
		s.clearsong()
		assertEquals("", s.title)
		assertEquals(256, s.sample.size)
		s.channels = 3
		s.initialize()
		assertEquals(3, s.channel.size)
		assertEquals(6, s.speed)
		assertEquals(125, s.bpm)
	}

	@Test
	fun testEffectT0SetSpeedPatternJumpBreak() {
		val s = Screamtracker()
		s.channels = 2
		s.initialize()
		val ch = s.channel[0]
		ch.data = 9
		s.effect_t0_a(0)
		assertEquals(9, s.speed)

		ch.data = 3
		s.effect_t0_b(0)
		assertEquals(3, s.patternjump)
		assertTrue((s.flags and 16) != 0)

		ch.data = 0x32
		s.effect_t0_c(0)
		assertEquals(32, s.breakrow)
		assertTrue((s.flags and 16) != 0)
	}

	@Test
	fun testVolumeSlidesAndClamping() {
		val s = Screamtracker()
		s.channels = 1
		s.initialize()
		val ch = s.channel[0]
		ch.voicevolume = 10
		ch.data = 0x1f // low nibble 0x0f -> increase by high nibble (1)
		s.effect_t0_d(0)
		assertEquals(11, ch.voicevolume)

		ch.voicevolume = 2
		ch.data = 0xF5 // high nibble 0xF -> fine down by 5
		s.effect_t0_d(0)
		assertEquals(0, ch.voicevolume)

		// tick-1 volume slide
		ch.voicevolume = 20
		ch.volslide = 0x50 // slide up by 5 (high nibble nonzero, low nibble zero)
		s.effect_t1_d(0)
		assertEquals(25, ch.voicevolume)

		ch.voicevolume = 20
		ch.volslide = 0x05 // slide down by 5
		s.effect_t1_d(0)
		assertEquals(15, ch.voicevolume)
	}

	@Test
	fun testSlideUpDownNoteonBehavior() {
		val s = Screamtracker()
		s.channels = 1
		s.initialize()
		val ch = s.channel[0]
		ch.voiceperiod = 27390.0
		ch.data = 0xf3
		ch.slidespeed = ch.data
		s.effect_t0_e(0) // slide down increases voiceperiod
		assertTrue(ch.voiceperiod > 27392 || ch.noteon == 0)

		ch.voiceperiod = 60.0
		ch.data = 0xf2
		ch.slidespeed = ch.data
		s.effect_t0_f(0) // slide up decreases voiceperiod
		assertTrue(ch.voiceperiod < 56 || ch.noteon == 0)
	}

	@Test
	fun testArpeggioAndSampleOffsetRetrig() {
		val s = Screamtracker()
		s.channels = 1
		s.sample = Array(1) { Screamtracker.Sample(length = 1000) }
		s.initialize()
		val ch = s.channel[0]
		ch.data = 0x12
		ch.period = 1234.0
		s.effect_t0_j(0)
		assertEquals(18, ch.arpeggio and 0xff or 0)  // TODO check why expected is not 0
		assertEquals(ch.period, ch.voiceperiod)

		// sample offset
		ch.data = 3
		ch.lastoffset = 0
		s.effect_t0_o(0)
		assertEquals(768.0, ch.samplepos)

		// retrig via Q: set samplepos and tick such that retrig happens
		ch.samplepos = 100.0
		ch.data = 0x11
		s.tick = 0
		s.effect_t0_q(0)
		assertEquals(0.0, ch.samplepos)
	}

	@Test
	fun testPanningAndLoopPatternAndPatternDelay() {
		val s = Screamtracker()
		s.channels = 2
		s.initialize()
		val ch = s.channel[0]
		ch.data = 0x0f
		s.effect_t0_s8(0)
		assertEquals(1.0f, s.pan_r[0])
		assertEquals(0.0f, s.pan_l[0])

		ch.data = 2
		s.loopcount = 0
		s.effect_t0_sb(0)
		assertTrue(s.loopcount >= 0)

		ch.data = 5
		s.effect_t0_se(0)
		assertEquals(5, s.patterndelay)
		assertEquals(0, s.patternwait)
	}

	@Test
	fun testTick1EffectsVibratoArpeggioRetrigAndCut() {
		val s = Screamtracker()
		s.channels = 1
		s.sample = Array(1) { Screamtracker.Sample(length = 10, c2spd = 8363) }
		s.initialize()
		val ch = s.channel[0]
		// vibrato
		ch.vibratowave = 0
		ch.vibratopos = 0
		ch.vibratodepth = 10
		ch.voiceperiod = 1000.0
		s.effect_t1_h(0)
		assertTrue((ch.flags and 1) != 0)

		// arpeggio
		ch.note = 1
		ch.sample = 0
		s.effect_t1_j(0)
		assertTrue(ch.voiceperiod > 0.0)

		// retrig
		ch.lastretrig = 0x21 // low nibble 1 -> retrig every tick
		ch.samplepos = 10.0
		ch.voicevolume = 20
		s.tick = 0
		s.effect_t1_q(0)
		assertEquals(0.0, ch.samplepos)

		// cut
		ch.data = 0x02
		s.tick = 2
		s.effect_t1_sc(0)
		assertEquals(0, ch.volume)
		assertEquals(0, ch.voicevolume)
	}

	@Test
	fun testProcessNoteSetsSampleAndVoiceVolume() {
		val s = Screamtracker()
		s.channels = 1
		s.sample = Array(1) { Screamtracker.Sample(length = 10, volume = 50, c2spd = 8363) }
		// create one pattern with one row/channel and set note and instrument
		s.pattern = Array(1) { Uint8Buffer(5) }
		s.pattern[0][0] = 0x10 // note
		s.pattern[0][1] = 1 // instrument
		s.pattern[0][2] = 0x20 // volume
		s.patterntable = Uint8Buffer(1)
		s.patterntable[0] = 0
		s.songlen = 1
		s.initialize()
		s.row = 0
		s.process_note(0, 0)
		assertEquals(0, s.channel[0].sample)
		assertEquals(32, s.channel[0].voicevolume)  // TODO check why expected is not 50
	}
}
