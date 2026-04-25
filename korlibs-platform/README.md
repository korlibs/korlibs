# korlibs-platform

<!-- BADGES -->
[![test](https://github.com/korlibs/korlibs-platform/actions/workflows/TEST.yml/badge.svg)](https://github.com/korlibs/korlibs-platform/actions/workflows/TEST.yml)
[![codecov](https://codecov.io/gh/korlibs/korlibs-platform/graph/badge.svg)](https://codecov.io/gh/korlibs/korlibs-platform)
[![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-platform)](https://central.sonatype.com/artifact/org.korge/korlibs-platform)
[![Discord](https://img.shields.io/discord/728582275884908604?logo=discord&label=Discord)](https://discord.korge.org/)
[![KDoc](https://img.shields.io/badge/docs-kdoc-blue)](https://korlibs.github.io/korlibs-platform/)
[![Documentation](https://img.shields.io/badge/docs-documentation-purple)](https://docs.korge.org/platform/)
<!-- /BADGES -->

`Korlibs-platform` is a small Kotlin Multiplatform (KMP) foundation library used across the Korlibs ecosystem.
It provides building blocks and thin "platform adapters" for higher-level Korlibs.

Supported platforms: JVM, Android, JS/WASM, and Kotlin/Native (Apple/Linux/Windows).

This library provides:

- Platform and runtime info: identify OS/arch/runtime/build variant in a portable way - via `Platform` (`Os`, `Runtime`, `Arch`, `Endian`, `BuildVariant`, etc).
- Cross-platform environment and path conventions: access environment variables and common separators uniformly - via `korlibs.io.lang.Environment` (`DIR_SEPARATOR`, `PATH_SEPARATOR`, `tempPath`, `userHome`).
- Portable exception types for IO: common IO exceptions for higher-level libs to throw/catch consistently across targets - `IOException`, `EOFException`, `FileNotFoundException`.
- Async/resource lifecycle and cancellation/closeable utilities: small components for structured resource cleanup in suspend code - `AsyncCloseable`, `suspend fun <T : AsyncCloseable?> T.use(...)`, `Cancellable`, `CancellableGroup`.
- Binary/memory helpers: performance-oriented utilities for building byte buffers and writing primitive values with explicit endianness - `korlibs.memory.ByteArrayBuilder` (see methods like `s16LE`, `f32BE`, etc. in the class).
- Minimal native image reference: a tiny interface `korlibs.image.bitmap.NativeImageRef` to reference platform-native image objects without depending on other libraries.
