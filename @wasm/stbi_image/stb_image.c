// https://github.com/emscripten-core/emscripten/blob/daf5b264706d7835e6c8d16c805d35ede59e4ece/system/include/emscripten/emscripten.h

#define EMSCRIPTEN_KEEPALIVE __attribute__((used))

#include <stdlib.h>

void *realloc_sized(void *ptr, int old, int size) {
    return realloc(ptr, size);
}

#define STBI_NO_FAILURE_STRINGS
#define STBI_NO_STDIO
#define STBI_ASSERT(...)
#define STBI_NO_HDR
#define STBI_NO_PSD
#define STBI_NO_PIC
#define STBI_NO_PNM
#define STBI_NO_LINEAR
#define STBI_MALLOC malloc
#define STBI_FREE free
#define STBI_REALLOC_SIZED realloc_sized

#define STB_IMAGE_IMPLEMENTATION 1
//#define STBIDEF EMSCRIPTEN_KEEPALIVE
#include "stb_image.h"

#define STBIW_NO_STDIO
#define STBI_WRITE_NO_STDIO
#define STBIW_ASSERT(...)
#define STBIW_MALLOC malloc
#define STBIW_FREE free
#define STBIW_REALLOC_SIZED realloc_sized

#define STB_IMAGE_WRITE_IMPLEMENTATION 1
//#define STBIWDEF EMSCRIPTEN_KEEPALIVE
#include "stb_image_write.h"

typedef struct {
    unsigned char *currptr;
} WriteCtx;

//void heap_reset() {}

void stbi_write_func_mem(void *context, void *data, int size) {
    WriteCtx *ctx = ((WriteCtx *)context);
    unsigned char *inp = (unsigned char *)data;
    unsigned char *out = ctx->currptr;
    memcpy(ctx->currptr, (char *)data, size);
    ctx->currptr += size;
}

EMSCRIPTEN_KEEPALIVE int stbi_write_to_memory(int type, char *inp, int w, int h, unsigned char *out, int out_size, int quality) {
    WriteCtx ctx = { out };
    switch (type) {
        case 0: stbi_write_png_to_func(stbi_write_func_mem, &ctx, w, h, 4, inp, w * 4); break;
        case 1: stbi_write_bmp_to_func(stbi_write_func_mem, &ctx, w, h, 4, inp); break;
        case 2: stbi_write_tga_to_func(stbi_write_func_mem, &ctx, w, h, 4, inp); break;
        case 3: stbi_write_jpg_to_func(stbi_write_func_mem, &ctx, w, h, 4, inp, quality); break;
    }
    return ctx.currptr - out;
}