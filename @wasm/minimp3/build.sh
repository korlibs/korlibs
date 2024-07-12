docker run --rm -v $(pwd):/src -u $(id -u):$(id -g) emscripten/emsdk:3.1.61-arm64 \
  emcc \
    -Oz \
    -s WASM=1 \
    -s EXPORTED_RUNTIME_METHODS='["cwrap", "getValue"]' \
    -s EXPORTED_FUNCTIONS="[\
      '_mp3dec_init', \
      '_mp3dec_decode_frame', \
      '_heap_reset' \
     ]" \
    -s ALLOW_MEMORY_GROWTH=1 \
    -s FILESYSTEM=0 \
    -o minimp3.wasm \
    -fno-builtin \
    --no-standard-libraries \
    --no-entry \
    -I . \
    minimp3.c \
    -g0 \

# docker run --rm -v $(pwd):/src -u $(id -u):$(id -g) emscripten/emsdk:3.1.61-arm64 /emsdk/upstream/bin/wasm-opt stb_image.wasm -Oz -o stb_image.wasm

#base64 -i minimp3.wasm -o minimp3.wasm.b64

cat minimp3.wasm | ~/go/bin/zlib -9 | base64 > minimp3.wasm.b64
