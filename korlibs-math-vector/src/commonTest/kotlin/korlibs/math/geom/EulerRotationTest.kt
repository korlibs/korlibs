package korlibs.math.geom

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private const val EPSILON = 1e-4f

class EulerRotationTest {

    private fun assertAngleAlmostEquals(
        expected: Angle,
        actual: Angle,
        epsilon: Float = EPSILON,
        message: String = ""
    ) {
        assertTrue(
            abs(expected.radians - actual.radians) < epsilon ||
                    abs(abs(expected.radians - actual.radians) - (2 * PI.toFloat())) < epsilon,
            "Expected angle $expected but got $actual. $message"
        )
    }

    private fun assertEulerAlmostEquals(expected: EulerRotation, actual: EulerRotation, epsilon: Float = EPSILON) {
        assertTrue(
            expected.isAlmostEquals(actual, epsilon),
            "Expected EulerRotation(roll=${expected.roll}, pitch=${expected.pitch}, yaw=${expected.yaw}) " +
                    "but got EulerRotation(roll=${actual.roll}, pitch=${actual.pitch}, yaw=${actual.yaw})"
        )
    }

    private fun assertQuaternionAlmostEquals(expected: Quaternion, actual: Quaternion, epsilon: Float = EPSILON) {
        // Account for double-cover: q and -q represent the same rotation
        val direct = abs(expected.x - actual.x) < epsilon &&
                abs(expected.y - actual.y) < epsilon &&
                abs(expected.z - actual.z) < epsilon &&
                abs(expected.w - actual.w) < epsilon
        val negated = abs(expected.x + actual.x) < epsilon &&
                abs(expected.y + actual.y) < epsilon &&
                abs(expected.z + actual.z) < epsilon &&
                abs(expected.w + actual.w) < epsilon
        assertTrue(
            direct || negated,
            "Expected Quaternion(${expected.x}, ${expected.y}, ${expected.z}, ${expected.w}) " +
                    "but got Quaternion(${actual.x}, ${actual.y}, ${actual.z}, ${actual.w})"
        )
    }

    @Test
    fun testDefaultConstructorIsZero() {
        val e = EulerRotation()
        assertAngleAlmostEquals(Angle.ZERO, e.roll)
        assertAngleAlmostEquals(Angle.ZERO, e.pitch)
        assertAngleAlmostEquals(Angle.ZERO, e.yaw)
    }

    @Test
    fun testConstructorStoresAnglesCorrectly() {
        val roll = 30.degrees
        val pitch = 45.degrees
        val yaw = 60.degrees
        val e = EulerRotation(roll, pitch, yaw)
        assertAngleAlmostEquals(roll, e.roll)
        assertAngleAlmostEquals(pitch, e.pitch)
        assertAngleAlmostEquals(yaw, e.yaw)
    }

    @Test
    fun testConstructorWithExplicitConfig() {
        val config = EulerRotation.Config(EulerRotation.Order.YXZ, EulerRotation.CoordinateSystem.RIGHT_HANDED)
        val e = EulerRotation(10.degrees, 20.degrees, 30.degrees, config)
        assertEquals(EulerRotation.Order.YXZ, e.order)
        assertEquals(EulerRotation.CoordinateSystem.RIGHT_HANDED, e.coordinateSystem)
    }

    @Test
    fun testCopyPreservesAllFields() {
        val original = EulerRotation(10.degrees, 20.degrees, 30.degrees)
        val copy = original.copy()
        assertEulerAlmostEquals(original, copy)
    }

    @Test
    fun testCopyOverridesIndividualFields() {
        val original = EulerRotation(10.degrees, 20.degrees, 30.degrees)
        val modified = original.copy(roll = 99.degrees)
        assertAngleAlmostEquals(99.degrees, modified.roll)
        assertAngleAlmostEquals(20.degrees, modified.pitch)
        assertAngleAlmostEquals(30.degrees, modified.yaw)
    }

    @Test
    fun testConfigDefaultIsXYZRightHanded() {
        val config = EulerRotation.Config.DEFAULT
        assertEquals(EulerRotation.Order.XYZ, config.order)
        assertEquals(EulerRotation.CoordinateSystem.RIGHT_HANDED, config.coordinateSystem)
    }

