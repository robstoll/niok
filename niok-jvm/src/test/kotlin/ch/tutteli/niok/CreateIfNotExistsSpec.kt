package ch.tutteli.niok

import ch.tutteli.atrium.api.cc.en_GB.toBe
import ch.tutteli.atrium.verbs.expect
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
            expect(path.readText()).toBe("bla")
        }
        it("createDirectoryIfNotExists does not override the file"){
            val path = tempFolder.resolve("a.txt").createDirectoryIfNotExists()
            expect(path.readText()).toBe("bla")
        }
    }

    describe("directory exists") {
        it("createFileIfNotExists does not override the file") {
            val path = tempFolder.resolve("b").createFileIfNotExists()
            //TODO replace with exists of Atrium with 0.9.0
            expect(path.resolve("c.txt").exists).toBe(true)
        }
        it("createDirectoryIfNotExists does not override the file"){
            val path = tempFolder.resolve("b").createDirectoryIfNotExists()
            expect(path.resolve("c.txt").exists).toBe(true)
        }
    }

    describe("file does not exist") {
        it("createFileIfNotExists creates the file") {
            val path = tempFolder.resolve("nonExisting.txt").createFileIfNotExists()
            //TODO replace with exists of Atrium with 0.9.0
            expect(path.exists).toBe(true)
            expect(path.isRegularFile).toBe(true)
            expect(path.readText()).toBe("")
        }
        it("createDirectoryIfNotExists creates the directory") {
            val path = tempFolder.resolve("nonExisting").createDirectoryIfNotExists()
            //TODO replace with exists of Atrium with 0.9.0
            expect(path.exists).toBe(true)
            expect(path.isDirectory).toBe(true)
        }
    }
})
