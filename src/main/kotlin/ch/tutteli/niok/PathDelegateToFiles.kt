@file:Suppress("NOTHING_TO_INLINE")

package ch.tutteli.niok

import java.io.OutputStream
import java.nio.channels.SeekableByteChannel
import java.nio.charset.Charset
import java.nio.file.*
import java.nio.file.attribute.*

/**
 * Delegates to [Files.copy] with overload for [OutputStream].
 */
inline fun <T: Path> T.copyTo(outputStream: OutputStream): Long = Files.copy(this, outputStream)

/**
 * Delegates to [Files.createDirectory].
 */
inline fun <T: Path> T.createDirectory(vararg fileAttributes: FileAttribute<*>): Path =
    Files.createDirectory(this, *fileAttributes)

/**
 * Delegates to [Files.createDirectories].
 */
inline fun <T: Path> T.createDirectories(vararg fileAttributes: FileAttribute<*>): Path =
    Files.createDirectories(this, *fileAttributes)

/**
 * Delegates to [Files.createFile].
 */
inline fun <T: Path> T.createFile(vararg fileAttributes: FileAttribute<*>): Path =
    Files.createFile(this, *fileAttributes)

/**
 * Creates a new link (directory entry) for this [Path].
 * Delegates to [Files.createLink].
 */
inline fun <T: Path> T.createLink(link: Path): Path = Files.createLink(link, this)

/**
 * Creates a symbolic link for this [Path].
 * Delegates to [Files.createDirectory].
 */
inline fun <T: Path> T.createSymbolicLink(link: Path, vararg fileAttributes: FileAttribute<*>): Path =
    Files.createSymbolicLink(link, this, *fileAttributes)

/**
 * Delegates to [Files.delete].
 */
inline fun <T: Path> T.delete(): Unit = Files.delete(this)

/**
 * Delegates to [Files.deleteIfExists].
 */
inline fun <T: Path> T.deleteIfExists(): Boolean = Files.deleteIfExists(this)

/**
 * Delegates to [Files.exists].
 */
inline val <T: Path> T.exists get(): Boolean = Files.exists(this)

/**
 * Delegates to [Files.isDirectory] without [LinkOption]s.
 */
inline val <T: Path> T.isDirectory get(): Boolean = Files.isDirectory(this)

/**
 * Delegates to [Files.isDirectory].
 */
inline fun <T: Path> T.isDirectory(linkOption: LinkOption, vararg furtherLinkOptions: LinkOption): Boolean =
    Files.isDirectory(this, linkOption, *furtherLinkOptions)

/**
 * Delegates to [Files.isExecutable].
 */
inline val <T: Path> T.isExecutable get(): Boolean = Files.isExecutable(this)

/**
 * Delegates to [Files.isHidden].
 */
inline val <T: Path> T.isHidden get(): Boolean = Files.isHidden(this)

/**
 * Delegates to [Files.isReadable].
 */
inline val <T: Path> T.isReadable get(): Boolean = Files.isReadable(this)

/**
 * Delegates to [Files.isRegularFile] without [LinkOption]s.
 */
inline val <T: Path> T.isRegularFile get(): Boolean = Files.isRegularFile(this)

/**
 * Delegates to [Files.isRegularFile].
 */
inline fun <T: Path> T.isRegularFile(linkOption: LinkOption, vararg furtherLinkOptions: LinkOption): Boolean =
    Files.isRegularFile(this, linkOption, *furtherLinkOptions)

/**
 * Delegates to [Files.isSameFile].
 */
inline fun <T: Path> T.isSameFile(other: Path): Boolean = Files.isSameFile(this, other)

/**
 * Delegates to [Files.isSymbolicLink].
 */
inline val <T: Path> T.isSymbolicLink get(): Boolean = Files.isSymbolicLink(this)

/**
 * Delegates to [Files.isWritable].
 */
inline val <T: Path> T.isWritable get(): Boolean = Files.isWritable(this)

/**
 * Delegates to [Files.getFileStore].
 */
inline val <T: Path> T.fileStore get(): FileStore = Files.getFileStore(this)

/**
 * Delegates to [Files.getAttribute].
 */
inline fun <T: Path> T.getAttribute(attribute: String, vararg linkOptions: LinkOption): Any =
    Files.getAttribute(this, attribute, *linkOptions)

/**
 * Delegates to [Files.getFileAttributeView].
 */
inline fun <reified V : FileAttributeView> Path.getFileAttributeView(vararg linkOptions: LinkOption): V? =
    Files.getFileAttributeView(this, V::class.java, *linkOptions)

