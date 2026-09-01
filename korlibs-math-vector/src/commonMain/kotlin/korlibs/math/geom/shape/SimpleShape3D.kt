package korlibs.math.geom.shape

import korlibs.math.geom.Vector3F

interface SimpleShape3D {
    val center: Vector3F
    val volume: Float
}
