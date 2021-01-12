[![Download](https://api.bintray.com/packages/robstoll/tutteli-jars/niok/images/download.svg)](https://bintray.com/robstoll/tutteli-jars/niok/_latestVersion)
[![Apache license](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](http://opensource.org/licenses/Apache2.0)

# Niok - java.nio.file for Kotlin

Niok provides a Kotlin idiomatic API for java.nio.file (the package for non-blocking I/O file operations).
We believe that using `Files` decreases readability => extension functions to the rescue 😏

Next to providing delegations to `Files` it also delegates to Kotlin's built-in extension functions for `File` and
provides additional functions.

# Installation

Niok is published to maven central, jcenter and [bintray](https://bintray.com/robstoll/tutteli-jars/noik).
Following an example if you use gradle and jcenter:

```
repositories { jcenter() }
dependencies {
    implementation 'ch.tutteli.niok:niok:1.4.1'
}
```

# Documentation

Visit [/kdoc](https://robstoll.github.io/niok/kdoc)