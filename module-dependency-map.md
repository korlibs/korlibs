# Korlibs Module Dependency Map

## Forward Dependencies

Legend: module -> internal korlibs dependencies (with dependency type and source location in `module.yaml`)

- `korlibs-annotations` -> (no internal korlibs dependencies declared in its `module.yaml`)
- `korlibs-audio` -> `korlibs-annotations` (commonMainApi; korlibs-audio/korlibs-audio/module.yaml:22), `korlibs-audio-core` (commonMainApi; korlibs-audio/korlibs-audio/module.yaml:14), `korlibs-concurrent` (commonMainApi; korlibs-audio/korlibs-audio/module.yaml:15), `korlibs-ffi-legacy` (commonMainApi; korlibs-audio/korlibs-audio/module.yaml:20), `korlibs-io` (commonTestImplementation; korlibs-audio/korlibs-audio/module.yaml:26), `korlibs-io-vfs` (commonMainApi; korlibs-audio/korlibs-audio/module.yaml:16), `korlibs-logger` (commonMainApi; korlibs-audio/korlibs-audio/module.yaml:21), `korlibs-math-core` (commonMainApi; korlibs-audio/korlibs-audio/module.yaml:18), `korlibs-math-vector` (commonMainApi; korlibs-audio/korlibs-audio/module.yaml:19), `korlibs-time-core` (commonMainApi; korlibs-audio/korlibs-audio/module.yaml:17)
- `korlibs-audio-core` -> `korlibs-ffi-legacy` (commonMainImplementation; korlibs-audio-core/korlibs-audio-core/module.yaml:10), `korlibs-io-fs` (commonMainImplementation; korlibs-audio-core/korlibs-audio-core/module.yaml:12), `korlibs-math-core` (commonMainImplementation; korlibs-audio-core/korlibs-audio-core/module.yaml:8), `korlibs-math-vector` (commonMainApi; korlibs-audio-core/korlibs-audio-core/module.yaml:9), `korlibs-platform` (commonMainImplementation; korlibs-audio-core/korlibs-audio-core/module.yaml:11)
- `korlibs-bignumber` -> (no internal korlibs dependencies declared in its `module.yaml`)
- `korlibs-checksum` -> (no internal korlibs dependencies declared in its `module.yaml`)
- `korlibs-compression` -> `korlibs-checksum` (commonMainImplementation; korlibs-compression/korlibs-compression/module.yaml:13), `korlibs-concurrent` (commonMainApi; korlibs-compression/korlibs-compression/module.yaml:12), `korlibs-datastructure-core` (commonMainImplementation; korlibs-compression/korlibs-compression/module.yaml:15), `korlibs-io-stream` (commonMainApi; korlibs-compression/korlibs-compression/module.yaml:11), `korlibs-math-core` (commonMainImplementation; korlibs-compression/korlibs-compression/module.yaml:14)
- `korlibs-concurrent` -> `korlibs-datastructure-core` (commonMainImplementation; korlibs-concurrent/korlibs-concurrent/module.yaml:14), `korlibs-platform` (commonMainApi; korlibs-concurrent/korlibs-concurrent/module.yaml:13), `korlibs-time-core` (commonMainImplementation; korlibs-concurrent/korlibs-concurrent/module.yaml:12)
- `korlibs-crypto` -> `korlibs-encoding` (commonMainApi; korlibs-crypto/korlibs-crypto/module.yaml:11)
- `korlibs-datastructure` -> `korlibs-concurrent` (commonMainApi; korlibs-datastructure/korlibs-datastructure/module.yaml:8), `korlibs-datastructure-core` (commonMainApi; korlibs-datastructure/korlibs-datastructure/module.yaml:11), `korlibs-math-vector` (commonMainApi; korlibs-datastructure/korlibs-datastructure/module.yaml:10), `korlibs-platform` (commonTestImplementation; korlibs-datastructure/korlibs-datastructure/module.yaml:14), `korlibs-time-core` (commonMainApi; korlibs-datastructure/korlibs-datastructure/module.yaml:9)
- `korlibs-datastructure-core` -> (no internal korlibs dependencies declared in its `module.yaml`)
- `korlibs-dyn` -> `korlibs-datastructure-core` (commonMainApi; korlibs-dyn/korlibs-dyn/module.yaml:11)
- `korlibs-encoding` -> (no internal korlibs dependencies declared in its `module.yaml`)
- `korlibs-ffi` -> (no internal korlibs dependencies declared in its `module.yaml`)
- `korlibs-ffi-ksp` -> (no internal korlibs dependencies declared in its `module.yaml`)
- `korlibs-ffi-legacy` -> `korlibs-annotations` (commonMainApi; korlibs-ffi/korlibs-ffi-legacy/module.yaml:12), `korlibs-concurrent` (commonMainApi; korlibs-ffi/korlibs-ffi-legacy/module.yaml:13), `korlibs-datastructure` (commonMainApi; korlibs-ffi/korlibs-ffi-legacy/module.yaml:10), `korlibs-io-fs` (commonMainApi; korlibs-ffi/korlibs-ffi-legacy/module.yaml:8), `korlibs-memory` (commonMainApi; korlibs-ffi/korlibs-ffi-legacy/module.yaml:11), `korlibs-platform` (commonMainApi; korlibs-ffi/korlibs-ffi-legacy/module.yaml:9)
- `korlibs-image` -> `korlibs-checksum` (commonMainImplementation; korlibs-image/korlibs-image/module.yaml:25), `korlibs-compression` (commonMainImplementation; korlibs-image/korlibs-image/module.yaml:18), `korlibs-dyn` (commonMainImplementation; korlibs-image/korlibs-image/module.yaml:19), `korlibs-encoding` (commonMainImplementation; korlibs-image/korlibs-image/module.yaml:21), `korlibs-ffi-legacy` (commonMainApi; korlibs-image/korlibs-image/module.yaml:15), `korlibs-image-core` (commonMainImplementation; korlibs-image/korlibs-image/module.yaml:12), `korlibs-io` (commonMainApi; korlibs-image/korlibs-image/module.yaml:11), `korlibs-io` (commonTestImplementation; korlibs-image/korlibs-image/module.yaml:29), `korlibs-io-vfs` (commonMainApi; korlibs-image/korlibs-image/module.yaml:13), `korlibs-math` (commonMainApi; korlibs-image/korlibs-image/module.yaml:14), `korlibs-math-core` (commonMainImplementation; korlibs-image/korlibs-image/module.yaml:20), `korlibs-platform` (commonMainApi; korlibs-image/korlibs-image/module.yaml:17), `korlibs-serialization` (commonMainImplementation; korlibs-image/korlibs-image/module.yaml:22), `korlibs-string` (commonMainApi; korlibs-image/korlibs-image/module.yaml:16), `korlibs-string` (commonMainImplementation; korlibs-image/korlibs-image/module.yaml:24), `korlibs-wasm` (commonMainImplementation; korlibs-image/korlibs-image/module.yaml:23)
- `korlibs-image-core` -> `korlibs-annotations` (commonMainImplementation; korlibs-image-core/korlibs-image-core/module.yaml:11), `korlibs-compression` (jsMainImplementation; korlibs-image-core/korlibs-image-core/module.yaml:22), `korlibs-wasm` (jsMainImplementation; korlibs-image-core/korlibs-image-core/module.yaml:21)
- `korlibs-inject` -> (no internal korlibs dependencies declared in its `module.yaml`)
- `korlibs-io` -> `korlibs-checksum` (commonMainApi; korlibs-io/korlibs-io/module.yaml:13), `korlibs-compression` (commonMainApi; korlibs-io/korlibs-io/module.yaml:14), `korlibs-crypto` (commonMainApi; korlibs-io/korlibs-io/module.yaml:22), `korlibs-datastructure` (commonMainApi; korlibs-io/korlibs-io/module.yaml:25), `korlibs-dyn` (commonMainApi; korlibs-io/korlibs-io/module.yaml:29), `korlibs-encoding` (commonMainApi; korlibs-io/korlibs-io/module.yaml:23), `korlibs-ffi-legacy` (commonMainApi; korlibs-io/korlibs-io/module.yaml:21), `korlibs-io-fs` (commonMainImplementation; korlibs-io/korlibs-io/module.yaml:32), `korlibs-io-network-core` (commonMainApi; korlibs-io/korlibs-io/module.yaml:18), `korlibs-io-stream` (commonMainApi; korlibs-io/korlibs-io/module.yaml:17), `korlibs-io-vfs` (commonMainApi; korlibs-io/korlibs-io/module.yaml:16), `korlibs-jseval` (commonMainApi; korlibs-io/korlibs-io/module.yaml:15), `korlibs-logger` (commonMainApi; korlibs-io/korlibs-io/module.yaml:28), `korlibs-math-core` (commonMainApi; korlibs-io/korlibs-io/module.yaml:19), `korlibs-memory` (commonMainApi; korlibs-io/korlibs-io/module.yaml:20), `korlibs-number` (commonMainApi; korlibs-io/korlibs-io/module.yaml:26), `korlibs-platform` (commonMainApi; korlibs-io/korlibs-io/module.yaml:24), `korlibs-serialization` (commonMainApi; korlibs-io/korlibs-io/module.yaml:31), `korlibs-string` (commonMainApi; korlibs-io/korlibs-io/module.yaml:30), `korlibs-time` (commonTestImplementation; korlibs-io/korlibs-io/module.yaml:39), `korlibs-time-core` (commonMainApi; korlibs-io/korlibs-io/module.yaml:27)
- `korlibs-io-fs` -> `korlibs-io-stream` (commonMainApi; korlibs-io-fs/korlibs-io-fs/module.yaml:14), `korlibs-platform` (commonMainApi; korlibs-io-fs/korlibs-io-fs/module.yaml:13), `korlibs-time` (commonTestImplementation; korlibs-io-fs/korlibs-io-fs/module.yaml:19)
- `korlibs-io-network-core` -> `korlibs-annotations` (commonMainApi; korlibs-io-network-core/korlibs-io-network-core/module.yaml:13), `korlibs-datastructure` (commonMainImplementation; korlibs-io-network-core/korlibs-io-network-core/module.yaml:15), `korlibs-io-stream` (commonMainApi; korlibs-io-network-core/korlibs-io-network-core/module.yaml:18), `korlibs-io-stream` (commonMainImplementation; korlibs-io-network-core/korlibs-io-network-core/module.yaml:14), `korlibs-logger` (commonMainImplementation; korlibs-io-network-core/korlibs-io-network-core/module.yaml:16), `korlibs-platform` (commonMainApi; korlibs-io-network-core/korlibs-io-network-core/module.yaml:17)
- `korlibs-io-nodejs` -> `korlibs-io` (commonMainImplementation; korlibs-io/korlibs-io-nodejs/module.yaml:13), `korlibs-platform` (commonMainImplementation; korlibs-io/korlibs-io-nodejs/module.yaml:12), `korlibs-time` (commonTestImplementation; korlibs-io/korlibs-io-nodejs/module.yaml:17)
- `korlibs-io-stream` -> `korlibs-datastructure-core` (commonMainApi; korlibs-io-stream/korlibs-io-stream/module.yaml:15), `korlibs-math-core` (commonMainApi; korlibs-io-stream/korlibs-io-stream/module.yaml:17), `korlibs-memory` (commonMainApi; korlibs-io-stream/korlibs-io-stream/module.yaml:14), `korlibs-platform` (commonMainApi; korlibs-io-stream/korlibs-io-stream/module.yaml:13), `korlibs-string` (commonMainApi; korlibs-io-stream/korlibs-io-stream/module.yaml:16), `korlibs-time` (commonTestImplementation; korlibs-io-stream/korlibs-io-stream/module.yaml:22)
- `korlibs-io-vfs` -> `korlibs-concurrent` (commonMainImplementation; korlibs-io-vfs/korlibs-io-vfs/module.yaml:14), `korlibs-io-fs` (commonMainImplementation; korlibs-io-vfs/korlibs-io-vfs/module.yaml:13), `korlibs-io-stream` (commonMainApi; korlibs-io-vfs/korlibs-io-vfs/module.yaml:16), `korlibs-platform` (commonMainApi; korlibs-io-vfs/korlibs-io-vfs/module.yaml:15), `korlibs-string` (commonMainApi; korlibs-io-vfs/korlibs-io-vfs/module.yaml:18), `korlibs-time` (commonTestImplementation; korlibs-io-vfs/korlibs-io-vfs/module.yaml:23), `korlibs-time-core` (commonMainApi; korlibs-io-vfs/korlibs-io-vfs/module.yaml:17)
- `korlibs-jseval` -> `korlibs-concurrent` (commonMainImplementation; korlibs-jseval/korlibs-jseval/module.yaml:9), `korlibs-platform` (commonMainImplementation; korlibs-jseval/korlibs-jseval/module.yaml:8), `korlibs-serialization` (commonMainImplementation; korlibs-jseval/korlibs-jseval/module.yaml:10), `korlibs-time` (commonTestImplementation; korlibs-jseval/korlibs-jseval/module.yaml:14)
- `korlibs-logger` -> `korlibs-platform` (commonMainApi; korlibs-logger/korlibs-logger/module.yaml:9)
- `korlibs-math` -> `korlibs-datastructure` (commonMainApi; korlibs-math/korlibs-math/module.yaml:11), `korlibs-math-core` (commonMainApi; korlibs-math/korlibs-math/module.yaml:8), `korlibs-math-vector` (commonMainApi; korlibs-math/korlibs-math/module.yaml:9), `korlibs-number` (commonMainApi; korlibs-math/korlibs-math/module.yaml:12), `korlibs-platform` (commonMainApi; korlibs-math/korlibs-math/module.yaml:10), `korlibs-platform` (commonTestImplementation; korlibs-math/korlibs-math/module.yaml:15)
- `korlibs-math-core` -> `korlibs-number` (commonMainApi; korlibs-math-core/korlibs-math-core/module.yaml:12), `korlibs-platform` (commonMainImplementation; korlibs-math-core/korlibs-math-core/module.yaml:11)
- `korlibs-math-vector` -> `korlibs-math-core` (commonMainApi; korlibs-math-vector/korlibs-math-vector/module.yaml:8), `korlibs-platform` (commonMainImplementation; korlibs-math-vector/korlibs-math-vector/module.yaml:9)
- `korlibs-memory` -> `korlibs-platform` (commonTestApi; korlibs-memory/korlibs-memory/module.yaml:11)
- `korlibs-number` -> (no internal korlibs dependencies declared in its `module.yaml`)
- `korlibs-platform` -> (no internal korlibs dependencies declared in its `module.yaml`)
- `korlibs-serialization` -> `korlibs-datastructure-core` (commonMainImplementation; korlibs-serialization/korlibs-serialization/module.yaml:13), `korlibs-math-core` (commonMainImplementation; korlibs-serialization/korlibs-serialization/module.yaml:12), `korlibs-platform` (commonMainImplementation; korlibs-serialization/korlibs-serialization/module.yaml:14), `korlibs-string` (commonMainImplementation; korlibs-serialization/korlibs-serialization/module.yaml:11)
- `korlibs-simple` -> (no internal korlibs dependencies declared in its `module.yaml`)
- `korlibs-string` -> `korlibs-platform` (commonMainApi; korlibs-string/korlibs-string/module.yaml:12)
- `korlibs-template` -> `korlibs-platform` (commonMainApi; korlibs-template/korlibs-template/module.yaml:9), `korlibs-serialization` (commonMainApi; korlibs-template/korlibs-template/module.yaml:8), `korlibs-string` (commonMainImplementation; korlibs-template/korlibs-template/module.yaml:10)
- `korlibs-time` -> `korlibs-time-core` (commonMainApi; korlibs-time/korlibs-time/module.yaml:11)
- `korlibs-time-core` -> `korlibs-annotations` (commonMainApi; korlibs-time/korlibs-time-core/module.yaml:11)
- `korlibs-wasm` -> `korlibs-compression` (commonMainImplementation; korlibs-wasm/korlibs-wasm/module.yaml:16), `korlibs-datastructure` (commonMainApi; korlibs-wasm/korlibs-wasm/module.yaml:15), `korlibs-io` (commonTestImplementation; korlibs-wasm/korlibs-wasm/module.yaml:27), `korlibs-logger` (commonMainApi; korlibs-wasm/korlibs-wasm/module.yaml:13), `korlibs-math-core` (commonMainApi; korlibs-wasm/korlibs-wasm/module.yaml:14), `korlibs-memory` (commonMainApi; korlibs-wasm/korlibs-wasm/module.yaml:12), `korlibs-platform` (commonMainImplementation; korlibs-wasm/korlibs-wasm/module.yaml:11), `korlibs-serialization` (androidMainImplementation; korlibs-wasm/korlibs-wasm/module.yaml:19)

