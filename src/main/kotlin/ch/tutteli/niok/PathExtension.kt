@file:Suppress("NOTHING_TO_INLINE")

package ch.tutteli.niok

import java.nio.file.Path
import java.nio.file.Paths

/**
 * Returns [Path.toAbsolutePath].[Path.toString].
 */
val <T: Path> T.absolutePathAsString
    get() : String = if (isAbsolute) toString() else toAbsolutePath().toString()

/**
 * Returns [Path.getFileName].[Path.toString].
 */
val <T: Path> T.fileNameAsString get() = fileName.toString()

/**
 * Converts the given [dir], [first] and [more] into a [Path] and [Path.resolve]s it.
 *
 * In other words it is a shortcut for using [Paths.get] in combination with [Path.resolve].
 *
 * @return The new resolved [Path].
 */
inline fun <T: Path> T.resolve(dir: String, first: String, vararg more: String): Path =
    resolve(Paths.get(dir, first, *more))
