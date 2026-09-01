package korlibs.platform

import java.util.Locale

internal actual val languages: List<String> get() = listOf(Locale.getDefault().isO3Language)
