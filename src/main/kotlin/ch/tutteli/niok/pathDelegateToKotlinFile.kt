@file:Suppress("NOTHING_TO_INLINE")

package ch.tutteli.niok

import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.nio.charset.Charset
import java.nio.file.Path

/**
 * Delegates to [kotlin.io.appendBytes].
 */
fun Path.appendBytes(array: ByteArray): Unit =
    this.toFile().appendBytes(array)

/**
 * Delegates to [kotlin.io.appendText].
 */
fun Path.appendText(text: String, charset: Charset = Charsets.UTF_8): Unit =
    this.toFile().appendText(text, charset)

//
// we use Files.newBufferedReader/Writer instead of kotlin's bufferedReader/Writer
//

/**
 * Delegates to [kotlin.io.copyRecursively].
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
 * Delegates to [kotlin.io.deleteRecursively].
 */
fun Path.deleteRecursively(): Boolean = this.toFile().deleteRecursively()

//
// endsWith is already provided by Path
//

/**
 * Delegates to [kotlin.io.extension].
 */
val Path.extension get() : String = this.toFile().extension

/**
 * Delegates to [kotlin.io.forEachBlock].
 */
fun Path.forEachBlock(action: (buffer: ByteArray, bytesRead: Int) -> Unit): Unit =
    this.toFile().forEachBlock(action)

/**
 * Delegates to [kotlin.io.forEachBlock].
 */
fun Path.forEachBlock(blockSize: Int, action: (buffer: ByteArray, bytesRead: Int) -> Unit): Unit =
    this.toFile().forEachBlock(blockSize, action)

/**
 * Delegates to [kotlin.io.forEachLine].
 */
fun Path.forEachLine(charset: Charset = Charsets.UTF_8, action: (line: String) -> Unit): Unit =
    this.toFile().forEachLine(charset, action)

//
// we use Files.newInputStream instead of kotlin's inputStream
//

/**
 * Delegates to [kotlin.io.invariantSeparatorsPath].
 */
val Path.invariantSeparatorsPath get() : String = this.toFile().invariantSeparatorsPath

/**
 * Delegates to [kotlin.io.isRooted].
 */
val Path.isRooted get(): Boolean = toFile().isRooted

/**
 * Delegates to [kotlin.io.nameWithoutExtension].
 */
@Deprecated(
    "use fileNameWithoutExtension instead; will be removed with 2.0.0",
    ReplaceWith("this.fileNameWithoutExtension")
)
val Path.nameWithoutExtension
    get(): String = this.toFile().nameWithoutExtension

/**
 * Delegates to [kotlin.io.nameWithoutExtension].
 */
val Path.fileNameWithoutExtension: String get() = fileName.toFile().nameWithoutExtension

//
// normalize is already provided by Path
//

//
// we use Files.newOutputStream instead of kotlin's outputStream
//

/**
 * Delegates to [kotlin.io.printWriter].
 */
inline fun Path.printWriter(charset: Charset = Charsets.UTF_8): PrintWriter =
    this.toFile().printWriter(charset)

//
// we use Files.readAllBytes instead of kotlin's readBytes
//

//
// we use Files.readAllLines instead of kotlin's readBytes
//

//
// we implement own readText method based on Files.readAllBytes()
//

/**
 * Delegates to [kotlin.io.reader].
 */
inline fun Path.reader(charset: Charset = Charsets.UTF_8): InputStreamReader =
    this.toFile().reader(charset)

/**
 * Delegates to [kotlin.io.relativeToOrNull].
 *
 * Notice, that this function is not equivalent to [Path.relativize].
 * This function calculates the relative path from this path to [other] where [Path.relativize] calculates the relative
 * path from [other] to this path.
 * However, `other.relativeTo(this)` is not necessarily the same as `this.relativize(other)`. There are small subtleties
 * when it comes to `../` and `./` in one of the paths and depending on the JVM in use. Following an example using
 * Oracle's JDK8
 * ```
 * val a = Paths.get("a")
 * val b = Paths.get("./b")
 * a.relativeTo(b) // ../a
 * b.relativize(a) // .././a
 * ```
 * Notice further, that [Path.relativize] seems to contain a bug (in Oracle's JDK8, seems to be fixed in JDK 9)
 * when this path contains `./`. Following an example
 * ```
 * Paths.get("./a").relativize(Paths.get("b")) // results in ../../b instead of ../b (or .././b)
 * ```
 * See https://bugs.java.com/bugdatabase/view_bug.do?bug_id=9057443 for further information.
 * Hence, in case you want to use the result in [Path.resolve] later on,
 * then you should prefer [Path.relativeTo] over [Path.relativize].
 */
fun Path.relativeTo(other: Path): Path =
    this.toFile().relativeTo(other.toFile()).toPath()

/**
 * Delegates to [kotlin.io.relativeToOrNull].
 */
fun Path.relativeToOrNull(other: Path): Path? =
    this.toFile().relativeToOrNull(other.toFile())?.toPath()

/**
 * Delegates to [kotlin.io.relativeToOrSelf].
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
 * Delegates to [kotlin.io.toRelativeString].
 */
fun Path.toRelativeString(base: Path): String =
    this.toFile().toRelativeString(base.toFile())

/**
 * Delegates to [kotlin.io.useLines].
 */
inline fun <T> Path.useLines(charset: Charset = Charsets.UTF_8, block: (Sequence<String>) -> T): T =
    this.toFile().useLines(charset, block)

//
// we use Files.writeByte instead of kotlin's writeByte
//

/**
 * Delegates to [kotlin.io.writeText].
 */
fun Path.writeText(text: String, charset: Charset = Charsets.UTF_8): Unit =
    this.toFile().writeText(text, charset)

/**
 * Delegates to [kotlin.io.writer].
 */
inline fun Path.writer(charset: Charset = Charsets.UTF_8): OutputStreamWriter = this.toFile().writer(charset)
