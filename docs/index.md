[![Download](https://img.shields.io/badge/Download-1.4.3-%23007ec6)](https://search.maven.org/artifact/ch.tutteli.niok/niok/1.4.3/jar)
[![Apache license](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](http://opensource.org/licenses/Apache2.0)

# Niok - java.nio.file for Kotlin

Niok provides a Kotlin idiomatic API for java.nio.file (the package for non-blocking I/O file operations).
We believe that using `Files` decreases readability => extension functions to the rescue 😏

Next to providing delegations to `Files` it also delegates to Kotlin's built-in extension functions for `File` and
provides additional functions.

# Installation

Niok is published to maven central. Following an example how to use it

```
repositories { mavenCentral() }
dependencies {
    implementation("ch.tutteli.niok:niok:1.4.3")
}
```

# Documentation

Visit [kdoc/](https://robstoll.github.io/niok/kdoc/)

# License
Niok is licensed under [Apache 2.0](http://opensource.org/licenses/Apache2.0).
