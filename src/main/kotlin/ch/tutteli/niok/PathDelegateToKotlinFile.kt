@file:Suppress("NOTHING_TO_INLINE")

package ch.tutteli.niok

import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.nio.charset.Charset
import java.nio.file.Path

/**
 * @see kotlin.io.appendBytes
 */
fun Path.appendBytes(array: ByteArray): Unit =
    this.toFile().appendBytes(array)

/**
 * @See kotlin.io.appendText
 */
fun Path.appendText(text: String, charset: Charset = Charsets.UTF_8): Unit =
    this.toFile().appendText(text, charset)

//
// we use Files.newBufferedReader/Writer instead of kotlin's bufferedReader/Writer
//

// TODO there are probably better ways than reusing kotlin copyRecursively
// which implements kind of an own FileTreeWalker.
// On the other hand we can be sure it behaves the same way
/**
 * @see kotlin.io.copyRecursively
 */
fun Path.copyRecursively(
    target: Path,
    overwrite: Boolean = false,
    onError: (Path, IOException) -> OnErrorAction = { _, exception -> throw exception }
): Boolean = this.toFile().copyRecursively(target.toFile(), overwrite) { file, t -> onError(file.toPath(), t) }

//
// we use Files.copy instead of kotlin's copyTo
//

/**
 * @see kotlin.io.deleteRecursively
 */
fun Path.deleteRecursively(): Boolean = this.toFile().deleteRecursively()

//
// endsWith is already provided by Path
//

/**
 * @see kotlin.io.extension
 */
val Path.extension get() : String = this.toFile().extension

/**
 * @see kotlin.io.forEachBlock
 */
fun Path.forEachBlock(action: (buffer: ByteArray, bytesRead: Int) -> Unit): Unit =
    this.toFile().forEachBlock(action)

/**
 * @see kotlin.io.forEachBlock
 */
fun Path.forEachBlock(blockSize: Int, action: (buffer: ByteArray, bytesRead: Int) -> Unit): Unit =
    this.toFile().forEachBlock(blockSize, action)

/**
 * @see kotlin.io.forEachLine
 */
fun Path.forEachLine(charset: Charset = Charsets.UTF_8, action: (line: String) -> Unit): Unit =
    this.toFile().forEachLine(charset, action)

//
// we use Files.newInputStream instead of kotlin's inputStream
//

/**
 * @see kotlin.io.invariantSeparatorsPath
 */
val Path.invariantSeparatorsPath get() : String = this.toFile().invariantSeparatorsPath

/**
 * @see kotlin.io.isRooted
 */
val Path.isRooted get(): Boolean = toFile().isRooted

/**
 * @see kotlin.io.nameWithoutExtension
 */
val Path.nameWithoutExtension get(): String = this.toFile().nameWithoutExtension

//
// normalize is already provided by Path
//

//
// we use Files.newOutputStream instead of kotlin's outputStream
//

/**
 * @see kotlin.io.printWriter
 */
inline fun Path.printWriter(charset: Charset = Charsets.UTF_8): PrintWriter =
    this.toFile().printWriter(charset)

//
// we use Files.readAllBytes instead of kotlin's readBytes
//

//
// we use Files.readAllLines instead of kotlin's readBytes
//

/**
 * @see kotlin.io.reader
 */
inline fun Path.reader(charset: Charset = Charsets.UTF_8): InputStreamReader =
    this.toFile().reader(charset)

//TODO check if File.relativeTo is the same as Path.relativize
/**
 * @see kotlin.io.relativeTo
 */
fun Path.relativeTo(other: Path): Path =
    this.toFile().relativeTo(other.toFile()).toPath()

/**
 * @see kotlin.io.relativeToOrNull
 */
fun Path.relativeToOrNull(other: Path): Path? =
    this.toFile().relativeToOrNull(other.toFile())?.toPath()

/**
 * @see kotlin.io.relativeToOrSelf
 */
fun Path.relativeToOrSelf(other: Path): Path =
    this.toFile().relativeToOrSelf(other.toFile()).toPath()

//
// resolve and resolveSibling is already provided by Path
//

//
// startsWith is already provided by Path
//

/**
 * @see kotlin.io.toRelativeString
 */
fun Path.toRelativeString(base: Path): String =
    this.toFile().toRelativeString(base.toFile())

/**
 * @see kotlin.io.useLines
 */
inline fun <T> Path.useLines(charset: Charset = Charsets.UTF_8, block: (Sequence<String>) -> T): T =
    this.toFile().useLines(charset, block)

//
// we use Files.writeByte instead of kotlin's writeByte
//

/**
 * @see kotlin.io.writeText
 */
fun Path.writeText(text: String, charset: Charset = Charsets.UTF_8) =
    this.toFile().writeText(text, charset)

/**
 * @see kotlin.io.writer
 */
inline fun Path.writer(charset: Charset = Charsets.UTF_8) = this.toFile().writer(charset)
