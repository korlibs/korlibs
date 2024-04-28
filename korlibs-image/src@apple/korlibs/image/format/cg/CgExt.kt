@file:OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)

package korlibs.image.format.cg

import korlibs.math.geom.*
import kotlinx.cinterop.*
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake

@Deprecated("")
fun MRectangle.toCG(): CValue<CGRect> = CGRectMake(x.cg, y.cg, width.cg, height.cg)
fun Rectangle.toCG(): CValue<CGRect> = CGRectMake(x.cg, y.cg, width.cg, height.cg)