## Reverse Dependencies

This section lists the reverse dependencies of each module, grouped by "levels". Level 1 modules have no internal korlibs
dependencies, while higher level modules depend on lower level modules. The levels are determined by the longest path from
a module to a leaf module (a module with no dependencies). Only non-test (main) dependencies determine the level.

Legend: `dependency` <- used by modules (*(test only)* = only a test-scope dependency)

### Level 1

- `korlibs-annotations` <- `korlibs-audio`, `korlibs-ffi-legacy`, `korlibs-image-core`, `korlibs-io-network-core`, `korlibs-time-core`
- `korlibs-bignumber`
- `korlibs-checksum` <- `korlibs-compression`, `korlibs-image`, `korlibs-io`
- `korlibs-datastructure-core` <- `korlibs-compression`, `korlibs-concurrent`, `korlibs-datastructure`, `korlibs-dyn`, `korlibs-io-stream`, `korlibs-serialization`
- `korlibs-encoding` <- `korlibs-crypto`, `korlibs-image`, `korlibs-io`
- `korlibs-ffi`
- `korlibs-ffi-ksp`
- `korlibs-inject`
- `korlibs-memory` <- `korlibs-ffi-legacy`, `korlibs-io`, `korlibs-io-stream`, `korlibs-wasm`
- `korlibs-number` <- `korlibs-io`, `korlibs-math`, `korlibs-math-core`
- `korlibs-platform` <- `korlibs-audio-core`, `korlibs-concurrent`, `korlibs-ffi-legacy`, `korlibs-image`, `korlibs-io`, `korlibs-io-fs`, `korlibs-io-network-core`,
                        `korlibs-io-nodejs`, `korlibs-io-stream`, `korlibs-io-vfs`, `korlibs-jseval`, `korlibs-logger`, `korlibs-math`, `korlibs-math-core`,
                        `korlibs-math-vector`, `korlibs-serialization`, `korlibs-string`, `korlibs-template`, `korlibs-wasm`, `korlibs-datastructure` *(test only)*,
                        `korlibs-memory` *(test only)*
