#ifndef STDLIB_H
#define STDLIB_H

#include <string.h>

int abs(int v) { return v < 0 ? -v : v; }

extern size_t __heap_base;
extern size_t __stack_high;

#define START_PTR (void *)(&__heap_base)
//#define START_PTR (void *)0x20000

void *LAST_PTR = (void*)0;
void *GLOBAL_PTR = (void*)0;

// https://chromium.googlesource.com/external/llvm.org/clang/+/google/stable/test/CodeGen/builtins-wasm.c

void *memory_size() { return (void *)(__builtin_wasm_memory_size(0) << 16); }
void memory_grow(int delta) {
  __builtin_wasm_memory_grow(0, delta);
}

int roundUp(int numToRound, int multiple) {
    if (multiple == 0) return numToRound;
    int remainder = numToRound % multiple;
    if (remainder == 0) return numToRound;
    return numToRound + multiple - remainder;
}

EMSCRIPTEN_KEEPALIVE void heap_reset() {
    LAST_PTR = GLOBAL_PTR = START_PTR;
}

EMSCRIPTEN_KEEPALIVE void *malloc(int size) {
    size = roundUp(size, 16);

    if (GLOBAL_PTR == 0) {
        LAST_PTR = GLOBAL_PTR = START_PTR;
    }

    void *ptr = GLOBAL_PTR;
    LAST_PTR = ptr;
    GLOBAL_PTR += size;

    if (GLOBAL_PTR >= memory_size()) {
        memory_grow((size >> 16) + 10);
    }

    return ptr;
}
void *realloc(void *ptr, int size) {
    //if (ptr == LAST_PTR) {
    //    GLOBAL_PTR = ptr;
    //    ptr = malloc(size);
    //} else {
        void *new_ptr = malloc(size);
        memcpy(new_ptr, ptr, size);
        ptr = new_ptr;
    //}
    return ptr;
}
EMSCRIPTEN_KEEPALIVE void free(void *ptr) {
}

#endif
