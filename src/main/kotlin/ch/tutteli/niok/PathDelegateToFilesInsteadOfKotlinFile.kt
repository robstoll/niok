@file:Suppress("NOTHING_TO_INLINE")

package ch.tutteli.niok

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Path

/**
 * @see Files.newBufferedReader
 */
inline fun Path.newBufferedReader(charset: Charset = Charsets.UTF_8): BufferedReader =
    Files.newBufferedReader(this, charset)

/**
 * @see Files.newBufferedWriter
 */
inline fun Path.newBufferedWriter(charset: Charset = Charsets.UTF_8, vararg openOptions: OpenOption): BufferedWriter =
    Files.newBufferedWriter(this, charset, *openOptions)

/**
 * @see Files.copy
 */
inline fun Path.copyTo(target: Path, vararg copyOptions: CopyOption): Path =
    Files.copy(this, target, *copyOptions)

/**
 * @see Files.newInputStream
 */
inline fun Path.newInputStream(vararg openOptions: OpenOption): InputStream =
    Files.newInputStream(this, *openOptions)

/**
 * @see Files.newOutputStream
 */
inline fun Path.newOutputStream(vararg openOptions: OpenOption): OutputStream =
    Files.newOutputStream(this, *openOptions)

/**
 * @see Files.readAllBytes
 */
inline fun Path.readAllBytes(): ByteArray = Files.readAllBytes(this)

/**
 * @see Files.readAllLines
 */
inline fun Path.readAllLines(charset: Charset = Charsets.UTF_8): List<String> = Files.readAllLines(this, charset)

/**
 * @see Files.write
 */
inline fun Path.writeBytes(array: ByteArray, vararg openOptions: OpenOption): Path =
    Files.write(this, array, *openOptions)
