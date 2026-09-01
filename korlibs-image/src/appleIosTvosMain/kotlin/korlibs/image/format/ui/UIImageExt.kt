@file:OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)

package korlibs.image.format.ui

import cnames.structs.CGContext
import korlibs.image.bitmap.Bitmap
import korlibs.image.bitmap.Bitmap32
import korlibs.image.format.cg.CGRectMakeExt
import korlibs.image.format.cg.transferBitmap32CGContext
import korlibs.image.format.cg.transferBitmap32ToCGImage
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGImageRelease
import platform.UIKit.UIGraphicsBeginImageContext
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetCurrentContext
import platform.UIKit.UIImage

fun Bitmap.toUIImage(): UIImage {
    val cgImage = transferBitmap32ToCGImage(this.toBMP32IfRequired())
    try {
        return UIImage(cGImage = cgImage)
    } finally {
        CGImageRelease(cgImage)
    }
}

fun UIImage.toBitmap32(): Bitmap32 {
    val out = Bitmap32(this.size.useContents { width }.toInt(), this.size.useContents { height }.toInt(), premultiplied = true)
    UIGraphicsBeginImageContext(this.size)
    try {
        val ctx: CPointer<CGContext>? = UIGraphicsGetCurrentContext(); // here you don't need this reference for the context but if you want to use in the future for drawing anything else on the context you could get it for it
        this.drawInRect(CGRectMakeExt(0, 0, out.width, out.height))
        transferBitmap32CGContext(out, ctx, toBitmap = true)
        return out
    } finally {
        UIGraphicsEndImageContext()
    }
}
