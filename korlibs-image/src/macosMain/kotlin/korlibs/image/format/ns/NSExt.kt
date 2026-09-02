@file:OptIn(ExperimentalForeignApi::class)

package korlibs.image.format.ns

import korlibs.image.bitmap.Bitmap32
import korlibs.image.format.cg.cg
import korlibs.image.format.cg.transferBitmap32CGContext
import korlibs.math.geom.Point
import korlibs.math.geom.Rectangle
import korlibs.math.geom.Vector2I
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import kotlinx.cinterop.useContents
import platform.AppKit.NSImage
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGBitmapContextCreateImage
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGColorSpaceRelease
import platform.CoreGraphics.CGContextRelease
import platform.CoreGraphics.CGImageAlphaInfo
import platform.CoreGraphics.CGImageRelease
import platform.Foundation.NSMakePoint
import platform.Foundation.NSMakeSize
import platform.Foundation.NSPoint
import platform.Foundation.NSRect

fun Bitmap32.toNSImage(): NSImage {
    val bmp = this
    val colorSpace = CGColorSpaceCreateDeviceRGB()
    val ctx = CGBitmapContextCreate(
        null, bmp.width.convert(), bmp.height.convert(),
        8.convert(), 0.convert(), colorSpace,
        CGImageAlphaInfo.kCGImageAlphaPremultipliedLast.value
    )
    val image = CGBitmapContextCreateImage(ctx)
    try {
        transferBitmap32CGContext(this, ctx, toBitmap = false)
        return NSImage(image, NSMakeSize(bmp.width.toDouble(), bmp.height.toDouble()))
    } finally {
        CGImageRelease(image)
        CGContextRelease(ctx)
        CGColorSpaceRelease(colorSpace)
    }
}

//fun NSImage.toBitmap32(): Bitmap32 { TODO() }

fun Point.toNSPoint(): CValue<NSPoint> = NSMakePoint(x.toDouble(), y.cg.toDouble())
fun Vector2I.toNSPoint(): CValue<NSPoint> = NSMakePoint(x.cg.toDouble(), y.cg.toDouble())
fun CValue<NSPoint>.toPoint(): Point = useContents { Point(this.x, this.y) }
fun CValue<NSRect>.toRectangle(): Rectangle = useContents { Rectangle(this.origin.x, this.origin.y, this.size.width, this.size.height) }