- `korlibs-simple`

### Level 2

- `korlibs-crypto` <- `korlibs-io`
- `korlibs-dyn` <- `korlibs-image`, `korlibs-io`
- `korlibs-logger` <- `korlibs-audio`, `korlibs-io`, `korlibs-io-network-core`, `korlibs-wasm`
- `korlibs-math-core` <- `korlibs-audio`, `korlibs-audio-core`, `korlibs-compression`, `korlibs-image`, `korlibs-io`, `korlibs-io-stream`, `korlibs-math`,
                         `korlibs-math-vector`, `korlibs-serialization`, `korlibs-wasm`
- `korlibs-string` <- `korlibs-image`, `korlibs-io`, `korlibs-io-stream`, `korlibs-io-vfs`, `korlibs-serialization`, `korlibs-template`
- `korlibs-time-core` <- `korlibs-audio`, `korlibs-concurrent`, `korlibs-datastructure`, `korlibs-io`, `korlibs-io-vfs`, `korlibs-time`

### Level 3

- `korlibs-concurrent` <- `korlibs-audio`, `korlibs-compression`, `korlibs-datastructure`, `korlibs-ffi-legacy`, `korlibs-io-vfs`, `korlibs-jseval`
- `korlibs-io-stream` <- `korlibs-compression`, `korlibs-io`, `korlibs-io-fs`, `korlibs-io-network-core`, `korlibs-io-vfs`
- `korlibs-math-vector` <- `korlibs-audio`, `korlibs-audio-core`, `korlibs-datastructure`, `korlibs-math`
- `korlibs-serialization` <- `korlibs-image`, `korlibs-io`, `korlibs-jseval`, `korlibs-template`, `korlibs-wasm`
- `korlibs-time` <- `korlibs-io` *(test only)*, `korlibs-io-fs` *(test only)*, `korlibs-io-nodejs` *(test only)*, `korlibs-io-stream` *(test only)*,
                    `korlibs-io-vfs` *(test only)*, `korlibs-jseval` *(test only)*

