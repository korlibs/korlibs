#!/bin/bash

set -euo pipefail

DRY_RUN=false
if [[ "${1:-}" == "--dry-run" ]]; then
  DRY_RUN=true
  echo "=== DRY RUN MODE - no changes will be made ==="
fi

remap_target() {
  local target="$1"
  case "$target" in
    wasm) echo "wasmJs" ;;
    jsAndWasmJs) echo "web" ;;
    *)    echo "$target" ;;
  esac
}

move() {
  local src="$1"
  local dst="$2"
  if [[ "$DRY_RUN" == true ]]; then
    echo "MOVE: $src -> $dst"
  else
    local tmp
    tmp="$(dirname "$src")/.tmp_migrate_$$"
    mv "$src" "$tmp"
    mkdir -p "$(dirname "$dst")"
    mv "$tmp" "$dst"
  fi
}

ROOT="${2:-.}"

for lib_dir in "$ROOT"/korlibs-*/; do
  [[ -d "$lib_dir" ]] || continue
  lib_dir="${lib_dir%/}"
  echo "Processing: $lib_dir"

  if [[ -d "$lib_dir/src" ]]; then
    move "$lib_dir/src" "$lib_dir/src/commonMain/kotlin"
  fi

  for d in "$lib_dir"/src@*/; do
    [[ -d "$d" ]] || continue
    d="${d%/}"; target="$(remap_target "${d##*src@}")"
    move "$d" "$lib_dir/src/${target}Main/kotlin"
  done

  if [[ -d "$lib_dir/test" ]]; then
    move "$lib_dir/test" "$lib_dir/src/commonTest/kotlin"
  fi

  for d in "$lib_dir"/test@*/; do
    [[ -d "$d" ]] || continue
    d="${d%/}"; target="$(remap_target "${d##*test@}")"
    move "$d" "$lib_dir/src/${target}Test/kotlin"
  done

  if [[ -d "$lib_dir/resources" ]]; then
    move "$lib_dir/resources" "$lib_dir/src/commonMain/resources"
  fi

  for d in "$lib_dir"/resources@*/; do
    [[ -d "$d" ]] || continue
    d="${d%/}"; target="$(remap_target "${d##*resources@}")"
    move "$d" "$lib_dir/src/${target}Main/resources"
  done

  if [[ -d "$lib_dir/testresources" ]]; then
    move "$lib_dir/testresources" "$lib_dir/src/commonTest/resources"
  fi

  for d in "$lib_dir"/testresources@*/; do
    [[ -d "$d" ]] || continue
    d="${d%/}"; target="$(remap_target "${d##*testresources@}")"
    move "$d" "$lib_dir/src/${target}Test/resources"
  done

done

echo "Done."
