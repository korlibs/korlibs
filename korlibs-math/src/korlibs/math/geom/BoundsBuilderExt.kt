package korlibs.math.geom

operator fun BoundsBuilder.plus(p: PointList): BoundsBuilder {
    var bb = this
    p.fastForEach { bb += it }
    return bb
}