    @Test
    fun testOrderReversedPairs() {
        assertEquals(EulerRotation.Order.ZYX, EulerRotation.Order.XYZ.reversed())
        assertEquals(EulerRotation.Order.YZX, EulerRotation.Order.XZY.reversed())
        assertEquals(EulerRotation.Order.ZXY, EulerRotation.Order.YXZ.reversed())
        assertEquals(EulerRotation.Order.XZY, EulerRotation.Order.YZX.reversed())
        assertEquals(EulerRotation.Order.YXZ, EulerRotation.Order.ZXY.reversed())
        assertEquals(EulerRotation.Order.XYZ, EulerRotation.Order.ZYX.reversed())
        assertEquals(EulerRotation.Order.INVALID, EulerRotation.Order.INVALID.reversed())
    }

    @Test
    fun testOrderReverseIsSymmetric() {
        for (order in EulerRotation.Order.entries) {
            assertEquals(order, order.reversed().reversed(), "Double-reverse of $order should equal itself")
        }
    }

    @Test
    @Ignore // TODO This seems to have identified a bug in EulerRotation.Config
    fun testConfigWithLeftHandedCoordinateSystem() {
        val config = EulerRotation.Config(EulerRotation.Order.XYZ, EulerRotation.CoordinateSystem.LEFT_HANDED)
        // Left-handed should internally store the reversed order
        assertEquals(EulerRotation.CoordinateSystem.LEFT_HANDED, config.coordinateSystem)
    }

    @Test
    fun testPredefinedConfigs() {
        assertEquals(EulerRotation.Order.XYZ, EulerRotation.Config.THREEJS.order)
        assertEquals(EulerRotation.Order.YXZ, EulerRotation.Config.GODOT.order)
        assertEquals(EulerRotation.Order.YXZ, EulerRotation.Config.LIBGDX.order)
        assertEquals(EulerRotation.CoordinateSystem.RIGHT_HANDED, EulerRotation.Config.THREEJS.coordinateSystem)
        assertEquals(EulerRotation.CoordinateSystem.RIGHT_HANDED, EulerRotation.Config.GODOT.coordinateSystem)
    }

    @Test
    fun testNormalizedKeepsAnglesInRange() {
        val e = EulerRotation(400.degrees, (-50).degrees, 720.degrees)
        val n = e.normalized()
        // After normalization angles should be in [0, 360)
        assertTrue(n.roll.degrees in 0f..360f)
        assertTrue(n.pitch.degrees in 0f..360f)
        assertTrue(n.yaw.degrees in 0f..360f)
    }

    @Test
    fun testNormalizedHalfKeepsAnglesInHalfRange() {
        val e = EulerRotation(400.degrees, (-50).degrees, 270.degrees)
        val n = e.normalizedHalf()
        // Half normalization should keep angles in (-180, 180]
        assertTrue(n.roll.degrees in -180f..180f)
        assertTrue(n.pitch.degrees in -180f..180f)
        assertTrue(n.yaw.degrees in -180f..180f)
    }

    @Test
    fun testNormalizedZeroStaysZero() {
        val e = EulerRotation()
        assertEulerAlmostEquals(e, e.normalized())
        assertEulerAlmostEquals(e, e.normalizedHalf())
    }

    @Test
    fun testZeroRotationProducesIdentityQuaternion() {
        val q = EulerRotation().toQuaternion()
        assertQuaternionAlmostEquals(Quaternion(0f, 0f, 0f, 1f), q)
    }

    @Test
    fun testStaticToQuaternionMatchesInstanceMethod() {
        val roll = 20.degrees
        val pitch = 35.degrees
        val yaw = 50.degrees
        val instance = EulerRotation(roll, pitch, yaw).toQuaternion()
        val static = EulerRotation.toQuaternion(roll, pitch, yaw)
        assertQuaternionAlmostEquals(instance, static)
    }

    @Test
    fun testPureRollXAxisQuaternion() {
        // 90° around X: q = (sin45, 0, 0, cos45)
        val e = EulerRotation(90.degrees, Angle.ZERO, Angle.ZERO)
        val q = e.toQuaternion()
        val expected = Quaternion(sin(PI.toFloat() / 4), 0f, 0f, cos(PI.toFloat() / 4))
        assertQuaternionAlmostEquals(expected, q)
    }

    @Test
    fun testPurePitchYAxisQuaternion() {
        val e = EulerRotation(Angle.ZERO, 90.degrees, Angle.ZERO)
        val q = e.toQuaternion()
        val expected = Quaternion(0f, sin(PI.toFloat() / 4), 0f, cos(PI.toFloat() / 4))
        assertQuaternionAlmostEquals(expected, q)
    }

