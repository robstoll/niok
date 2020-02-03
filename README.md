[![Download](https://api.bintray.com/packages/robstoll/tutteli-jars/niok/images/download.svg)](https://bintray.com/robstoll/tutteli-jars/niok/_latestVersion)
[![Apache license](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](http://opensource.org/licenses/Apache2.0)
[![Build Status Travis](https://travis-ci.org/robstoll/niok.svg?tag=v1.3.3)](https://travis-ci.org/robstoll/niok/branches)
[![Build status GitHub Actions](https://github.com/robstoll/niok/workflows/Windows/badge.svg)](https://github.com/robstoll/niok/actions/)
[![SonarCloud Status](https://sonarcloud.io/api/project_badges/measure?project=robstoll_niok&metric=alert_status)](https://sonarcloud.io/dashboard?id=robstoll_niok)

# Niok - java.nio.file for Kotlin

Niok provides a Kotlin idiomatic API for java.nio.file (the package for non-blocking I/O file operations).
We believe that using `Files` decreases readability => extension functions to the rescue :smirk: 

Next to providing delegations to `Files` it also delegates to Kotlin's built in extension functions for `File`. 

# Installation

Niok is pusblished to maven central, jcenter and [bintray](https://bintray.com/robstoll/tutteli-jars/noik). 
Following an example if you use gradle and jcenter:

```
repositories { jcenter() }
dependencies {
    implementation 'ch.tutteli.niok:niok:1.3.3'
}
```

accordingly you can use `niok-android` as artifactId instead of `niok` if you want to use it in an android project.

# Contribute
You found a bug, a delegation to `Files` is missing or there is something else you would like to improve?
Please [open an issue](https://github.com/robstoll/niok/issues/new), contributions are more than welcome :+1:

# License
Niok is licensed under [Apache 2.0](http://opensource.org/licenses/Apache2.0).
