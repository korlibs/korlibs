# Korlibs

This repository hosts all libraries used by [KorGE (Kotlin Coroutine Game Engine)](https://github.com/korlibs/korge). Its main purpose is to make maintenance easier. KorGE itself is not part of it and lives in its own repo.

The old korlibs repo can be found here: <https://github.com/korlibs/korlibs-all>

## Overview

Here is a comprehensive list of the libraries available in the `org.korge:korlibs` collection, organized by their level of dependency.
Each library is designed to be modular and can be used independently. The levels indicate the dependency hierarchy, with Level 1 being the
most fundamental libraries that have no dependencies on other `korlibs`, and higher levels building upon the lower ones. This structure allows
you to easily pick and choose the libraries you need for your project without having to include unnecessary dependencies.

_TODO: List below needs to be updated to reflect the current state of the repo. Some libraries may have been merged with others._

### Level 1
- korlibs-annotations — Common annotations (`@Serializable`, `@Keep`, `@KeepNames`, `@DeprecatedParameter`)
- korlibs-bignumber — BigInteger and BigDecimal library for Kotlin Multiplatform
- korlibs-checksum — Checksum algorithms (CRC32, Adler32)
- korlibs-datastructure-core — Core primitive typed lists (`IntList`, `FloatList`, `DoubleList`) and fast array collections
- korlibs-encoding — Encoding/decoding utilities (Base64, Hex, ASCII)
- korlibs-inject — Lightweight dependency injection container
- korlibs-number — Specialised numeric types (`Fixed`, `Half`, `BFloat`, `Complex`)
- korlibs-platform — Platform detection, environment access, and common interfaces (`Closeable`, `AsyncCloseable`)

### Level 2

- korlibs-crypto — Cryptographic algorithms (AES, SHA, MD5, HMAC)
- korlibs-dyn — Dynamic value handling for maps, lists and arithmetic without strict typing
- korlibs-logge — Multiplatform logging library
- korlibs-math-cor — Core math utilities (clamping, interpolation, number parsing, alignment)
- korlibs-memory — Low-level memory and byte-array utilities (`Buffer`, typed array read/write helpers)
- korlibs-string — String utilities (charsets, case conversion, extensions, `WString`)
- korlibs-time — Multiplatform date, time, and duration utilities

### Level 3

- korlibs-concurrent — Concurrency primitives (locks, threads, async queues and dispatchers)
- korlibs-io-stream — Async stream abstractions (`AsyncInputStream`, `AsyncOutputStream`, ring buffers)
- korlibs-math-vector — 2D/3D vector math, angles, shapes, AABB, transforms
- korlibs-serialization — Serialization formats (JSON, XML, CSV, TOML, Properties)

### Level 4

- korlibs-compression — Compression algorithms (Deflate/Inflate, LZO, 7-Zip)
- korlibs-datastructure] — Rich collection types (typed ArrayLists, Array2D, deques, spatial structures)
- korlibs-io-fs — Filesystem access abstraction (`SystemFS`)
- korlibs-jseval — JavaScript expression evaluation from Kotlin
- korlibs-template — Kotlin-based text/HTML templating engine

### Level 5

- korlibs-ffi — Foreign Function Interface (FFI) for calling native libraries
- korlibs-io-network-core — Core network abstractions (async sockets, HTTP fetch, SSL)
- korlibs-io-vfs — Virtual File System (VFS) abstraction (local, memory, jail VFS)
- korlibs-math — Extended math (Bézier curves, geometry shapes, vector operations)

### Level 6

- korlibs-audio-core — Core audio data types and platform audio output abstraction
- korlibs-io — Full I/O library combining streams, VFS, networking and filesystem

### Level 7

- korlibs-audio — Audio playback and decoding (MOD, S3M tracker formats and more)
- korlibs-wasm — WebAssembly interpreter and WASM library integration

### Level 8

- korlibs-image-core — Core image data type (`CoreImage`) and basic image abstractions

### Level 9

- korlibs-image — Image loading, decoding, atlas packing and manipulation

### Level 10

- KorGE (The game engine itself. It is not included in this repository. It can be found at <https://github.com/korlibs/korge>)

## Usage

Modules are published to Maven Central under the `org.korge` group.

For example, you can use `korlibs-crypto` as follows (replacing `7.0.0-alpha1` with the latest release):

```kotlin
implementation("org.korge:korlibs-crypto:7.0.0-alpha1")
```

## Contributing

If your pull request breaks the Korge unit tests run by CI (the `korge-test` job), see
[Contributing changes that affect both Korlibs and Korge](docs/contributing-cross-repo-changes.md)
for how to provide a companion Korge branch so both repos can be updated together.
