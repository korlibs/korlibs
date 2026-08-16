package korlibs.image.font

class CharacterSet(val codePoints: IntArray) {
    constructor(chars: String) : this(chars.map { it.toInt() }.toIntArray())
    operator fun plus(other: CharacterSet) = CharacterSet(this.codePoints + other.codePoints)

    companion object {
        val SPACE = CharacterSet(" ")
        val UPPERCASE = CharacterSet(('A'..'Z').joinToString(""))
        val LOWERCASE = CharacterSet(('a'..'z').joinToString(""))
        val NUMBERS = CharacterSet(('0'..'9').joinToString(""))
        val PUNCTUATION = CharacterSet("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}")
        val LATIN_BASIC = CharacterSet("çÇ ñÑ åÅ æÆ ÿ ¢£¥Pª°¿¬½¼¡«»ßµø±÷°·.² áéíóúäëïöüàèìòùâêîôû ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ")
        val CYRILLIC = CharacterSet("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ")
        val LATIN_ALL = SPACE + UPPERCASE + LOWERCASE + NUMBERS + PUNCTUATION + LATIN_BASIC
    }
}
