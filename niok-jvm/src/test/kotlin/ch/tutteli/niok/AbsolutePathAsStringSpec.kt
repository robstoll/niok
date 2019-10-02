package ch.tutteli.niok

import ch.tutteli.atrium.api.cc.en_GB.contains
import ch.tutteli.atrium.api.cc.en_GB.toBe
import ch.tutteli.atrium.verbs.expect
import ch.tutteli.spek.extensions.TempFolder
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.File
import java.nio.file.Paths

object AbsolutePathAsStringSpec : Spek({
    val tempFolder = TempFolder.perTest()
    registerListener(tempFolder)

    val sep = File.separatorChar

    describe("absolute path") {
        it("returns the whole path") {
            val path = tempFolder.newFile("test")
            expect(path.absolutePathAsString).toBe(path.toFile().absolutePath)
        }
    }

    describe("absolute path with ./ in it") {
        it("returns the whole path with ./ in it") {
            val path = tempFolder.newFile("./test")
            expect(path.absolutePathAsString) {
                toBe(path.toFile().absolutePath)
                contains(".${sep}test")
            }
        }
    }

    describe("absolute path with ../ in it") {
        it("returns the whole path with ../ in it") {
            val tmpDirName = tempFolder.tmpDir.fileNameAsString
            val path = tempFolder.newFile("../$tmpDirName/test")
            expect(path.absolutePathAsString) {
                toBe(path.toFile().absolutePath)
                contains("..$sep$tmpDirName${sep}test")
            }
        }
    }

    describe("relative path") {
        it("returns the whole path") {
            val path = Paths.get("test")
            expect(path.absolutePathAsString).toBe(File("test").absolutePath)
        }
    }

    describe("relative path with ./") {
        it("returns the whole path with ./") {
            val path = Paths.get("./test")
            expect(path.absolutePathAsString) {
                toBe(File("./test").absolutePath)
                contains(".${sep}test")
            }
        }
    }

    describe("relative path with ../ in it") {
        it("returns the whole path with ../ in it") {
            val path = Paths.get("../test")
            expect(path.absolutePathAsString) {
                toBe(File("../test").absolutePath)
                contains("..${sep}test")
            }
        }
    }
})
