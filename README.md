[![Download](https://api.bintray.com/packages/robstoll/tutteli-jars/niok/images/download.svg)](https://bintray.com/robstoll/tutteli-jars/niok/_latestVersion)
[![Apache license](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](http://opensource.org/licenses/Apache2.0)
[![Build Status Travis](https://travis-ci.org/robstoll/niok.svg?tag=v0.3.1)](https://travis-ci.org/robstoll/niok/branches)
[![Build Status AppVeyor](https://ci.appveyor.com/api/projects/status/26r91mvbtvtlkeay/branch/master?svg=true)](https://ci.appveyor.com/project/robstoll/niok/branch/master)
[![SonarCloud Status](https://sonarcloud.io/api/project_badges/measure?project=robstoll_niok&metric=alert_status)](https://sonarcloud.io/dashboard?id=robstoll_niok)

# Niok - java.nio.file for Kotlin

Niok provides a Kotlin idiomatic API for java.nio.file (the package for non-blocking I/O file operations).
We believe that using `Files` decreases readability => extension functions to the rescue :smirk: 

Next to providing delegations to `Files` it also delegates to Kotlin's built in extension functions for `File`. 

# Contribute
You found a bug, a delegation to `Files` is missing or there is something else you would like to improve?
Please [open an issue](https://github.com/robstoll/niok/issues/new), contributions are more than welcome :+1:

# License
Niok is licensed under [Apache 2.0](http://opensource.org/licenses/Apache2.0).
