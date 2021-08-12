package ch.tutteli.niok

import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.verbs.expect
import ch.tutteli.spek.extensions.memoizedTempFolder
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.describe
import java.io.File
import java.nio.file.Paths

object RelativeToSpec : Spek({
    val tempFolder by memoizedTempFolder()

    describe("compare Kotlin's File.relativeTo with Path.relativize") {
        val s = File.separator
        println(System.getProperty("java.version"))
        val list = if (System.getProperty("java.version").startsWith("1.8")) {
            listOf(
                listOf("a", "a", "", ""),
                listOf("a", "b", "..${s}a", "..${s}b"),
                //Bug in JDK due to https://bugs.java.com/bugdatabase/view_bug.do?bug_id=9057443
                //listOf(".${s}a", "b", "..${s}a", "..$s.${s}b"),
                listOf("a", ".${s}b", "..${s}a", "..$s.${s}b")

            )
        } else {
            listOf(
                listOf("a", "a", "", ""),
                listOf("a", "b", "..${s}a", "..${s}b"),
                listOf(".${s}a", "b", "..${s}a", "..$s${s}b"),
                listOf("a", ".${s}b", "..${s}a", "..$s${s}b")

            )
        }
        @Suppress("DestructuringDeclarationWithTooManyEntries")
        list.forEach { (stringPath1, stringPath2, expectRelativeTo, expectRelativize) ->
            context(
                "path1: $stringPath1, " +
                    "path2: $stringPath2 -> relativeTo: $expectRelativeTo, " +
                    "relativize: $expectRelativize"
            ) {
                val path1 by memoized(CachingMode.SCOPE) { tempFolder.newFile(stringPath1) }
                val path2 by memoized(CachingMode.SCOPE) {
                    if (stringPath1 != stringPath2) {
                        tempFolder.newFile(stringPath2)
                    } else {
                        path1
                    }
                }

                val resultRelativeTo by memoized(CachingMode.SCOPE) { path1.relativeTo(path2) }
                it("relativeTo which delegates to Kotlin's File.relativeTo") {
                    expect(resultRelativeTo).toEqual(Paths.get(expectRelativeTo))
                }
                it("path2.resolve(Path.relativeTo).normalize() = path1.normalize()") {
                    expect(path2.resolve(resultRelativeTo).normalize()).toEqual(path1.normalize())
                }

                val resultRelativize by memoized(CachingMode.SCOPE) { path1.relativize(path2) }
                it("Path.relativize") {
                    expect(resultRelativize).toEqual(Paths.get(expectRelativize))
                }
                it("path1.resolve(Path.relativize).normalize() = path2.normalize()") {
                    expect(path1.resolve(resultRelativize).normalize()).toEqual(path2.normalize())
                }
            }
        }
    }
})
