package ch.tutteli.niok

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import ch.tutteli.spek.extensions.memoizedTempFolder
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.describe
import java.nio.file.Files.list

object ReCreateSpec : Spek({
    val tempFolder by memoizedTempFolder(CachingMode.SCOPE) {
        newFile("a.txt").writeText("bla")
        newDirectory("b").newFile("c.txt")
        newDirectory("d").newDirectory("d2").newFile("c.txt")
        val e = newDirectory("e")
        newSymbolicLink("f", e)
    }

    describe("reCreateDirectory") {

        it("a file - throws IllegalStateException") {
            val a = tempFolder.resolve("a.txt")
            expect(a).isRegularFile()
            expect {
                a.reCreateDirectory()
            }.toThrow<IllegalStateException> {
                messageContains("a.txt is not a directory")
            }
        }
        it("a symbolic link pointing to a directory - throws IllegalStateException") {
            val f = tempFolder.resolve("f")
            //TODO replace with function from Atrium with 0.16.0
            expect(f).feature { f(it::isSymbolicLink) }.toBe(true)
            expect {
                f.reCreateDirectory()
            }.toThrow<IllegalStateException> {
                messageContains("f is not a directory")
            }
        }

        it("an non empty directory without sub-dirs - deletes recursively and reCreates") {
            val b = tempFolder.resolve("b")
            expect(b).isDirectory()
            b.reCreateDirectory()
            expect(b) {
                isDirectory()
                //TODO replace with function from Atrium with 0.16.0
                feature("findFirstFile") { list(this).findFirst() }.isEmpty()
            }
        }

        it("an non empty directory with sub-dirs - deletes recursively and reCreates") {
            val d = tempFolder.resolve("d")
            expect(d).isDirectory()
            d.reCreateDirectory()
            expect(d) {
                isDirectory()
                //TODO replace with function from Atrium with 0.16.0
                feature("findFirstFile") { list(this).findFirst() }.isEmpty()
            }
        }
        it("an empty directory - deletes and reCreates") {
            val e = tempFolder.resolve("e")
            expect(e).isDirectory()
            e.isDirectory
            e.reCreateDirectory()
            expect(e) {
                isDirectory()
                //TODO replace with function from Atrium with 0.16.0
                feature("findFirstFile") { list(this).findFirst() }.isEmpty()
            }
        }
    }
})
