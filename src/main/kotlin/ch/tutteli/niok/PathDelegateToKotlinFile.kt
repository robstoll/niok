package ch.tutteli.niok

import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path

/**
 * @see kotlin.io.appendBytes
 */
fun Path.appendBytes(array: ByteArray) =
    this.toFile().appendBytes(array)

/**
 * @See kotlin.io.appendText
 */
fun Path.appendText(text: String, charset: Charset = Charsets.UTF_8) =
    this.toFile().appendText(text, charset)

/**
 * @see kotlin.io.bufferedReader
 */
fun Path.bufferedReader(charset: Charset = Charsets.UTF_8, bufferSize: Int = DEFAULT_BUFFER_SIZE) =
    this.toFile().bufferedReader(charset, bufferSize)

/**
 * @see kotlin.io.bufferedWriter
 */
fun Path.bufferedWriter(charset: Charset = Charsets.UTF_8, bufferSize: Int = DEFAULT_BUFFER_SIZE) =
    this.toFile().bufferedWriter(charset, bufferSize)

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
) = this.toFile().copyRecursively(target.toFile(), overwrite) { file, t -> onError(file.toPath(), t) }

// TODO shouldn't we use Files.copy? The main difference is, that it would provide CopyOption and not only an overwrite flag.
/**
 * @see kotlin.io.copyTo
 */
fun Path.copyTo(target: Path, overwrite: Boolean = false, bufferSize: Int = DEFAULT_BUFFER_SIZE) =
    this.toFile().copyTo(target.toFile(), overwrite, bufferSize)

/**
 * @see kotlin.io.deleteRecursively
 */
fun Path.deleteRecursively() = this.toFile().deleteRecursively()

//
// endsWith is already provided by Path
//

/**
 * @see kotlin.io.extension
 */
val Path.extension get() = this.toFile().extension

/**
 * @see kotlin.io.forEachBlock
 */
fun Path.forEachBlock(action: (buffer: ByteArray, bytesRead: Int) -> Unit) =
    this.toFile().forEachBlock(action)

/**
 * @see kotlin.io.forEachBlock
 */
fun Path.forEachBlock(blockSize: Int, action: (buffer: ByteArray, bytesRead: Int) -> Unit) =
    this.toFile().forEachBlock(blockSize, action)

/**
 * @see kotlin.io.forEachLine
 */
fun Path.forEachLine(charset: Charset = Charsets.UTF_8, action: (line: String) -> Unit) =
    this.toFile().forEachLine(charset, action)

/**
 * @see kotlin.io.inputStream
 */
fun Path.inputStream() = this.toFile().inputStream()

/**
 * @see kotlin.io.invariantSeparatorsPath
 */
val Path.invariantSeparatorsPath get() = this.toFile().invariantSeparatorsPath

/**
 * @see kotlin.io.isRooted
 */
val Path.isRooted get() = toFile().isRooted

/**
 * @see kotlin.io.nameWithoutExtension
 */
val Path.nameWithoutExtension get() = this.toFile().nameWithoutExtension

//
// normalize is already provided by Path
//

/**
 * @see kotlin.io.outputStream
 */
fun Path.outputStream() = this.toFile().outputStream()

/**
 * @see kotlin.io.printWriter
 */
fun Path.printWriter(charset: Charset = Charsets.UTF_8) =
    this.toFile().printWriter(charset)

/**
 * @see kotlin.io.readBytes
 */
fun Path.readBytes() = this.toFile().readBytes()

/**
 * @see kotlin.io.readLines
 */
fun Path.readLines() = this.toFile().readLines()

/**
 * @see kotlin.io.reader
 */
fun Path.reader(charset: Charset = Charsets.UTF_8) = this.toFile().reader(charset)

//TODO check if File.relativeTo is the same as Path.relativize
/**
 * @see kotlin.io.relativeTo
 */
fun Path.relativeTo(other: Path) = this.toFile().relativeTo(other.toFile())

/**
 * @see kotlin.io.relativeToOrNull
 */
fun Path.relativeToOrNull(other: Path) = this.toFile().relativeToOrNull(other.toFile())

/**
 * @see kotlin.io.relativeToOrSelf
 */
fun Path.relativeToOrSelf(other: Path) = this.toFile().relativeToOrSelf(other.toFile())

//
// resolve and resolveSibling is already provided by Path
//

//
// startsWith is already provided by Path
//

/**
 * @see kotlin.io.toRelativeString
 */
fun Path.toRelativeString(base: Path) = this.toFile().toRelativeString(base.toFile())

/**
 * @see kotlin.io.useLines
 */
fun <T> Path.useLines(charset: Charset = Charsets.UTF_8, block: (Sequence<String>) -> T) =
    this.toFile().useLines(charset, block)

/**
 * @see kotlin.io.writeBytes
 */
fun Path.writeBytes(array: ByteArray) = this.toFile().writeBytes(array)

/**
 * @see kotlin.io.writeText
 */
fun Path.writeText(text: String, charset: Charset = Charsets.UTF_8) =
    this.toFile().writeText(text, charset)

/**
 * @see kotlin.io.writer
 */
fun Path.writer(charset: Charset = Charsets.UTF_8) = this.toFile().writer(charset)
