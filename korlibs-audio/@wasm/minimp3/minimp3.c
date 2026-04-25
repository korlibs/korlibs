#define MINIMP3_IMPLEMENTATION
#define EMSCRIPTEN_KEEPALIVE __attribute__((used))

#include "minimp3.h"

EMSCRIPTEN_KEEPALIVE void heap_reset() {
}