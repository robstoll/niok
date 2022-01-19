import java.net.URL

buildscript {
    // needs to be defined in here because otherwise tutteli-publish plugin does not have this information when applied
    rootProject.group = "ch.tutteli.niok"
    rootProject.version = "1.4.8-SNAPSHOT"
    rootProject.description = "API for java.nio.file in a Kotlin idiomatic way"
}

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.10"
    val tutteliGradleVersion = "4.2.1"
    id("ch.tutteli.gradle.plugins.dokka") version tutteliGradleVersion
    id("ch.tutteli.gradle.plugins.kotlin.module.info") version tutteliGradleVersion
    id("ch.tutteli.gradle.plugins.publish") version tutteliGradleVersion
    id("ch.tutteli.gradle.plugins.spek") version tutteliGradleVersion
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
    id("org.sonarqube") version "3.3"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

repositories {
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        //TODO change to jdk11 with 2.0.0
        jvmTarget = "1.6"

        // so that consumers of this library using 1.3 are still happy, we don't use specific features of 1.5
        //TODO change to 1.5 with 2.0.0
        apiVersion = "1.4"
        languageVersion = "1.4"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    val excludeNiokAndKotlin: ExternalModuleDependency.() -> Unit = {
        exclude(group = "ch.tutteli.niok")
        exclude(group = "org.jetbrains.kotlin")
    }
    testImplementation("ch.tutteli.atrium:atrium-fluent-en_GB:0.17.0", excludeNiokAndKotlin)
    testImplementation("ch.tutteli.spek:tutteli-spek-extensions:1.2.1", excludeNiokAndKotlin)
}

detekt {
    allRules = true
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
        property("sonar.projectKey", "robstoll_${rootProject.name}")
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

2. update main:

Either use the following commands or the manual steps below

export NIOK_PREVIOUS_VERSION=1.4.7
export NIOK_VERSION=1.4.8
find ./ -name "*.md" | xargs perl -0777 -i \
   -pe "s@$NIOK_PREVIOUS_VERSION@$NIOK_VERSION@g;" \
   -pe "s@tree/v1.4.7@tree/v$NIOK_VERSION@g;"
perl -0777 -i \
  -pe "s@$NIOK_PREVIOUS_VERSION@$NIOK_VERSION@g;" \
  -pe "s/rootProject.version = \"$NIOK_VERSION-SNAPSHOT\"/rootProject.version = \"$NIOK_VERSION\"/;" \
  ./build.gradle.kts
perl -0777 -i \
  -pe "s@$NIOK_PREVIOUS_VERSION@$NIOK_VERSION@g;" \
  -pe 's/(<!-- for main -->\n)\n([\S\s]*?)(\n<!-- for a specific release -->\n)<!--\n([\S\s]*?)-->\n(\n# Niok)/$1<!--\n$2-->$3\n$4\n$5/;' \
  ./README.md
git commit -a -m "v$NIOK_VERSION"
git tag "v$NIOK_VERSION"

alternatively the manual steps:
  a) search for X.Y.Z-SNAPSHOT and replace with X.Y.Z
  b) adjust badges in readme
  c) commit (modified build.gradle and README.md)

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
export NIOK_NEXT_VERSION=1.4.8
find ./ -name "*.md" | xargs perl -0777 -i \
   -pe "s@tree/v$NIOK_VERSION@tree/v1.4.7@g;";
perl -0777 -i \
  -pe "s/rootProject.version = \"$NIOK_VERSION\"/rootProject.version = \"$NIOK_NEXT_VERSION-SNAPSHOT\"/;" \
  -pe "s/NIOK_VERSION=$NIOK_VERSION/NIOK_VERSION=$NIOK_NEXT_VERSION/;" \
  ./build.gradle.kts
perl -0777 -i \
  -pe 's/(<!-- for main -->\n)<!--\n([\S\s]*?)-->(\n<!-- for a specific release -->)\n([\S\s]*?)\n(\n# Niok)/$1\n$2$3\n<!--$4-->\n$5/;' \
  ./README.md
git commit -a -m "prepare dev cycle of $NIOK_NEXT_VERSION"

alternatively the manual steps:
1. point to main
   a) search for `tag=vX.Y.Z` and replace it with `branch=main`
   b) search for `tree/vX.Y.Z` and replace it with `tree/master`
2. search for X.Y.Z and replace with X.Y.Z-SNAPSHOT
3. adjust badges in readme
4. commit & push changes

*/
