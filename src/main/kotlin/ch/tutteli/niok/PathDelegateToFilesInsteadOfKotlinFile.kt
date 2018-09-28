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
 * Delegates to [Files.newBufferedReader].
 */
inline fun Path.newBufferedReader(charset: Charset = Charsets.UTF_8): BufferedReader =
    Files.newBufferedReader(this, charset)

/**
 * Delegates to [Files.newBufferedWriter].
 */
inline fun Path.newBufferedWriter(charset: Charset = Charsets.UTF_8, vararg openOptions: OpenOption): BufferedWriter =
    Files.newBufferedWriter(this, charset, *openOptions)

/**
 * Delegates to [Files.copy].
 */
inline fun Path.copyTo(target: Path, vararg copyOptions: CopyOption): Path =
    Files.copy(this, target, *copyOptions)

/**
 * Delegates to [Files.newInputStream].
 */
inline fun Path.newInputStream(vararg openOptions: OpenOption): InputStream =
    Files.newInputStream(this, *openOptions)

/**
 * Delegates to [Files.newOutputStream].
 */
inline fun Path.newOutputStream(vararg openOptions: OpenOption): OutputStream =
    Files.newOutputStream(this, *openOptions)

/**
 * Delegates to [Files.readAllBytes].
 */
inline fun Path.readAllBytes(): ByteArray = Files.readAllBytes(this)

/**
 * Delegates to [Files.readAllLines].
 */
inline fun Path.readAllLines(charset: Charset = Charsets.UTF_8): List<String> = Files.readAllLines(this, charset)

/**
 * Delegates to [Files.write].
 */
inline fun Path.writeBytes(array: ByteArray, vararg openOptions: OpenOption): Path =
    Files.write(this, array, *openOptions)
