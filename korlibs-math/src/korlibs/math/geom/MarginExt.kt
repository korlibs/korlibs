package korlibs.math.geom

import korlibs.number.*

val Margin.topFixed: FixedShort get() = top.toFixedShort()
val Margin.rightFixed: FixedShort get() = right.toFixedShort()
val Margin.bottomFixed: FixedShort get() = bottom.toFixedShort()
val Margin.leftFixed: FixedShort get() = left.toFixedShort()

val Margin.leftPlusRightFixed: FixedShort get() = leftFixed + rightFixed
val Margin.topPlusBottomFixed: FixedShort get() = topFixed + bottomFixed
val Margin.horizontalFixed: FixedShort get() = (leftFixed + rightFixed) / 2.toFixedShort()
val Margin.verticalFixed: FixedShort get() = (topFixed + bottomFixed) / 2.toFixedShort()
