package korlibs.math.geom

import korlibs.math.geom.shape.*
import korlibs.math.geom.vector.*

fun RoundRectangle.toVectorPath(): VectorPath = buildVectorPath { roundRect(this@toVectorPath) }
fun Polygon.toVectorPath(): VectorPath = buildVectorPath { polygon(points, close = true) }
fun Polyline.toVectorPath(): VectorPath = buildVectorPath { polygon(points, close = false) }
fun Rectangle.toVectorPath(): VectorPath = buildVectorPath { rect(this@toVectorPath) }
fun Line.toVectorPath(): VectorPath = buildVectorPath { moveTo(a); lineTo(b) }
fun Circle.toVectorPath(): VectorPath = buildVectorPath { circle(this@toVectorPath.center, this@toVectorPath.radius) }
fun Ellipse.toVectorPath(): VectorPath = buildVectorPath { ellipse(this@toVectorPath.center, this@toVectorPath.radius) }
