package korlibs.image.bitmap

import korlibs.math.geom.Vector2I

data class BitmapWithHotspot<T : Bitmap> constructor (val bitmap: T, val hotspot: Vector2I) {
}
