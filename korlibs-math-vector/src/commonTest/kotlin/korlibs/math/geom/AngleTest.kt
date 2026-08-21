package korlibs.math.geom

import korlibs.math.interpolation.Ratio
import kotlin.math.PI
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class AngleTest {

    private val EPS = 0.00001

    @Test
    fun testFromDegrees() {
        assertEquals(0.0, Angle.fromDegrees(0).radians, EPS)
        assertEquals(PI, Angle.fromDegrees(180).radians, EPS)
        assertEquals(PI * 2, Angle.fromDegrees(360).radians, EPS)
        assertEquals(PI / 2, Angle.fromDegrees(90.0).radians, EPS)
        assertEquals(PI / 2, Angle.fromDegrees(90.0f).radians, EPS)
    }

    @Test
    fun testFromRadians() {
        assertEquals(0.0, Angle.fromRadians(0.0).degrees, EPS)
        assertEquals(180.0, Angle.fromRadians(PI).degrees, EPS)
        assertEquals(360.0, Angle.fromRadians(PI * 2).degrees, EPS)
        assertEquals(90.0, Angle.fromRadians(PI / 2).degrees, EPS)
        assertEquals(90.0, Angle.fromRadians((PI / 2).toFloat()).degrees, EPS)
        assertEquals(90.0, Angle.fromRadians((PI / 2)).degrees, EPS)
    }

    @Test
    fun testFromRatio() {
        assertEquals(0.0, Angle.fromRatio(0.0).degrees, EPS)
        assertEquals(90.0, Angle.fromRatio(0.25).degrees, EPS)
        assertEquals(180.0, Angle.fromRatio(0.5).degrees, EPS)
        assertEquals(360.0, Angle.fromRatio(1.0).degrees, EPS)
        assertEquals(180.0, Angle.fromRatio(0.5f).degrees, EPS)
        assertEquals(180.0, Angle.fromRatio(Ratio(0.5)).degrees, EPS)
    }

    @Test
    fun testRatioRoundTrip() {
        val angle = Angle.fromDegrees(135.0)
        assertEquals(0.375, angle.ratio.toDouble(), EPS)
    }

    @Test
    fun testConstants() {
        assertEquals(0.0, Angle.ZERO.degrees, EPS)
        assertEquals(90.0, Angle.QUARTER.degrees, EPS)
        assertEquals(180.0, Angle.HALF.degrees, EPS)
        assertEquals(270.0, Angle.THREE_QUARTERS.degrees, EPS)
        assertEquals(360.0, Angle.FULL.degrees, EPS)
    }

    @Test
    fun testCosineSineTangent() {
        assertEquals(1.0, Angle.fromDegrees(0).cosine, EPS)
        assertEquals(0.0, Angle.fromDegrees(0).sine, EPS)
        assertEquals(0.0, Angle.fromDegrees(90).cosine, EPS)
        assertEquals(1.0, Angle.fromDegrees(90).sine, EPS)
        assertEquals(-1.0, Angle.fromDegrees(180).cosine, EPS)
        assertEquals(0.0, Angle.fromDegrees(180).sine, EPS)
        assertEquals(1.0, Angle.fromDegrees(45).tangent, EPS)
    }

    @Test
    fun testSineWithUpScreenIsNegated() {
        val angle = Angle.fromDegrees(90)
        assertEquals(1.0, angle.sine(Vector2D.UP), EPS)
        assertEquals(-1.0, angle.sine(Vector2D.UP_SCREEN), EPS)
        assertEquals(0.0, angle.cosine(Vector2D.UP), EPS)
        assertEquals(0.0, angle.cosine(Vector2D.UP_SCREEN), EPS)
    }

    @Test
    fun testTopLevelTrigFunctions() {
        val angle = Angle.fromDegrees(90)
        assertEquals(cos(angle), angle.cosine, EPS)
        assertEquals(sin(angle), angle.sine, EPS)
        assertEquals(cosf(angle).toDouble(), angle.cosine, EPS)
        assertEquals(sinf(angle).toDouble(), angle.sine, EPS)
    }

    @Test
    fun testPlusMinus() {
        val a = Angle.fromDegrees(30)
        val b = Angle.fromDegrees(60)
        assertEquals(90.0, (a + b).degrees, EPS)
        assertEquals(-30.0, (a - b).degrees, EPS)
    }

    @Test
    fun testUnaryOperators() {
        val a = Angle.fromDegrees(30)
        assertEquals(-30.0, (-a).degrees, EPS)
        assertEquals(30.0, (+a).degrees, EPS)
    }

    @Test
    fun testTimesDiv() {
        val a = Angle.fromDegrees(30)
        assertEquals(60.0, (a * 2.0).degrees, EPS)
        assertEquals(60.0, (a * 2.0f).degrees, EPS)
        assertEquals(60.0, (a * 2).degrees, EPS)
        assertEquals(15.0, (a / 2.0).degrees, EPS)
        assertEquals(15.0, (a / 2.0f).degrees, EPS)
        assertEquals(15.0, (a / 2).degrees, EPS)
    }

    @Test
    fun testDivByAngleReturnsRatio() {
        val a = Angle.fromDegrees(90)
        val b = Angle.fromDegrees(180)
        assertEquals(0.5, a / b, EPS)
    }

    @Test
    fun testRemAndUmod() {
        val a = Angle.fromDegrees(400)
        val full = Angle.fromDegrees(360)
        assertEquals(40.0, (a % full).degrees, EPS)
        assertEquals(40.0, (a umod full).degrees, EPS)

        val negative = Angle.fromDegrees(-40)
        // Regular rem can be negative, umod should always be positive
        assertTrue((negative % full).degrees <= 0.0)
        assertEquals(320.0, (negative umod full).degrees, EPS)
    }

    @Test
    fun testAbsoluteValue() {
        assertEquals(30.0, Angle.fromDegrees(-30).absoluteValue.degrees, EPS)
        assertEquals(30.0, abs(Angle.fromDegrees(-30)).degrees, EPS)
    }

    @Test
    fun testCompareTo() {
        val a = Angle.fromDegrees(10)
        val b = Angle.fromDegrees(20)
        assertTrue(a < b)
        assertTrue(b > a)
        assertEquals(0, a.compareTo(Angle.fromDegrees(10)))
    }

    @Test
    fun testMinMax() {
        val a = Angle.fromDegrees(10)
        val b = Angle.fromDegrees(20)
        assertEquals(10.0, min(a, b).degrees, EPS)
        assertEquals(20.0, max(a, b).degrees, EPS)
    }

    @Test
    fun testClamp() {
        // clamp(min, max) = min(max(this, min), max)
        val value = Angle.fromDegrees(50)
        assertEquals(30.0, value.clamp(Angle.fromDegrees(0), Angle.fromDegrees(30)).degrees, EPS)
        assertEquals(50.0, value.clamp(Angle.fromDegrees(0), Angle.fromDegrees(90)).degrees, EPS)
        // value (50) is above both bounds, so it gets clamped down to max (20)
        assertEquals(20.0, value.clamp(Angle.fromDegrees(10), Angle.fromDegrees(20)).degrees, EPS)
    }

    @Test
    fun testNormalized() {
        assertEquals(10.0, Angle.fromDegrees(370).normalized.degrees, EPS)
        assertEquals(350.0, Angle.fromDegrees(-10).normalized.degrees, EPS)
        assertEquals(0.0, Angle.fromDegrees(360).normalized.degrees, EPS)
    }

    @Test
    fun testNormalizedHalf() {
        assertEquals(-90.0, Angle.fromDegrees(270).normalizedHalf.degrees, EPS)
        assertEquals(90.0, Angle.fromDegrees(90).normalizedHalf.degrees, EPS)
        assertEquals(-179.0, Angle.fromDegrees(181).normalizedHalf.degrees, EPS)
    }

    @Test
    fun testShortDistanceTo() {
        val from = Angle.fromDegrees(350)
        val to = Angle.fromDegrees(10)
        assertEquals(20.0, from.shortDistanceTo(to).degrees, EPS)

        val from2 = Angle.fromDegrees(10)
        val to2 = Angle.fromDegrees(350)
        assertEquals(-20.0, from2.shortDistanceTo(to2).degrees, EPS)

        val same = Angle.fromDegrees(45)
        assertEquals(0.0, same.shortDistanceTo(Angle.fromDegrees(45)).degrees, EPS)
    }

    @Test
    fun testLongDistanceTo() {
        val from = Angle.fromDegrees(350)
        val to = Angle.fromDegrees(10)
        assertEquals(-340.0, from.longDistanceTo(to).degrees, EPS)

        val same = Angle.fromDegrees(45)
        assertEquals(0.0, same.longDistanceTo(Angle.fromDegrees(45)).degrees, EPS)
    }

    @Test
    fun testInBetweenSimpleRange() {
        val angle = Angle.fromDegrees(45)
        assertTrue(angle.inBetween(Angle.fromDegrees(0), Angle.fromDegrees(90), inclusive = true))
        assertFalse(Angle.fromDegrees(120).inBetween(Angle.fromDegrees(0), Angle.fromDegrees(90), inclusive = true))
    }

    @Test
    fun testInBetweenInclusiveExclusiveBoundaries() {
        val min = Angle.fromDegrees(0)
        val max = Angle.fromDegrees(90)
        assertTrue(max.inBetweenInclusive(min, max))
        assertFalse(max.inBetweenExclusive(min, max))
    }

    @Test
    fun testInBetweenWrapAround() {
        val min = Angle.fromDegrees(350)
        val max = Angle.fromDegrees(10)
        assertTrue(Angle.fromDegrees(355).inBetween(min, max, inclusive = true))
        assertTrue(Angle.fromDegrees(5).inBetween(min, max, inclusive = true))
        assertFalse(Angle.fromDegrees(180).inBetween(min, max, inclusive = true))
    }

    @Test
    fun testClosedRangeContains() {
        val range = Angle.fromDegrees(0)..Angle.fromDegrees(90)
        assertTrue(Angle.fromDegrees(45) in range)
        assertFalse(Angle.fromDegrees(120) in range)
    }

    @Test
    fun testOpenRangeUntil() {
        val range = Angle.fromDegrees(0) until Angle.fromDegrees(90)
        assertTrue(Angle.fromDegrees(0) in range)
        assertFalse(Angle.fromDegrees(90) in range)
    }

    @Test
    fun testIsAlmostEquals() {
        val a = Angle.fromRadians(1.0)
        val b = Angle.fromRadians(1.0000001)
        assertTrue(a.isAlmostEquals(b))
        assertFalse(a.isAlmostEquals(Angle.fromRadians(1.1)))
    }

    @Test
    fun testIsAlmostZero() {
        assertTrue(Angle.fromRadians(0.00000001).isAlmostZero())
        assertFalse(Angle.fromRadians(0.1).isAlmostZero())
    }

    @Test
    fun testNumberExtensions() {
        assertEquals(90.0, 90.0.degrees.degrees, EPS)
        assertEquals(90.0, 90.degrees.degrees, EPS)
        assertEquals(90.0, 90.0f.degrees.degrees, EPS)
        assertEquals(PI / 2, (PI / 2).radians.radians, EPS)
        // 1.radians is 1 radian (~57.3 degrees), not PI/2 -- just verify the
        // Int extension passes the raw radian value through unchanged.
        assertEquals(1.0, 1.radians.radians, EPS)
        assertEquals(2.0, 2.radians.radians, EPS)
    }

    @Test
    fun testBetweenRightDirection() {
        val angle = Angle.between(0.0, 0.0, 1.0, 0.0)
        assertEquals(0.0, angle.degrees, EPS)
    }

    @Test
    fun testBetweenUpDirection() {
        val angle = Angle.between(0.0, 0.0, 0.0, 1.0)
        assertEquals(90.0, angle.degrees, EPS)
    }

    @Test
    fun testBetweenLeftDirection() {
        val angle = Angle.between(0.0, 0.0, -1.0, 0.0)
        assertEquals(180.0, angle.degrees, EPS)
    }

    @Test
    @Ignore
    fun testBetweenWithOrigin() {
        val angle = Angle.between(0.0, 0.0, 1.0, 0.0, 0.0, 1.0)
        // TODO Check for potential bug, otherwise assert 135.0 instead of 90.0
        assertEquals(90.0, angle.degrees, EPS)

        // Since `o` cancels out, translating the origin shouldn't change the result.
        // TODO If bug, the below assertion is wrong and should be updated
        val translated = Angle.between(5.0, 5.0, 6.0, 5.0, 5.0, 6.0)
        assertEquals(angle.degrees, translated.degrees, EPS)
    }

    @Test
    fun testAsinAcos() {
        assertEquals(90.0, Angle.asin(1.0).degrees, EPS)
        assertEquals(0.0, Angle.acos(1.0).degrees, EPS)
        assertEquals(180.0, Angle.acos(-1.0).degrees, EPS)
    }

    @Test
    fun testAtan2() {
        val angle = Angle.atan2(1.0, 0.0)
        assertEquals(90.0, angle.degrees, EPS)
    }

    @Test
    fun testInterpolateAngleSimple() {
        val result = Ratio(0.5).interpolateAngle(Angle.fromDegrees(0), Angle.fromDegrees(90))
        assertEquals(45.0, result.degrees, EPS)
    }

    @Test
    fun testInterpolateAngleMinimizedWrapsAround() {
        val result = Ratio(0.5).interpolateAngleNormalized(Angle.fromDegrees(350), Angle.fromDegrees(10))
        // shortest path from 350 to 10 goes through 0/360, so the midpoint is 0 degrees
        assertEquals(0.0, result.normalized.degrees, EPS)
    }

    @Test
    fun testInterpolateAngleDenormalizedDoesNotWrap() {
        val result = Ratio(0.5).interpolateAngleDenormalized(Angle.fromDegrees(0), Angle.fromDegrees(350))
        assertEquals(175.0, result.degrees, EPS)
    }

    @Test
    fun testInterpolateAngleAtEndpoints() {
        val l = Angle.fromDegrees(10)
        val r = Angle.fromDegrees(80)
        assertEquals(10.0, Ratio(0.0).interpolateAngle(l, r).degrees, EPS)
        assertEquals(80.0, Ratio(1.0).interpolateAngle(l, r).degrees, EPS)
    }

    @Test
    fun testToStringContainsDegrees() {
        val text = Angle.fromDegrees(45.0).toString()
        assertTrue(text.contains("degrees"))
        assertTrue(text.contains("45"))
    }

    @Test
    fun testBetweenPointOverload() {
        assertEquals(0.0, Angle.between(Point(0, 0), Point(10, 0)).degrees, EPS)
        assertEquals(90.0, Angle.between(Point(0, 0), Point(0, 10)).degrees, EPS)
        assertEquals(180.0, Angle.between(Point(0, 0), Point(-10, 0)).degrees, EPS)
        assertEquals(270.0, Angle.between(Point(0, 0), Point(0, -10)).degrees, EPS)
    }

    @Test
    fun testBetweenIntOverload() {
        assertEquals(0.0, Angle.between(100, 100, 110, 100).degrees, EPS)
        assertEquals(90.0, Angle.between(100, 100, 100, 110).degrees, EPS)
        assertEquals(180.0, Angle.between(100, 100, -110, 100).degrees, EPS)
        assertEquals(270.0, Angle.between(100, 100, 100, -110).degrees, EPS)
    }

    /**
     * infix `angle inBetween range` — a distinct entry point from
     * the 3-arg inBetween(min, max, inclusive) and the range `in`
     * operator (member fun inBetween(ClosedRange<Angle>) / inBetween(OpenRange<Angle>)).
     */
    @Test
    fun testInBetweenInfixClosedRange() {
        assertTrue((-15).degrees inBetween ((-15).degrees..15.degrees))
        assertTrue((+15).degrees inBetween ((-15).degrees..15.degrees))
        assertTrue(0.degrees inBetween ((-15).degrees..15.degrees))
        assertTrue(0.degrees inBetween (345.degrees..15.degrees)) // wrap-around

        assertFalse((-20).degrees inBetween ((-15).degrees..15.degrees))
        assertFalse((+20).degrees inBetween ((-15).degrees..15.degrees))
        assertFalse((-20).degrees inBetween (345.degrees..15.degrees))
        assertFalse((+20).degrees inBetween (345.degrees..15.degrees))
    }

    @Test
    fun testInBetweenInfixOpenRange() {
        assertTrue((-15).degrees inBetween ((-15).degrees until 15.degrees))
        assertFalse((+15).degrees inBetween ((-15).degrees until 15.degrees)) // exclusive end
        assertTrue(0.degrees inBetween ((-15).degrees until 15.degrees))
        assertTrue(0.degrees inBetween (345.degrees until 15.degrees)) // wrap-around

        assertFalse((-20).degrees inBetween ((-15).degrees until 15.degrees))
        assertFalse((+20).degrees inBetween ((-15).degrees until 15.degrees))
        assertFalse((-20).degrees inBetween (345.degrees until 15.degrees))
        assertFalse((+20).degrees inBetween (345.degrees until 15.degrees))
    }

    /**
     * explicit ==, <=, >= operators, incl. cross-construction equality
     * (radians/degrees/ratio should all agree on identity).
     */
    @Test
    fun testEqualityAndComparisonOperators() {
        assertEquals(90.degrees, 90.degrees)
        assertTrue(90.degrees <= 90.degrees)
        assertTrue(90.degrees <= 100.degrees)
        assertTrue(100.degrees >= 90.degrees)
        assertNotEquals(90.degrees, 91.degrees)
    }

    @Test
    fun testCrossConstructionEquality() {
        assertEquals(Angle.fromRadians(PI), Angle.fromDegrees(180.0))
        assertTrue(Angle.fromRatio(0.5) == Angle.fromDegrees(180.0))
    }

    /**
     * Exact toString() output — pins down whole-number formatting
     * across three different construction paths.
     */
    @Test
    fun testToStringExact() {
        assertEquals("180.degrees", Angle.fromRatio(0.5).toString())
        assertEquals("180.degrees", PI.radians.toString())
        assertEquals("180.degrees", 180.degrees.toString())
    }

    /**
     * normalizedHalf boundary + negative-input cases.
     *
     * In particular normalizedHalf at exactly 180.degrees is a real boundary:
     * the impl only flips sign when strictly > HALF, so 180 itself must stay
     * positive rather than becoming -180.
     */
    @Test
    fun testNormalizedHalfBoundariesAndNegatives() {
        assertEquals(0.0, (-360).degrees.normalizedHalf.degrees, EPS)
        assertEquals(90.0, (-270).degrees.normalizedHalf.degrees, EPS)
        assertEquals(180.0, (-180).degrees.normalizedHalf.degrees, EPS)
        assertEquals(-90.0, (-90).degrees.normalizedHalf.degrees, EPS)
        assertEquals(0.0, 0.degrees.normalizedHalf.degrees, EPS)
        assertEquals(90.0, 90.degrees.normalizedHalf.degrees, EPS)
        // Boundary: exactly 180 must NOT flip sign (condition is `> HALF`, not `>=`).
        assertEquals(180.0, 180.degrees.normalizedHalf.degrees, EPS)
        assertEquals(-90.0, 270.degrees.normalizedHalf.degrees, EPS)
        assertEquals(0.0, 360.degrees.normalizedHalf.degrees, EPS)
        assertEquals(0.0, 720.degrees.normalizedHalf.degrees, EPS)
    }

    /**
     * Normalized boundary + negative-input cases.
     */
    @Test
    fun testNormalizedBoundariesAndNegatives() {
        assertEquals(0.0, (-360).degrees.normalized.degrees, EPS)
        assertEquals(90.0, (-270).degrees.normalized.degrees, EPS)
        assertEquals(180.0, (-180).degrees.normalized.degrees, EPS)
        assertEquals(270.0, (-90).degrees.normalized.degrees, EPS)
        assertEquals(0.0, 0.degrees.normalized.degrees, EPS)
        assertEquals(90.0, 90.degrees.normalized.degrees, EPS)
        assertEquals(180.0, 180.degrees.normalized.degrees, EPS)
        assertEquals(270.0, 270.degrees.normalized.degrees, EPS)
        assertEquals(0.0, 360.degrees.normalized.degrees, EPS)
        assertEquals(0.0, 720.degrees.normalized.degrees, EPS)
    }

    /**
     * interpolateAngle with a negative-degree endpoint.
     */
    @Test
    fun testInterpolateWithNegativeEndpoint() {
        assertEquals(
            202.5,
            Ratio(0.25).interpolateAngleNormalized(180.degrees, (-90).degrees).degrees,
            EPS
        )
        assertEquals(
            112.5,
            Ratio(0.25).interpolateAngleDenormalized(180.degrees, (-90).degrees).degrees,
            EPS
        )
    }

    /**
     * Full quadrant x up-vector matrix for cosine()/sine(),
     * called as zero-arg *functions* (not the `.cosine`/`.sine` properties).
     */
    @Test
    fun testReferenceSystemQuadrantMatrix() {
        fun check(expectedCos: Double, expectedSin: Double, angle: Angle, up: Vector2D) {
            assertEquals(expectedCos, angle.cosine(up), EPS)
            assertEquals(expectedSin, angle.sine(up), EPS)
        }

        // Default up (no argument) matches Vector2D.UP.
        check(1.0, 0.0, Angle.ZERO, Vector2D.UP)
        check(0.0, 1.0, Angle.QUARTER, Vector2D.UP)
        check(-1.0, 0.0, Angle.HALF, Vector2D.UP)
        check(0.0, -1.0, Angle.THREE_QUARTERS, Vector2D.UP)

        check(1.0, 0.0, Angle.ZERO, Vector2D.UP_SCREEN)
        check(0.0, -1.0, Angle.QUARTER, Vector2D.UP_SCREEN)
        check(-1.0, 0.0, Angle.HALF, Vector2D.UP_SCREEN)
        check(0.0, 1.0, Angle.THREE_QUARTERS, Vector2D.UP_SCREEN)
    }

    /**
     * Clamp boundary coverage — exactly-at-min, exactly-at-max,
     * and strictly-inside-range cases, plus negative bounds.
     */
    @Test
    fun testClampBoundaries() {
        assertEquals(-30.0, (-45).degrees.clamp((-30).degrees, 30.degrees).degrees, EPS) // below range
        assertEquals(-30.0, (-30).degrees.clamp((-30).degrees, 30.degrees).degrees, EPS) // exactly at min
        assertEquals(-20.0, (-20).degrees.clamp((-30).degrees, 30.degrees).degrees, EPS) // inside range
        assertEquals(0.0, 0.degrees.clamp((-30).degrees, 30.degrees).degrees, EPS)       // inside range
        assertEquals(15.0, 15.degrees.clamp((-30).degrees, 30.degrees).degrees, EPS)     // inside range
        assertEquals(30.0, 30.degrees.clamp((-30).degrees, 30.degrees).degrees, EPS)     // exactly at max
        assertEquals(30.0, 45.degrees.clamp((-30).degrees, 30.degrees).degrees, EPS)     // above range
    }
}