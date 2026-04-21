# Korlibs

This repository hosts all libraries used by [KorGE (Kotlin Coroutine Game Engine)](https://github.com/korlibs/korge). Its main purpose is to make maintenance easier. KorGE itself is not part of it and lives in its own repo.

The old korlibs repo can be found here: <https://github.com/korlibs/korlibs-all>

## Overview

Here is a comprehensive list of the libraries available in the `org.korge:korlibs` collection, organized by their level of dependency.
Each library is designed to be modular and can be used independently. The levels indicate the dependency hierarchy, with Level 1 being the
most fundamental libraries that have no dependencies on other `korlibs`, and higher levels building upon the lower ones. This structure allows
you to easily pick and choose the libraries you need for your project without having to include unnecessary dependencies.

### Level 1
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-annotations)](https://central.sonatype.com/artifact/org.korge/korlibs-annotations) [korlibs-annotations](https://github.com/korlibs/korlibs-annotations) — Common annotations (`@Serializable`, `@Keep`, `@KeepNames`, `@DeprecatedParameter`)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-bignumber)](https://central.sonatype.com/artifact/org.korge/korlibs-bignumber) [korlibs-bignumber](https://github.com/korlibs/korlibs-bignumber) — BigInteger and BigDecimal library for Kotlin Multiplatform
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-checksum)](https://central.sonatype.com/artifact/org.korge/korlibs-checksum) [korlibs-checksum](https://github.com/korlibs/korlibs-checksum) — Checksum algorithms (CRC32, Adler32)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-datastructure-core)](https://central.sonatype.com/artifact/org.korge/korlibs-datastructure-core) [korlibs-datastructure-core](https://github.com/korlibs/korlibs-datastructure-core) — Core primitive typed lists (`IntList`, `FloatList`, `DoubleList`) and fast array collections
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-encoding)](https://central.sonatype.com/artifact/org.korge/korlibs-encoding) [korlibs-encoding](https://github.com/korlibs/korlibs-encoding) — Encoding/decoding utilities (Base64, Hex, ASCII)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-inject)](https://central.sonatype.com/artifact/org.korge/korlibs-inject) [korlibs-inject](https://github.com/korlibs/korlibs-inject) — Lightweight dependency injection container
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-number)](https://central.sonatype.com/artifact/org.korge/korlibs-number) [korlibs-number](https://github.com/korlibs/korlibs-number) — Specialised numeric types (`Fixed`, `Half`, `BFloat`, `Complex`)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-platform)](https://central.sonatype.com/artifact/org.korge/korlibs-platform) [korlibs-platform](https://github.com/korlibs/korlibs-platform) — Platform detection, environment access, and common interfaces (`Closeable`, `AsyncCloseable`)

### Level 2

- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-crypto)](https://central.sonatype.com/artifact/org.korge/korlibs-crypto) [korlibs-crypto](https://github.com/korlibs/korlibs-crypto) — Cryptographic algorithms (AES, SHA, MD5, HMAC)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-dyn)](https://central.sonatype.com/artifact/org.korge/korlibs-dyn) [korlibs-dyn](https://github.com/korlibs/korlibs-dyn) — Dynamic value handling for maps, lists and arithmetic without strict typing
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-logger)](https://central.sonatype.com/artifact/org.korge/korlibs-logger) [korlibs-logger](https://github.com/korlibs/korlibs-logger) — Multiplatform logging library
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-math-core)](https://central.sonatype.com/artifact/org.korge/korlibs-math-core) [korlibs-math-core](https://github.com/korlibs/korlibs-math-core) — Core math utilities (clamping, interpolation, number parsing, alignment)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-memory)](https://central.sonatype.com/artifact/org.korge/korlibs-memory) [korlibs-memory](https://github.com/korlibs/korlibs-memory) — Low-level memory and byte-array utilities (`Buffer`, typed array read/write helpers)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-string)](https://central.sonatype.com/artifact/org.korge/korlibs-string) [korlibs-string](https://github.com/korlibs/korlibs-string) — String utilities (charsets, case conversion, extensions, `WString`)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-time)](https://central.sonatype.com/artifact/org.korge/korlibs-time) [korlibs-time](https://github.com/korlibs/korlibs-time) — Multiplatform date, time, and duration utilities

### Level 3

- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-concurrent)](https://central.sonatype.com/artifact/org.korge/korlibs-concurrent) [korlibs-concurrent](https://github.com/korlibs/korlibs-concurrent) — Concurrency primitives (locks, threads, async queues and dispatchers)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-io-stream)](https://central.sonatype.com/artifact/org.korge/korlibs-io-stream) [korlibs-io-stream](https://github.com/korlibs/korlibs-io-stream) — Async stream abstractions (`AsyncInputStream`, `AsyncOutputStream`, ring buffers)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-math-vector)](https://central.sonatype.com/artifact/org.korge/korlibs-math-vector) [korlibs-math-vector](https://github.com/korlibs/korlibs-math-vector) — 2D/3D vector math, angles, shapes, AABB, transforms
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-serialization)](https://central.sonatype.com/artifact/org.korge/korlibs-serialization) [korlibs-serialization](https://github.com/korlibs/korlibs-serialization) — Serialization formats (JSON, XML, CSV, TOML, Properties)

### Level 4

- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-compression)](https://central.sonatype.com/artifact/org.korge/korlibs-compression) [korlibs-compression](https://github.com/korlibs/korlibs-compression) — Compression algorithms (Deflate/Inflate, LZO, 7-Zip)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-datastructure)](https://central.sonatype.com/artifact/org.korge/korlibs-datastructure) [korlibs-datastructure](https://github.com/korlibs/korlibs-datastructure) — Rich collection types (typed ArrayLists, Array2D, deques, spatial structures)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-io-fs)](https://central.sonatype.com/artifact/org.korge/korlibs-io-fs) [korlibs-io-fs](https://github.com/korlibs/korlibs-io-fs) — Filesystem access abstraction (`SystemFS`)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-jseval)](https://central.sonatype.com/artifact/org.korge/korlibs-jseval) [korlibs-jseval](https://github.com/korlibs/korlibs-jseval) — JavaScript expression evaluation from Kotlin
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-template)](https://central.sonatype.com/artifact/org.korge/korlibs-template) [korlibs-template](https://github.com/korlibs/korlibs-template) — Kotlin-based text/HTML templating engine

### Level 5

- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-ffi)](https://central.sonatype.com/artifact/org.korge/korlibs-ffi) [korlibs-ffi](https://github.com/korlibs/korlibs-ffi) — Foreign Function Interface (FFI) for calling native libraries
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-io-network-core)](https://central.sonatype.com/artifact/org.korge/korlibs-io-network-core) [korlibs-io-network-core](https://github.com/korlibs/korlibs-io-network-core) — Core network abstractions (async sockets, HTTP fetch, SSL)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-io-vfs)](https://central.sonatype.com/artifact/org.korge/korlibs-io-vfs) [korlibs-io-vfs](https://github.com/korlibs/korlibs-io-vfs) — Virtual File System (VFS) abstraction (local, memory, jail VFS)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-math)](https://central.sonatype.com/artifact/org.korge/korlibs-math) [korlibs-math](https://github.com/korlibs/korlibs-math) — Extended math (Bézier curves, geometry shapes, vector operations)

### Level 6

- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-audio-core)](https://central.sonatype.com/artifact/org.korge/korlibs-audio-core) [korlibs-audio-core](https://github.com/korlibs/korlibs-audio-core) — Core audio data types and platform audio output abstraction
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-io)](https://central.sonatype.com/artifact/org.korge/korlibs-io) [korlibs-io](https://github.com/korlibs/korlibs-io) — Full I/O library combining streams, VFS, networking and filesystem

### Level 7

- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-audio)](https://central.sonatype.com/artifact/org.korge/korlibs-audio) [korlibs-audio](https://github.com/korlibs/korlibs-audio) — Audio playback and decoding (MOD, S3M tracker formats and more)
- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-wasm)](https://central.sonatype.com/artifact/org.korge/korlibs-wasm) [korlibs-wasm](https://github.com/korlibs/korlibs-wasm) — WebAssembly interpreter and WASM library integration

### Level 8

- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-image-core)](https://central.sonatype.com/artifact/org.korge/korlibs-image-core) [korlibs-image-core](https://github.com/korlibs/korlibs-image-core) — Core image data type (`CoreImage`) and basic image abstractions

### Level 9

- [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-image)](https://central.sonatype.com/artifact/org.korge/korlibs-image) [korlibs-image](https://github.com/korlibs/korlibs-image) — Image loading, decoding, atlas packing and manipulation

### Level 10

- KorGE (The game engine itself. It is not included in this repository. It can be found at <https://github.com/korlibs/korge>)

## Usage

Modules are published to Maven Central under the `org.korge` group.

For example, you can use `korlibs-crypto` as follows (replacing `X.X.X` with the latest release):

```kotlin
implementation("org.korge:korlibs-crypto:X.X.X")
```
