package korlibs.io.util

import kotlin.math.*

object NumberParser {
	const val END = '\u0000'

	fun parseInt(str: String, start: Int = 0, end: Int = str.length, radix: Int = 10): Int {
		var n = start
		return parseInt(radix) { if (n >= end) END else str[n++] }
	}

	fun parseDouble(str: String, start: Int = 0, end: Int = str.length): Double {
		var n = start
		return parseDouble { if (n >= end) END else str[n++] }
	}

	inline fun parseInt(radix: Int = 10, gen: (Int) -> Char): Int {
		var positive = true
		var out = 0
		var n = 0
		while (true) {
			val c = gen(n++)
			if (c == END) break
			if (c == '-' || c == '+') {
				positive = (c == '+')
			} else {
				val value = c.ctypeAsInt()
				if (value < 0) break
				out *= radix
				out += value
			}
		}
		return if (positive) out else -out
	}

	inline fun parseDouble(gen: (Int) -> Char): Double {
		var out = 0.0
		var frac = 1.0
		var pointSeen = false
		var eSeen = false
		var negate = false
		var negateExponent = false
		var exponent = 0
		var n = 0
		while (true) {
			val c = gen(n++)
			if (c == END) break
			when (c) {
				'e', 'E' -> eSeen = true
				'-' -> {
					if (eSeen) negateExponent = true else negate = true
				}
				'.' -> pointSeen = true
				else -> {
					if (eSeen) {
						exponent *= 10
						exponent += c.ctypeAsInt()
					} else {
						if (pointSeen) frac /= 10
						out *= 10
						out += c.ctypeAsInt()
					}
				}
			}
		}
		val res = (out * frac) * 10.0.pow(if (negateExponent) -exponent else exponent)
		return if (negate) -res else res
	}
}

@Suppress("ConvertTwoComparisonsToRangeCheck") // @TODO: Kotlin-Native doesn't optimize ranges
@PublishedApi internal fun Char.ctypeAsInt(): Int = when {
	this >= '0' && this <= '9' -> this - '0'
	this >= 'a' && this <= 'z' -> this - 'a' + 10
	this >= 'A' && this <= 'Z' -> this - 'A' + 10
	else -> -1
}
