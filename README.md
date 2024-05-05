# korlibs

This repository hosts the libraries used by [KorGE (Kotlin Game Engine)](https://github.com/korlibs/korge).
It contains all the modules but KorGE itself.

For the old korlibs repo: <https://github.com/korlibs/korlibs-all>

## Usage

Modules are published to Maven Central under the `com.soywiz` group.

For example, you can use `korlibs-crypto` as follows (replacing `X.X.X` with the latest release):

```kotlin
implementation("com.soywiz:korlibs-crypto:X.X.X")
```