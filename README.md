<!-- for master -->

[![Download](https://api.bintray.com/packages/robstoll/tutteli-jars/niok/images/download.svg)](https://bintray.com/robstoll/tutteli-jars/niok/_latestVersion)
[![Apache license](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](http://opensource.org/licenses/Apache2.0)
[![Build Status Ubuntu](https://github.com/robstoll/niok/workflows/Ubuntu/badge.svg?event=push)](https://github.com/robstoll/niok/actions?query=workflow%3AUbuntu+branch%3Amaster)
[![Build Status Windows](https://github.com/robstoll/niok/workflows/Windows/badge.svg?event=push)](https://github.com/robstoll/niok/actions?query=workflow%3AWindows+branch%3Amaster)
[![SonarCloud Status](https://sonarcloud.io/api/project_badges/measure?project=robstoll_niok&metric=alert_status)](https://sonarcloud.io/dashboard?id=robstoll_niok)

<!-- for a specific release -->
<!--
[![Download](https://img.shields.io/badge/Download-1.4.1-%23007ec6)](https://bintray.com/robstoll/tutteli-jars/niok/1.4.1 "Download 1.4.1 from Bintray")
[![Apache license](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](http://opensource.org/licenses/Apache2.0)
-->

# Niok - java.nio.file for Kotlin

Niok provides a Kotlin idiomatic API for java.nio.file (the package for non-blocking I/O file operations).
We believe that using `Files` decreases readability => extension functions to the rescue :smirk: 

Next to providing delegations to `Files` it also delegates to Kotlin's built-in extension functions for `File` and
provides additional functions.

# Installation

Niok is published to maven central, jcenter and [bintray](https://bintray.com/robstoll/tutteli-jars/niok). 
Following an example if you use gradle and jcenter:

```
repositories { jcenter() }
dependencies {
    implementation 'ch.tutteli.niok:niok:1.4.1'
}
```

# Documentation

Visit [https://robstoll.github.io/niok/kdoc](https://robstoll.github.io/niok/kdoc/ch.tutteli.niok/index.html).

# Contribute
You found a bug, a delegation to `Files` is missing or there is something else you would like to improve?
Please [open an issue](https://github.com/robstoll/niok/issues/new), contributions are more than welcome :+1:

# License
Niok is licensed under [Apache 2.0](http://opensource.org/licenses/Apache2.0).
