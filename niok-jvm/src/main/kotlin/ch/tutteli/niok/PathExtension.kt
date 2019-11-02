@file:Suppress("NOTHING_TO_INLINE")

package ch.tutteli.niok

import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.FileAttribute

/**
 * Returns [Path.toAbsolutePath].[Path.toString].
 */
val Path.absolutePathAsString  : String
    get() = if (isAbsolute) toString() else toAbsolutePath().toString()

/**
 * Returns [Path.getFileName].[Path.toString].
 */
val Path.fileNameAsString: String get() = fileName.toString()

/**
 * Converts the given [dir], [first] and [more] into a [Path] and [Path.resolve]s it.
 *
 * In other words it is a shortcut for using [Paths.get] in combination with [Path.resolve].
 *
 * @return The new resolved [Path].
 */
inline fun Path.resolve(dir: String, first: String, vararg more: String): Path =
    resolve(Paths.get(dir, first, *more))

/**
 * Resolve the given [dir] and calls [createDirectory].
 */
fun Path.newDirectory(dir: String, vararg fileAttributes: FileAttribute<*>) = resolve(dir).createDirectory(*fileAttributes)

/**
 * Resolve the given [file] and calls [createDirectory].
 */
fun Path.newFile(file: String, vararg fileAttributes: FileAttribute<*>) = resolve(file).createFile(*fileAttributes)
