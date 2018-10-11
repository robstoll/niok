package ch.tutteli.niok

import ch.tutteli.atrium.api.cc.en_GB.contains
import ch.tutteli.atrium.api.cc.en_GB.toBe
import ch.tutteli.atrium.assert
import ch.tutteli.spek.extensions.TempFolder
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import java.io.File
import java.nio.file.Paths

object AbsolutePathAsStringSpec : Spek({
    val tempFolder = TempFolder.perTest()
    registerListener(tempFolder)

    val sep = File.separatorChar

    given("absolute path") {
        it("returns the whole path") {
            val path = tempFolder.newFile("test")
            assert(path.absolutePathAsString).toBe(path.toFile().absolutePath)
        }
    }

    given("absolute path with ./ in it") {
        it("returns the whole path with ./ in it") {
            val path = tempFolder.newFile("./test")
            assert(path.absolutePathAsString) {
                toBe(path.toFile().absolutePath)
                contains(".${sep}test")
            }
        }
    }

    given("absolute path with ../ in it") {
        it("returns the whole path with ../ in it") {
            val tmpDirName = tempFolder.tmpDir.fileNameAsString
            val path = tempFolder.newFile("../$tmpDirName/test")
            assert(path.absolutePathAsString) {
                toBe(path.toFile().absolutePath)
                contains("..$sep$tmpDirName${sep}test")
            }
        }
    }

    given("relative path") {
        it("returns the whole path") {
            val path = Paths.get("test")
            assert(path.absolutePathAsString).toBe(File("test").absolutePath)
        }
    }

    given("relative path with ./") {
        it("returns the whole path with ./") {
            val path = Paths.get("./test")
            assert(path.absolutePathAsString) {
                toBe(File("./test").absolutePath)
                contains(".${sep}test")
            }
        }
    }

    given("relative path with ../ in it") {
        it("returns the whole path with ../ in it") {
            val path = Paths.get("../test")
            assert(path.absolutePathAsString) {
                toBe(File("../test").absolutePath)
                contains("..${sep}test")
            }
        }
    }
})