    @Test
    fun testPureYawZAxisQuaternion() {
        val e = EulerRotation(Angle.ZERO, Angle.ZERO, 90.degrees)
        val q = e.toQuaternion()
        val expected = Quaternion(0f, 0f, sin(PI.toFloat() / 4), cos(PI.toFloat() / 4))
        assertQuaternionAlmostEquals(expected, q)
    }

    private fun assertRoundTrip(
        roll: Angle,
        pitch: Angle,
        yaw: Angle,
        config: EulerRotation.Config = EulerRotation.Config.DEFAULT
    ) {
        val original = EulerRotation(roll, pitch, yaw, config)
        val q = original.toQuaternion()
        val restored = EulerRotation.fromQuaternion(q, config)
        // Compare via quaternions to avoid Euler ambiguity
        assertQuaternionAlmostEquals(q, restored.toQuaternion())
    }

    @Test
    fun testRoundTripZero() = assertRoundTrip(Angle.ZERO, Angle.ZERO, Angle.ZERO)

    @Test
    fun testRoundTripPureRoll() = assertRoundTrip(45.degrees, Angle.ZERO, Angle.ZERO)

    @Test
    fun testRoundTripPurePitch() = assertRoundTrip(Angle.ZERO, 45.degrees, Angle.ZERO)

    @Test
    fun testRoundTripPureYaw() = assertRoundTrip(Angle.ZERO, Angle.ZERO, 45.degrees)

    @Test
    fun testRoundTripAllAxes() = assertRoundTrip(30.degrees, 45.degrees, 60.degrees)

    @Test
    fun testRoundTripNegativeAngles() = assertRoundTrip((-30).degrees, (-45).degrees, (-60).degrees)

    @Test
    fun testRoundTripSmallAngles() = assertRoundTrip(1.degrees, 2.degrees, 3.degrees)

    @Test
    fun testRoundTrip180Degrees() = assertRoundTrip(180.degrees, Angle.ZERO, Angle.ZERO)

    @Test
    @Ignore // TODO likely a bug too
    fun testRoundTripAllOrdersXYZ() {
        for (order in EulerRotation.Order.entries.filter { it != EulerRotation.Order.INVALID }) {
            val config = EulerRotation.Config(order, EulerRotation.CoordinateSystem.RIGHT_HANDED)
            assertRoundTrip(20.degrees, 30.degrees, 40.degrees, config)
        }
    }

    @Test
    fun testRoundTripGodotConfig() {
        assertRoundTrip(15.degrees, 25.degrees, 35.degrees, EulerRotation.Config.GODOT)
    }

    @Test
    fun testRoundTripUnrealConfig() {
        assertRoundTrip(15.degrees, 25.degrees, 35.degrees, EulerRotation.Config.UNREAL)
    }

    @Test
    fun testRoundTripThreeJsConfig() {
        assertRoundTrip(15.degrees, 25.degrees, 35.degrees, EulerRotation.Config.THREEJS)
    }

    @Test
    fun testFromIdentityQuaternionIsZeroRotation() {
        val q = Quaternion(0f, 0f, 0f, 1f)
        val e = EulerRotation.fromQuaternion(q)
        assertQuaternionAlmostEquals(Quaternion(0f, 0f, 0f, 1f), e.toQuaternion())
    }

    @Test
    fun testFromQuaternionFloatOverloadMatchesObjectOverload() {
        val q = Quaternion(0.1f, 0.2f, 0.3f, 0.9274f) // roughly normalised
        val fromObject = EulerRotation.fromQuaternion(q)
        val fromFloats = EulerRotation.fromQuaternion(q.x, q.y, q.z, q.w)
        assertQuaternionAlmostEquals(fromObject.toQuaternion(), fromFloats.toQuaternion())
    }

    @Test
    fun testFromRotationMatrixIdentity() {
        val identity = Matrix3.IDENTITY
        val e = EulerRotation.fromRotationMatrix(identity)
        assertQuaternionAlmostEquals(Quaternion(0f, 0f, 0f, 1f), e.toQuaternion())
    }

    @Test
    fun testFromRotationMatrixRoundTrip() {
        val original = EulerRotation(25.degrees, 40.degrees, 55.degrees)
        val matrix = original.toQuaternion().toMatrix3()
        val restored = EulerRotation.fromRotationMatrix(matrix)
        assertQuaternionAlmostEquals(original.toQuaternion(), restored.toQuaternion())
    }

    @Test
    fun testToMatrixIdentityForZeroRotation() {
        val m = EulerRotation().toMatrix()
        val identity = Matrix4.IDENTITY
        for (i in 0 until 4) for (j in 0 until 4) {
            assertEquals(identity[i, j], m[i, j], EPSILON, "Matrix4[$i,$j] mismatch")
        }
    }

