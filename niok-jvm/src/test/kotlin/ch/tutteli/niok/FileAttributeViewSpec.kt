package ch.tutteli.niok

import ch.tutteli.atrium.api.cc.en_GB.notToBeNull
import ch.tutteli.atrium.api.cc.en_GB.returnValueOf
import ch.tutteli.atrium.api.cc.en_GB.toBe
import ch.tutteli.atrium.verbs.expect
import ch.tutteli.spek.extensions.TempFolder
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.nio.file.attribute.BasicFileAttributeView
import java.nio.file.attribute.FileAttributeView

object FileAttributeViewSpec: Spek({
    val tempFolder = TempFolder.perTest()
    registerListener(tempFolder)

    describe("Path.getFileAttributeView") {
        it("returns an existing view") {
            val testFile = tempFolder.newFile("test")
            expect(testFile.getFileAttributeView<BasicFileAttributeView>()).notToBeNull {
                returnValueOf(FileAttributeView::name).toBe("basic")
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
