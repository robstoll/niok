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
inline fun Path.newBufferedReader(charset: Charset = Charsets.UTF_8, vararg openOptions: OpenOption) =
    Files.newBufferedWriter(this, charset, *openOptions)

/**
 * @see Files.newBufferedWriter
 */
inline fun Path.newBufferedWriter(charset: Charset = Charsets.UTF_8, vararg openOptions: OpenOption) =
    Files.newBufferedWriter(this, charset, *openOptions)

/**
 * @see Files.copy
 */
inline fun Path.copyTo(target: Path, vararg copyOptions: CopyOption) =
    Files.copy(this, target, *copyOptions)

/**
 * @see Files.newInputStream
 */
inline fun Path.newInputStream(vararg openOptions: OpenOption) =
    Files.newInputStream(this, *openOptions)

/**
 * @see Files.newOutputStream
 */
inline fun Path.newOutputStream(vararg openOptions: OpenOption) =
    Files.newOutputStream(this, *openOptions)

/**
 * @see Files.readAllBytes
 */
inline fun Path.readAllBytes(): ByteArray = Files.readAllBytes(this)

/**
 * @see Files.readAllLines
 */
inline fun Path.readAllLines(charset: Charset = Charsets.UTF_8) = Files.readAllLines(this, charset)

/**
 * @see Files.write
 */
inline fun Path.writeBytes(array: ByteArray, vararg openOptions: OpenOption): Path =
    Files.write(this, array, *openOptions)
