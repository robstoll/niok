@file:Suppress("NOTHING_TO_INLINE")

package ch.tutteli.niok

import java.nio.charset.Charset
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Path

/**
 * @see Files.newBufferedReader
 */
inline fun Path.bufferedReader(charset: Charset = Charsets.UTF_8, vararg openOptions: OpenOption) =
    Files.newBufferedWriter(this, charset, *openOptions)

/**
 * @see Files.newBufferedWriter
 */
inline fun Path.bufferedWriter(charset: Charset = Charsets.UTF_8, vararg openOptions: OpenOption) =
    Files.newBufferedWriter(this, charset, *openOptions)

/**
 * @see Files.copy
 */
inline fun Path.copyTo(target: Path, vararg copyOptions: CopyOption) =
    Files.copy(this, target, *copyOptions)

/**
 * @see Files.newInputStream
 */
inline fun Path.inputStream(vararg openOptions: OpenOption) =
    Files.newInputStream(this, *openOptions)

/**
 * @see Files.newOutputStream
 */
inline fun Path.outputStream(vararg openOptions: OpenOption) =
    Files.newOutputStream(this, *openOptions)

/**
 * @see Files.readAllBytes
 */
inline fun Path.readBytes(): ByteArray = Files.readAllBytes(this)

/**
 * @see Files.readAllLines
 */
inline fun Path.readLines(charset: Charset = Charsets.UTF_8) = Files.readAllLines(this, charset)

/**
 * @see Files.write
 */
inline fun Path.writeBytes(array: ByteArray, vararg openOptions: OpenOption): Path =
    Files.write(this, array, *openOptions)

