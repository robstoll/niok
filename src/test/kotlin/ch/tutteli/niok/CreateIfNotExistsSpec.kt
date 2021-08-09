package ch.tutteli.niok

import ch.tutteli.atrium.api.fluent.en_GB.toExist
import ch.tutteli.atrium.api.fluent.en_GB.toBeADirectory
import ch.tutteli.atrium.api.fluent.en_GB.toBeARegularFile
import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.verbs.expect
import ch.tutteli.spek.extensions.memoizedTempFolder
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.describe

object CreateIfNotExistsSpec : Spek({
    val tempFolder by memoizedTempFolder(CachingMode.SCOPE) {
        newFile("a.txt").writeText("bla")
        newDirectory("b").newFile("c.txt")
    }

    describe("file exists") {
        it("createFileIfNotExists does not override the file") {
            val path = tempFolder.resolve("a.txt").createFileIfNotExists()
            expect(path.readText()).toEqual("bla")
        }
        it("createDirectoryIfNotExists does not override the file") {
            val path = tempFolder.resolve("a.txt").createDirectoryIfNotExists()
            expect(path.readText()).toEqual("bla")
        }
    }

    describe("directory exists") {
        it("createFileIfNotExists does not override the file") {
            val path = tempFolder.resolve("b").createFileIfNotExists()
            expect(path.resolve("c.txt")).toExist()
        }
        it("createDirectoryIfNotExists does not override the file") {
            val path = tempFolder.resolve("b").createDirectoryIfNotExists()
            expect(path.resolve("c.txt")).toExist()
        }
    }

    describe("file does not exist") {
        it("createFileIfNotExists creates the file") {
            val path = tempFolder.resolve("nonExisting.txt").createFileIfNotExists()
            expect(path) {
                toExist()
                toBeARegularFile()
            }
            expect(path.readText()).toEqual("")
        }
        it("createDirectoryIfNotExists creates the directory") {
            val path = tempFolder.resolve("nonExisting").createDirectoryIfNotExists()
            expect(path) {
                toExist()
                toBeADirectory()
            }
        }
    }
})
