#!/usr/bin/env -S deno run --allow-env --allow-read
const wasmBytes = Deno.readFileSync('minimp3.wasm') // slightly better, not much
//const wasmBytes = Deno.readFileSync('minimp3.nosimd.wasm')

const mp3Bytes = Deno.readFileSync('../../korlibs-audio/testresources/Snowland.mp3')
//const mp3Bytes = Deno.readFileSync('l3-nonstandard-sin1k0db_lame_vbrtag.pcm')
//const mp3Bytes = Deno.readFileSync('l3-sin1k0db.pcm')
//const mp3Bytes = Deno.readFileSync('l3-compl.pcm')

function printBytes(fd: number, c: Uint8Array) {
    //console.log(fd, c);
    Deno.stdout.writeSync(c)
}

const wasm = await WebAssembly.instantiate(wasmBytes, {
    env: {
        abort: () => { throw new Error(); }
    },
    // https://github.com/WebAssembly/WASI/blob/main/legacy/preview1/docs.md#fd_write
    wasi_snapshot_preview1: {
        proc_exit: () => { throw new Error(); },
        fd_close: () => { throw new Error(); },
        fd_write: function (fd: number, iov: number, iovcnt: number, pnum: number) {
            let num = 0;
            for (let i = 0; i < iovcnt; i++) {
                const ptr = HEAPU32[iov >> 2];
                const len = HEAPU32[iov + 4 >> 2];
                iov += 8;
                printBytes(fd, HEAPU8.subarray(ptr, ptr + len))
                //for (let j = 0; j < len; j++) printChar(fd, HEAPU8[ptr + j])
                num += len
            }
            HEAPU32[pnum >> 2] = num;
            return 0
        },
        fd_seek: () => { throw new Error(); },
    },
});

const exports = wasm.instance.exports as any
const mem = new Uint8Array(exports.memory.buffer);
const HEAPU32 = new Uint32Array(exports.memory.buffer);
const HEAPU8 = new Uint8Array(exports.memory.buffer);
const memData = new DataView(mem.buffer);
console.log(Object.keys(exports));

const dec = exports._emscripten_stack_alloc(8 * 1024); // 6668
const mp3PtrSize = 2048;
const mp3Ptr = exports._emscripten_stack_alloc(mp3PtrSize);
const pcmData = exports._emscripten_stack_alloc(1152 * 2 * 2); // 1152 * 2 * 2 // 16 * 1024
const infoPtr = exports._emscripten_stack_alloc(32); // 24
//console.log(mp3Bytes.byteLength);

// void mp3dec_init(mp3dec_t *dec);

//mp3dec_frame_info_t: int frame_bytes, frame_offset, channels, hz, layer, bitrate_kbps;
// int mp3dec_decode_frame(mp3dec_t *dec, const uint8_t *mp3, int mp3_bytes, mp3d_sample_t *pcm, mp3dec_frame_info_t *info)

function decodeFullMp3(mp3Bytes: Uint8Array) {
    exports.mp3dec_init(dec);

    var mp3Offset = 0

    for (let n = 0; n < 1000000; n++) {
        if (mp3Offset >= mp3Bytes.byteLength) {
            break;
        }
        mem.set(mp3Bytes.subarray(mp3Offset, mp3Offset + mp3PtrSize), mp3Ptr)

        const samples = exports.mp3dec_decode_frame(dec, mp3Ptr, mp3PtrSize, pcmData, infoPtr)
        const frame_bytes = memData.getInt32(infoPtr + 0, true)
        const frame_buffer = memData.getInt32(infoPtr + 4, true)
        const channels = memData.getInt32(infoPtr + 8, true)
        const hz = memData.getInt32(infoPtr + 12, true)
        const layer = memData.getInt32(infoPtr + 16, true)
        const bitrate_kbps = memData.getInt32(infoPtr + 20, true)

        const bytesOut = samples * channels * 2

        //            const bytesData = mem.subarray(pcmData, pcmData + bytesOut)

        //    console.log(bytesData);
        //console.log({samples, frame_bytes, frame_buffer, channels, hz, layer, bitrate_kbps, bytesOut});

        mp3Offset += frame_bytes

        //console.log();
        //console.log(exports._emscripten_stack_alloc(1024));    
    }

}

for (let n = 0; n < 30; n++) {
    const start = performance.now()
    for (let m = 0; m < 50; m++) {
        decodeFullMp3(mp3Bytes);
    }
    const end = performance.now()
    console.log('decoded in ', end - start, 'ms');
}
