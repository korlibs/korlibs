package korlibs.annotations

import kotlin.test.Test

@Keep
@KeepNames
class AnnotationsTest : korlibs.Serializable {
    @Test
    fun test() {
        demo()
    }

    @Keep
    @KeepNames
    fun demo(@DeprecatedParameter("Do not use it anymore") a: String = "") {

    }
}