/**
 * Delegates to [Files.getLastModifiedTime] without [LinkOption]s.
 */
inline val <T: Path> T.lastModifiedTime get(): FileTime = Files.getLastModifiedTime(this)

/**
 * Delegates to [Files.getLastModifiedTime].
 */
inline fun <T: Path> T.lastModifiedTime(linkOption: LinkOption, vararg furtherLinkOptions: LinkOption): FileTime =
    Files.getLastModifiedTime(this, linkOption, *furtherLinkOptions)

/**
 * Delegates to [Files.move].
 */
inline fun <T: Path> T.move(target: Path, vararg copyOptions: CopyOption): Path =
    Files.move(this, target, *copyOptions)

/**
 * Delegates to [Files.newByteChannel].
 */
inline fun <T: Path> T.newByteChannel(vararg openOptions: OpenOption): SeekableByteChannel =
    Files.newByteChannel(this, *openOptions)

/**
 * Delegates to [Files.newByteChannel].
 */
inline fun <T: Path> T.newByteChannel(
    openOptions: Set<OpenOption>,
    vararg fileAttributes: FileAttribute<*>
): SeekableByteChannel = Files.newByteChannel(this, openOptions, *fileAttributes)

/**
 * Delegates to [Files.notExists] without [LinkOption]s.
 */
inline val <T: Path> T.notExists get(): Boolean = Files.notExists(this)

/**
 * Delegates to [Files.notExists].
 */
inline fun <T: Path> T.notExists(linkOption: LinkOption, vararg furtherLinkOptions: LinkOption): Boolean =
    Files.notExists(this, linkOption, *furtherLinkOptions)

/**
 * Delegates to [Files.getPosixFilePermissions] without [LinkOption]s.
 */
inline val <T: Path> T.posixFilePersmissions get(): Set<PosixFilePermission> = Files.getPosixFilePermissions(this)

/**
 * Delegates to [Files.getPosixFilePermissions].
 */
inline fun <T: Path> T.posixFilePersmissions(linkOption: LinkOption, vararg furtherLinkOptions: LinkOption) =
    Files.getPosixFilePermissions(this, linkOption, *furtherLinkOptions)

/**
 * Delegates to [Files.probeContentType].
 */
inline fun <T: Path> T.probeContentType(): String = Files.probeContentType(this)

/**
 * Delegates to [Files.getLastModifiedTime] without [LinkOption]s.
 */
inline val <T: Path> T.owner get(): UserPrincipal = Files.getOwner(this)

/**
 * Delegates to [Files.getLastModifiedTime].
 */
inline fun <T: Path> T.owner(linkOption: LinkOption, vararg furtherLinkOptions: LinkOption): UserPrincipal =
    Files.getOwner(this, linkOption, *furtherLinkOptions)


/**
 * Delegates to [Files.readAttributes].
 */
inline fun <reified A : BasicFileAttributes> Path.readAttributes(vararg linkOptions: LinkOption): A =
    Files.readAttributes(this, A::class.java, *linkOptions)

/**
 * Delegates to [Files.readAttributes].
 */
inline fun <T: Path> T.readAttributes(attributes: String, vararg linkOptions: LinkOption): Map<String, Any> =
    Files.readAttributes(this, attributes, *linkOptions)

/**
 * Delegates to [Files.readSymbolicLink].
 */
inline fun <T: Path> T.readSymbolicLink() = Files.readSymbolicLink(this)

/**
 * Delegates to [Files.setAttribute].
 */
inline fun <T: Path> T.setAttribute(attribute: String, value: Any, vararg linkOptions: LinkOption) =
    Files.setAttribute(this, attribute, value, *linkOptions)

/**
 * Delegates to [Files.setLastModifiedTime].
 */
inline fun <T: Path> T.setLastModifiedTime(fileTime: FileTime) = Files.setLastModifiedTime(this, fileTime)

/**
 * Delegates to [Files.setPosixFilePermissions].
 */
inline fun <T: Path> T.setPosixFilePermissions(perms: Set<PosixFilePermission>) = Files.setPosixFilePermissions(this, perms)

/**
 * Delegates to [Files.size].
 */
inline val <T: Path> T.size get(): Long = Files.size(this)

/**
 * Delegates to [Files.write].
 */
inline fun <T: Path> T.writeLines(
    lines: Iterable<CharSequence>,
    charset: Charset = Charsets.UTF_8,
    vararg openOptions: OpenOption
): Path = Files.write(this, lines, charset, *openOptions)

