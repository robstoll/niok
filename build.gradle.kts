import java.net.URL

buildscript {
    // needs to be defined in here because otherwise tutteli-publish plugin does not have this information when applied
    rootProject.group = "ch.tutteli.niok"
    rootProject.version = "1.4.7"
    rootProject.description = "API for java.nio.file in a Kotlin idiomatic way"
}

plugins {
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.dokka") version "1.5.0"
    val tutteliGradleVersion = "2.0.0"
    id("ch.tutteli.gradle.plugins.kotlin.module.info") version tutteliGradleVersion
    id("ch.tutteli.gradle.plugins.publish") version tutteliGradleVersion
    id("ch.tutteli.gradle.plugins.spek") version tutteliGradleVersion
    id("io.gitlab.arturbosch.detekt") version "1.18.0"
    id("org.sonarqube") version "3.3"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

repositories {
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.6"

        // so that consumers of this library using 1.3 are still happy, we don't use specific features of 1.5
        apiVersion = "1.4"
        languageVersion = "1.4"
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    val excludeKotlin: ExternalModuleDependency.() -> Unit = {
        exclude(group = "org.jetbrains.kotlin")
    }
    testImplementation("ch.tutteli.atrium:atrium-fluent-en_GB:0.17.0-RC1", excludeKotlin)
    testImplementation("ch.tutteli.spek:tutteli-spek-extensions:1.2.1", excludeKotlin)
}

val docsDir = projectDir.resolve("docs/kdoc")
tasks.dokkaHtml.configure {
    outputDirectory.set(docsDir)
}
tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
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

val dokka = tasks.register("dokka") {
    dependsOn(tasks.dokkaHtml)
}
tasks.register<Jar>("javaDoc") {
    archiveClassifier.set("javadoc")
    dependsOn(dokka)
    doFirst {
        from(docsDir)
    }
}

detekt {
    buildUponDefaultConfig = true
    config = files("${rootProject.projectDir}/gradle/scripts/detekt.yml")
    reports {
        xml.enabled = true
        html.enabled = false
        sarif.enabled = false
        txt.enabled = false
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

nexusPublishing {
    repositories {
        sonatype()
    }
}

/*

Release & deploy a commit
--------------------------------
1. generate dokka
a) gr dokka
b) check if output/links are still good (use intellij's http server via -> right click -> open in -> browser)

2. update master:

Either use the following commands or the manual steps below

export NIOK_PREVIOUS_VERSION=1.4.7
export NIOK_VERSION=1.4.7
find ./ -name "*.md" | xargs perl -0777 -i \
   -pe "s@$NIOK_PREVIOUS_VERSION@$NIOK_VERSION@g;" \
   -pe "s@tree/v1.4.7@tree/v$NIOK_VERSION@g;"
perl -0777 -i \
  -pe "s@$NIOK_PREVIOUS_VERSION@$NIOK_VERSION@g;" \
  -pe "s/rootProject.version = \"$NIOK_VERSION-SNAPSHOT\"/rootProject.version = \"$NIOK_VERSION\"/;" \
  ./build.gradle.kts
perl -0777 -i \
  -pe "s@$NIOK_PREVIOUS_VERSION@$NIOK_VERSION@g;" \
  -pe 's/(<!-- for master -->\n)\n([\S\s]*?)(\n<!-- for a specific release -->\n)<!--\n([\S\s]*?)-->\n(\n# Niok)/$1<!--\n$2-->$3\n$4\n$5/;' \
  ./README.md
git commit -a -m "v$NIOK_VERSION"

alternatively the manual steps:
  a) search for X.Y.Z-SNAPSHOT and replace with X.Y.Z
  b) commit (modified build.gradle and README.md)

3. prepare release on github
   a) git tag vX.Y.Z
   b) git push origin vX.Y.Z

3. deploy to maven central:
(assumes you have an alias named gr pointing to ./gradlew)
    a) java -version 2>&1 | grep "version \"11" && CI=true gr clean publishToSonatype closeAndReleaseSonatypeStagingRepository
    b) Log into https://oss.sonatype.org/#stagingRepositories
    c) check if staging repo is ok
    d) gr closeAndReleaseSonatypeStagingRepository (currently fails with `No staging repository with name sonatype created` needs to be done manually via Nexus gui)

4. create release on github

Prepare next dev cycle
-----------------------

export NIOK_VERSION=1.4.7
export NIOK_NEXT_VERSION=1.4.7
find ./ -name "*.md" | xargs perl -0777 -i \
   -pe "s@tree/v$NIOK_VERSION@tree/v1.4.7@g;";
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
   b) search for `tree/vX.Y.Z` and replace it with `tree/v1.4.7`
2. search for X.Y.Z and replace with X.Y.Z-SNAPSHOT
3. commit & push changes

*/