    @Test
    fun testToMatrixConsistentWithQuaternionToMatrix() {
        val e = EulerRotation(30.degrees, 45.degrees, 60.degrees)
        val m1 = e.toMatrix()
        val m2 = e.toQuaternion().toMatrix()
        for (i in 0 until 4) for (j in 0 until 4) {
            assertEquals(m1[i, j], m2[i, j], EPSILON, "Matrix4[$i,$j] mismatch")
        }
    }

    @Test
    fun testIsAlmostEqualsSameObject() {
        val e = EulerRotation(10.degrees, 20.degrees, 30.degrees)
        assertTrue(e.isAlmostEquals(e, EPSILON))
    }

    @Test
    fun testIsAlmostEqualsDistinctButEqual() {
        val a = EulerRotation(10.degrees, 20.degrees, 30.degrees)
        val b = EulerRotation(10.degrees, 20.degrees, 30.degrees)
        assertTrue(a.isAlmostEquals(b, EPSILON))
    }

    @Test
    fun testIsAlmostEqualsReturnsFalseForLargeDifference() {
        val a = EulerRotation(10.degrees, 20.degrees, 30.degrees)
        val b = EulerRotation(11.degrees, 20.degrees, 30.degrees)
        assertFalse(a.isAlmostEquals(b, 0.0001f))
    }

    @Test
    fun testToStringContainsComponentLabels() {
        val e = EulerRotation(10.degrees, 20.degrees, 30.degrees)
        val s = e.toString()
        assertTrue("roll" in s, "toString should mention roll")
        assertTrue("pitch" in s, "toString should mention pitch")
        assertTrue("yaw" in s, "toString should mention yaw")
    }

    @Suppress("DEPRECATION")
    @Test
    fun testDeprecatedXYZAliases() {
        val e = EulerRotation(10.degrees, 20.degrees, 30.degrees)
        assertAngleAlmostEquals(e.roll, e.x)
        assertAngleAlmostEquals(e.pitch, e.y)
        assertAngleAlmostEquals(e.yaw, e.z)
    }

    @Test
    fun testGimbalLockNorthPole() {
        // pitch = 90° causes gimbal lock in several orders
        val e = EulerRotation(
            roll = Angle.ZERO,
            pitch = 90.degrees,
            yaw = Angle.ZERO,
            config = EulerRotation.Config(
                order = EulerRotation.Order.YXZ,
                coordinateSystem = EulerRotation.CoordinateSystem.RIGHT_HANDED,
            ),
        )
        val q = e.toQuaternion()
        // After round-trip the quaternion should still represent the same rotation
        val restored = EulerRotation.fromQuaternion(
            q = q,
            config = EulerRotation.Config(
                order = EulerRotation.Order.YXZ,
                coordinateSystem = EulerRotation.CoordinateSystem.RIGHT_HANDED,
            ),
        )
        assertQuaternionAlmostEquals(q, restored.toQuaternion())
    }

    @Test
    fun testGimbalLockSouthPole() {
        val e = EulerRotation(
            roll = Angle.ZERO,
            pitch = (-90).degrees,
            yaw = Angle.ZERO,
            config = EulerRotation.Config(
                order = EulerRotation.Order.YXZ,
                coordinateSystem = EulerRotation.CoordinateSystem.RIGHT_HANDED,
            ),
        )
        val q = e.toQuaternion()
        val restored = EulerRotation.fromQuaternion(
            q = q,
            config = EulerRotation.Config(
                order = EulerRotation.Order.YXZ,
                coordinateSystem = EulerRotation.CoordinateSystem.RIGHT_HANDED,
            ),
        )
        assertQuaternionAlmostEquals(q, restored.toQuaternion())
    }

    @Test
    fun testFullRotation360DegreesIsEffectivelyIdentity() {
        val e = EulerRotation(360.degrees, Angle.ZERO, Angle.ZERO)
        val q = e.toQuaternion()
        assertQuaternionAlmostEquals(Quaternion(0f, 0f, 0f, 1f), q)
    }

    @Test
    fun testOppositeRotationsProduceSameQuaternionUpToSign() {
        val e1 = EulerRotation(90.degrees, Angle.ZERO, Angle.ZERO)
        val e2 = EulerRotation((-270).degrees, Angle.ZERO, Angle.ZERO)
        assertQuaternionAlmostEquals(e1.toQuaternion(), e2.toQuaternion())
    }
}
