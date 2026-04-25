package korlibs.io.lang

/**
 * A common interface for enums and enum-like classes.
 */
interface EnumLike<T : EnumLike<T>> {
    object Scope

    companion object {
        /** Gets all the values of an enum. */
        inline fun <reified T : Enum<T>> getValues(enum: T): List<T> = enumValues<T>().toList()
        /** Gets all the values of an enum-like class. */
        inline fun <reified T : EnumLike<T>> getValues(enumLike: EnumLike<T>): List<T> = enumLike.run { Scope.getValues() }
    }

    /** Gets all the values of an enum-like class. */
    fun Scope.getValues(): List<T>
}
