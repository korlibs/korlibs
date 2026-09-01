@file:OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)

package korlibs.image.format.cg

import korlibs.image.bitmap.Bitmap32
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.convert
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGContextDrawImage
import platform.CoreGraphics.CGImageAlphaInfo
import platform.CoreGraphics.CGImageGetHeight
import platform.CoreGraphics.CGImageGetWidth
import platform.CoreGraphics.CGImageRef

fun Bitmap32.toCGImage(): CGImageRef? {
    return transferBitmap32ToCGImage(this, null)
}

fun CGImageRef.toBitmap32(): Bitmap32 {
    val image = this
    val width = CGImageGetWidth(image).toInt()
    val height = CGImageGetHeight(image).toInt()
    val colorSpace = CGColorSpaceCreateDeviceRGB()
    val ctx = CGBitmapContextCreate(
        null, width.convert(), height.convert(),
        8.convert(), 0.convert(), colorSpace,
        CGImageAlphaInfo.kCGImageAlphaPremultipliedLast.value
    )
    CGContextDrawImage(ctx, CGRectMakeExt(0, 0, width, height), image)
    val out = Bitmap32(width, height, premultiplied = true)
    transferBitmap32CGContext(out, ctx, toBitmap = true)
    return out
}
