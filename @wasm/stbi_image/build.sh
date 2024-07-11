#clang -I. -Os -fno-builtin --target=wasm32 --no-standard-libraries -Wl --no-entry \
#    -Wl --export=stbi_load_from_memory \
#    -Wl --export=malloc \
#    -Wl --export=heap_reset \
#    -Wl --allow-undefined \
#    -o wasm/stb_image.wasm \
#    -D STB_IMAGE_IMPLEMENTATION=1 \
#    stb_image.h
#  emcc -D STB_IMAGE_IMPLEMENTATION=1 stb_image.h stb_image_write.h -o stb_image.js


docker run --rm -v $(pwd):/src -u $(id -u):$(id -g) emscripten/emsdk:3.1.61-arm64 \
  emcc \
    -Oz \
    -s WASM=1 \
    -s EXPORTED_RUNTIME_METHODS='["cwrap", "getValue"]' \
    -s EXPORTED_FUNCTIONS="[\
      '_stbi_load_from_memory', \
      '_stbi_info_from_memory', \
      '_stbi_write_to_memory', \
      '_free', \
      '_malloc' \
     ]" \
    -s ALLOW_MEMORY_GROWTH=1 \
    -s FILESYSTEM=0 \
    -o stb_image.wasm \
    -fno-builtin \
    --no-standard-libraries \
    --no-entry \
    -I . \
    stb_image.c \
    -g0

#docker run --rm -v $(pwd):/src -u $(id -u):$(id -g) emscripten/emsdk:3.1.61-arm64 \
#  wasm-opt stb_image.wasm -o stb_image.opt.wasm -O --intrinsic-lowering -O