package ch.tutteli.niok

import java.nio.file.Path

val Path.absolutePathAsString
    get() : String = if (isAbsolute) toString() else toAbsolutePath().toString()

val Path.fileNameAsString get() = fileName.toString()
