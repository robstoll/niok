@file:Suppress("NOTHING_TO_INLINE")

package ch.tutteli.niok

import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.FileAttribute

/**
 * Returns [Path.toAbsolutePath].[Path.toString].
 */
val Path.absolutePathAsString: String
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
fun Path.newDirectory(dir: String, vararg fileAttributes: FileAttribute<*>) =
    resolve(dir).createDirectory(*fileAttributes)

/**
 * Resolve the given [file] and calls [createFile].
 */
fun Path.newFile(file: String, vararg fileAttributes: FileAttribute<*>) =
    resolve(file).createFile(*fileAttributes)

/**
 * Delegates to [createDirectory] and swallows a potential [java.nio.file.FileAlreadyExistsException].
 */
inline fun Path.createDirectoryIfNotExists(vararg fileAttributes: FileAttribute<*>): Path =
    swallowFileAlreadyExistsException { createDirectory(*fileAttributes) }

/**
 * Delegates to [createFile] and swallows a potential [java.nio.file.FileAlreadyExistsException].
 */
inline fun Path.createFileIfNotExists(vararg fileAttributes: FileAttribute<*>): Path =
    try {
        swallowFileAlreadyExistsException { createFile(*fileAttributes) }
    } catch (e: java.nio.file.AccessDeniedException) {
        // windows seems to throw an AccessDeniedException in case it was a directory
        try {
            if (isDirectory) this else throw e
        } catch (es: SecurityException) {
            throw e
        }
    }


@PublishedApi
@Suppress("FunctionMaxLength")
internal inline fun Path.swallowFileAlreadyExistsException(action: Path.() -> Unit) =
    apply {
        try {
            action(this)
        } catch (ignored: java.nio.file.FileAlreadyExistsException) {
            // can be ignored
        }
    }

/**
 * Deletes the directory recursively and creates a new empty one at the same place.
 *
 * This function does not follow symlinks, if the path points to a directory via symlink then this function throws.
 *
 * @throws IllegalStateException In case this Path is not a directory - also throws if it is a symbolic link
 *   pointing to a directory.
 */
fun Path.reCreateDirectory() = apply {
    if (Files.isDirectory(this, LinkOption.NOFOLLOW_LINKS)) {
        deleteRecursively()
        createDirectory()
    } else {
        throw IllegalStateException("$absolutePathAsString is not a directory")
    }
}

