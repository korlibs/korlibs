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
      '_heap_reset', \
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
    -g0 \

# docker run --rm -v $(pwd):/src -u $(id -u):$(id -g) emscripten/emsdk:3.1.61-arm64 /emsdk/upstream/bin/wasm-opt stb_image.wasm -Oz -o stb_image.wasm

#base64 -i stb_image.wasm -o stb_image.wasm.b64

cat stb_image.wasm | ~/go/bin/zlib -9 | base64 > stb_image.wasm.b64

# Define the paths
WASM_B64_FILE="stb_image.wasm.b64"
TARGET_FILE="../../korlibs-image-core/src@js/korlibs/image/core/Impl.CoreImage.stbiwasm.kt"

# Read the new base64 content from the file
NEW_BASE64=$(<"$WASM_B64_FILE")

# Use Python to replace the base64 string in the target file
python3 -c "
import re

# Read the new base64 content from the file
new_base64 = '''$NEW_BASE64'''

# Read the content of the target file
with open('$TARGET_FILE', 'r') as file:
    content = file.read()

# Replace the base64 string
content = re.sub(r'private val STBI_WASM_BYTES = Base64.decode\(\".*\"\)', f'private val STBI_WASM_BYTES = Base64.decode(\"{new_base64}\")', content)

# Write the updated content back to the file
with open('$TARGET_FILE', 'w') as file:
    file.write(content)
"

echo "Base64 string has been updated successfully in $TARGET_FILE"
