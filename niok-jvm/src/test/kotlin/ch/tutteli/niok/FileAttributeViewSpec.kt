package ch.tutteli.niok

import ch.tutteli.atrium.api.fluent.en_GB.notToBeNull
import ch.tutteli.atrium.api.fluent.en_GB.feature
import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.verbs.expect
import ch.tutteli.spek.extensions.memoizedTempFolder
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.nio.file.attribute.BasicFileAttributeView
import java.nio.file.attribute.FileAttributeView

object FileAttributeViewSpec: Spek({
    val tempFolder by memoizedTempFolder()

    describe("Path.getFileAttributeView") {
        it("returns an existing view") {
            val testFile = tempFolder.newFile("test")
            expect(testFile.getFileAttributeView<BasicFileAttributeView>()).notToBeNull {
                feature(FileAttributeView::name).toBe("basic")
            }
        }

        it("returns null for a non-existent view") {
            val testFile = tempFolder.newFile("test")
            expect(testFile.getFileAttributeView<UnknownFileAttributeView>()).toBe(null)
        }
    }
})

class UnknownFileAttributeView: FileAttributeView {
    override fun name() = "unknown"
}
