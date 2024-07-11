#ifndef STDLIB_H
#define STDLIB_H

int abs(int v) { return v < 0 ? -v : v; }

void *LAST_PTR = (void *)64;
void *GLOBAL_PTR = (void *)64;

// https://chromium.googlesource.com/external/llvm.org/clang/+/google/stable/test/CodeGen/builtins-wasm.c

void *memory_size() { return (void *)(__builtin_wasm_memory_size(0) << 16); }
void memory_grow(int delta) {
  __builtin_wasm_memory_grow(0, delta);
}

EMSCRIPTEN_KEEPALIVE void heap_reset() {
    LAST_PTR = GLOBAL_PTR = (void *)64;
}

EMSCRIPTEN_KEEPALIVE void *malloc(int size) {
    memory_size();

    void *ptr = GLOBAL_PTR;
    LAST_PTR = ptr;
    GLOBAL_PTR += size;

    if (GLOBAL_PTR >= memory_size()) {
        memory_grow((size >> 16) + 1);
    }

    return ptr;
}
void *realloc(void *ptr, int size) {
    LAST_PTR = GLOBAL_PTR = ptr;
    malloc(size);
    return ptr;
}
EMSCRIPTEN_KEEPALIVE void free(void *ptr) {
}

#endif
