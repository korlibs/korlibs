package korlibs.time

import kotlin.time.*

public fun Duration.convertRange(srcMin: Duration, srcMax: Duration, dstMin: Duration, dstMax: Duration): Duration = (dstMin + (dstMax - dstMin) * ((this - srcMin) / (srcMax - srcMin)))
public fun DateTime.convertRange(srcMin: DateTime, srcMax: DateTime, dstMin: DateTime, dstMax: DateTime): DateTime = (dstMin + (dstMax - dstMin) * ((this - srcMin) / (srcMax - srcMin)))
