package ch.tutteli.niok

import ch.tutteli.atrium.api.cc.en_GB.toBe
import ch.tutteli.atrium.assert
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import java.io.File
import java.nio.file.Paths

object ResolveSpec : Spek({
    val s = File.separatorChar

    describe("resolve file in sub-directory") {
        val path = Paths.get("a")
        it("is appended correctly") {
            assert(path.resolve("b", "c").toString()).toBe("a${s}b${s}c")
        }
    }

    describe("resolve file in sub-sub-directory") {
        val path = Paths.get("a")
        it("is appended correctly") {
            assert(path.resolve("b", "c", "d").toString()).toBe("a${s}b${s}c${s}d")
        }
    }
})
