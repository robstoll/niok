package ch.tutteli.niok

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import ch.tutteli.spek.extensions.memoizedTempFolder
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.describe

object ReCreateSpec : Spek({
    val tempFolder by memoizedTempFolder(CachingMode.SCOPE) {
        newFile("a.txt").writeText("bla")
        newDirectory("b").newFile("c.txt")
        newDirectory("d").newDirectory("d2").newFile("c.txt")
        val e = newDirectory("e")
        newSymbolicLink("f", e)
    }

    describe("reCreateDirectory") {

        it("non-existing directory in existing directory - creates the directory") {
            val e = tempFolder.resolve("e")
            expect(e).toBeADirectory()
            val a = e.resolve("a")
            a.reCreateDirectory()
            expect(a) { toBeAnEmptyDirectory() }
            expect(e).toExist()
        }

        it("non-existing directory in non-existing directory - throws IllegalStateException") {
            val e = tempFolder.resolve("nonExisting")
            expect(e).notToExist()
            val a = e.resolve("a")
            expect {
                a.reCreateDirectory()
            }.toThrow<java.nio.file.NoSuchFileException> {
                messageToContain(a.absolutePathAsString)
            }
        }

        it("a file - throws IllegalStateException") {
            val a = tempFolder.resolve("a.txt")
            expect(a).toBeARegularFile()
            expect {
                a.reCreateDirectory()
            }.toThrow<IllegalStateException> {
                messageToContain("a.txt is not a directory")
            }
        }
        it("a symbolic link pointing to a directory - throws IllegalStateException") {
            val f = tempFolder.resolve("f")
            expect(f).toBeASymbolicLink()
            expect {
                f.reCreateDirectory()
            }.toThrow<IllegalStateException> {
                messageToContain("f is not a directory")
            }
        }

        it("an non empty directory without sub-dirs - deletes recursively and reCreates") {
            val b = tempFolder.resolve("b")
            expect(b).toBeADirectory()
            b.reCreateDirectory()
            expect(b) { toBeAnEmptyDirectory() }
        }

        it("an non empty directory with sub-dirs - deletes recursively and reCreates") {
            val d = tempFolder.resolve("d")
            expect(d).toBeADirectory()
            d.reCreateDirectory()
            expect(d) { toBeAnEmptyDirectory() }
        }
        it("an empty directory - deletes and reCreates") {
            val e = tempFolder.resolve("e")
            expect(e).toBeADirectory()
            e.reCreateDirectory()
            expect(e) { toBeAnEmptyDirectory() }
        }
    }

})
