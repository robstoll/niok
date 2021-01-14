import java.net.URI
import java.net.URL

buildscript {
    // needs to be defined in here because otherwise tutteli-publish plugin does not have this information
    rootProject.group = "ch.tutteli.niok"
    rootProject.version = "1.5.0-SNAPSHOT"
    rootProject.description = "API for java.nio.file in a Kotlin idiomatic way"
}

plugins {
    kotlin("jvm") version "1.4.21"
    id("org.jetbrains.dokka") version "1.4.20"
    val tutteliGradleVersion = "0.33.1"
    id("ch.tutteli.kotlin.module.info") version tutteliGradleVersion
    id("ch.tutteli.kotlin.utils") version tutteliGradleVersion
    id("ch.tutteli.project.utils") version tutteliGradleVersion
    id("ch.tutteli.publish") version tutteliGradleVersion
    id("ch.tutteli.spek") version tutteliGradleVersion
    id("io.gitlab.arturbosch.detekt") version "1.15.0"
    id("org.sonarqube") version "3.1"
}

repositories {
    mavenCentral()
    jcenter()
    maven { url = URI("https://dl.bintray.com/robstoll/tutteli-jars") }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    val excludeKotlin: (ExternalModuleDependency).() -> Unit = {
        exclude(group = "org.jetbrains.kotlin")
    }
    testImplementation("ch.tutteli.atrium:atrium-fluent-en_GB:0.15.0", excludeKotlin)
    testImplementation("ch.tutteli.spek:tutteli-spek-extensions:1.2.1", excludeKotlin)
}
val docsDir = projectDir.resolve("docs")
tasks.dokkaHtml.configure {
    outputDirectory.set(docsDir)
    dokkaSourceSets {
        configureEach {
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("https://github.com/robstoll/niok/blob/master/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}

fun File.rewrite(search: String, replace: String) =
    writeText(readText().replace(search, replace))

tasks.register("dokka") {
    dependsOn(tasks.dokkaHtml)
    doLast {
        val kdoc = docsDir.resolve("kdoc")
        kdoc.deleteRecursively()
        docsDir.resolve(project.name).renameTo(kdoc)
        listOf("scripts/navigation-pane.json", "scripts/pages.js").forEach { filePath ->
            docsDir.resolve(filePath).rewrite("\"location\":\"${project.name}/", "\"location\":\"kdoc/")
        }
        docsDir.resolve("navigation.html").rewrite("href=\"${project.name}/", "href=\"kdoc/")
    }
}

detekt {
    failFast = true
    config = files("${rootProject.projectDir}/gradle/scripts/detekt.yml")
    reports {
        xml.enabled = true
    }
}

sonarqube {
    properties {
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.organization", "robstoll-github")
        property("sonar.projectKey", "robstoll_niok")
        property("sonar.projectVersion", rootProject.version)
        property("sonar.kotlin", "detekt.reportPaths=build/reports/detekt/detekt.xml")
        property("sonar.sources", "src/main/kotlin")
        property("sonar.tests", "src/test/kotlin")
        property("sonar.coverage", "jacoco.xmlReportPaths=build/reports/jacoco/report.xml")
        property("sonar.verbose", "true")
    }
}
tasks.named("sonarqube").configure {
    dependsOn(tasks.named("detekt"))
}

/*

Release & deploy a commit
--------------------------------
export NIOK_PREVIOUS_VERSION=1.4.1
export NIOK_VERSION=1.5.0
find ./ -name "*.md" | xargs perl -0777 -i \
   -pe "s@$NIOK_PREVIOUS_VERSION@$NIOK_VERSION@g;" \
   -pe "s@tree/master@tree/v$NIOK_VERSION@g;" \
perl -0777 -i \
  -pe "s@$NIOK_PREVIOUS_VERSION@$NIOK_VERSION@g;" \
  -pe "s/rootProject.version = \"$NIOK_VERSION-SNAPSHOT\"/rootProject.version = \"$NIOK_VERSION\"/;" \
  ./build.gradle.kts
perl -0777 -i \
  -pe "s@$NIOK_PREVIOUS_VERSION@$NIOK_VERSION@g;" \
  -pe 's/(<!-- for master -->\n)\n([\S\s]*?)(\n<!-- for a specific release -->\n)<!--\n([\S\s]*?)-->\n(\n# Niok)/$1<!--\n$2-->$3\n$4\n$5/;' \
  ./README.md
git commit -a -m "v$NIOK_VERSION"

1. search for X.Y.Z-SNAPSHOT and replace with X.Y.Z
2. update master:
    b) commit (modified build.gradle and README.md)
    c) git tag vX.Y.Z
    d) git push origin vX.Y.Z
4. deploy to bintray:
    a) java -version 2>&1 | grep "version \"11" && CI=true ./gradlew clean publishToBintray
    b) Log in to bintray, check that there are 10 artifacts which needs to be published; publish them
    c) synchronise to maven central
5. create release on github

Prepare next dev cycle
-----------------------

export NIOK_VERSION=1.4.1
export NIOK_NEXT_VERSION=1.5.0
find ./ -name "*.md" | xargs perl -0777 -i \
   -pe "s@tree/v$NIOK_VERSION@tree/master@g;";
perl -0777 -i \
  -pe "s/rootProject.version = \"$NIOK_VERSION\"/rootProject.version = \"$NIOK_NEXT_VERSION-SNAPSHOT\"/;" \
  -pe "s/NIOK_VERSION=$NIOK_VERSION/NIOK_VERSION=$NIOK_NEXT_VERSION/;" \
  ./build.gradle.kts
perl -0777 -i \
  -pe 's/(<!-- for master -->\n)<!--\n([\S\s]*?)-->(\n<!-- for a specific release -->)\n([\S\s]*?)\n(\n# Niok)/$1\n$2$3\n<!--$4-->\n$5/;' \
  ./README.md
git commit -a -m "prepare dev cycle of $NIOK_NEXT_VERSION"

1. point to master
   a) search for `tag=vX.Y.Z` and replace it with `branch=master`
   b) search for `tree/vX.Y.Z` and replace it with `tree/master`
2. search for X.Y.Z and replace with X.Y.Z-SNAPSHOT
3. commit & push changes

*/