### Level 4

- `korlibs-compression` <- `korlibs-image`, `korlibs-image-core`, `korlibs-io`, `korlibs-wasm`
- `korlibs-datastructure` <- `korlibs-ffi-legacy`, `korlibs-io`, `korlibs-io-network-core`, `korlibs-math`, `korlibs-wasm`
- `korlibs-io-fs` <- `korlibs-audio-core`, `korlibs-ffi-legacy`, `korlibs-io`, `korlibs-io-vfs`
- `korlibs-jseval` <- `korlibs-io`
- `korlibs-template`

### Level 5

- `korlibs-ffi-legacy` <- `korlibs-audio`, `korlibs-audio-core`, `korlibs-image`, `korlibs-io`
- `korlibs-io-network-core` <- `korlibs-io`
- `korlibs-io-vfs` <- `korlibs-audio`, `korlibs-image`, `korlibs-io`
- `korlibs-math` <- `korlibs-image`
- `korlibs-wasm` <- `korlibs-image`, `korlibs-image-core`

### Level 6

- `korlibs-audio-core` <- `korlibs-audio`
- `korlibs-image-core` <- `korlibs-image`
- `korlibs-io` <- `korlibs-image`, `korlibs-io-nodejs`, `korlibs-audio` *(test only)*, `korlibs-wasm` *(test only)*

### Level 7

- `korlibs-audio` ← **end of chain** (nothing depends on it)
- `korlibs-image` ← **end of chain** (nothing depends on it)
- `korlibs-io-nodejs` ← **end of chain** (nothing depends on it)
