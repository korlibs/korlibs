#!/usr/bin/env bash
# =============================================================================
# migrate_submodules.sh
# Migrates git submodules into the root mono-repo, preserving commit history.
#
# Usage:
#   ./migrate_submodules.sh [OPTIONS] [SUBMODULE...]
#
# Examples:
#   ./migrate_submodules.sh                         # migrates ALL submodules
#   ./migrate_submodules.sh libs/auth libs/utils    # migrates specific ones
#   ./migrate_submodules.sh --dry-run               # preview without changes
#   ./migrate_submodules.sh --no-history            # skip history rewrite
#
# Requirements:
#   - Git Bash for Windows (or any bash with git)
#   - git-filter-repo (optional, for history rewriting)
#     Install: pip install git-filter-repo
# =============================================================================

set -euo pipefail

# ── Colours (disabled automatically if not a TTY) ────────────────────────────
if [ -t 1 ]; then
  RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'
  CYAN='\033[0;36m'; BOLD='\033[1m'; RESET='\033[0m'
else
  RED=''; GREEN=''; YELLOW=''; CYAN=''; BOLD=''; RESET=''
fi

# ── Defaults ─────────────────────────────────────────────────────────────────
DRY_RUN=false
PRESERVE_HISTORY=true
BACKUP_BRANCH="backup/pre-submodule-migration"
LOG_FILE="migrate_submodules.log"
TARGET_SUBMODULES=()   # empty = migrate all

# ── Helpers ──────────────────────────────────────────────────────────────────
log()     { echo -e "${RESET}[$(date '+%H:%M:%S')] $*" | tee -a "$LOG_FILE"; }
info()    { echo -e "${CYAN}[INFO]${RESET}  $*" | tee -a "$LOG_FILE"; }
success() { echo -e "${GREEN}[OK]${RESET}    $*" | tee -a "$LOG_FILE"; }
warn()    { echo -e "${YELLOW}[WARN]${RESET}  $*" | tee -a "$LOG_FILE"; }
error()   { echo -e "${RED}[ERROR]${RESET} $*" | tee -a "$LOG_FILE"; exit 1; }
dry()     { echo -e "${YELLOW}[DRY-RUN]${RESET} $*" | tee -a "$LOG_FILE"; }

run() {
  # Runs a command; in dry-run mode just prints it
  if $DRY_RUN; then
    dry "Would run: $*"
  else
    "$@"
  fi
}

usage() {
  sed -n '/^# Usage:/,/^# =/p' "$0" | sed 's/^# \{0,3\}//'
  exit 0
}

# ── Argument parsing ──────────────────────────────────────────────────────────
while [[ $# -gt 0 ]]; do
  case "$1" in
    -h|--help)         usage ;;
    --dry-run)         DRY_RUN=true; shift ;;
    --no-history)      PRESERVE_HISTORY=false; shift ;;
    --backup-branch)   BACKUP_BRANCH="$2"; shift 2 ;;
    --log)             LOG_FILE="$2"; shift 2 ;;
    -*)                error "Unknown option: $1" ;;
    *)                 TARGET_SUBMODULES+=("$1"); shift ;;
  esac
done

# ── Sanity checks ─────────────────────────────────────────────────────────────
[[ -f ".gitmodules" ]] || error "No .gitmodules found. Run this from the root of your repo."
command -v git &>/dev/null || error "git not found in PATH."

if $PRESERVE_HISTORY && ! command -v git-filter-repo &>/dev/null; then
  warn "git-filter-repo not found. History will NOT be rewritten."
  warn "Install with: pip install git-filter-repo"
  PRESERVE_HISTORY=false
fi

# ── Collect submodules ────────────────────────────────────────────────────────
# Parse all submodule paths from .gitmodules
ALL_SUBMODULE_PATHS=()
while IFS= read -r line; do
  if [[ "$line" =~ ^[[:space:]]*path[[:space:]]*=[[:space:]]*(.+)$ ]]; then
    path="${BASH_REMATCH[1]}"
    path="${path#"${path%%[![:space:]]*}"}"   # ltrim
    path="${path%"${path##*[![:space:]]}"}"   # rtrim
    ALL_SUBMODULE_PATHS+=("$path")
  fi
done < .gitmodules

[[ ${#ALL_SUBMODULE_PATHS[@]} -gt 0 ]] || error "No submodules found in .gitmodules."

# Filter to requested submodules (or use all)
if [[ ${#TARGET_SUBMODULES[@]} -gt 0 ]]; then
  SUBMODULE_PATHS=()
  for req in "${TARGET_SUBMODULES[@]}"; do
    # Normalise slashes (Windows paths)
    req="${req//\\//}"
    found=false
    for p in "${ALL_SUBMODULE_PATHS[@]}"; do
      if [[ "$p" == "$req" ]]; then
        SUBMODULE_PATHS+=("$p")
        found=true
        break
      fi
    done
    $found || warn "Requested submodule '$req' not found in .gitmodules — skipping."
  done
else
  SUBMODULE_PATHS=("${ALL_SUBMODULE_PATHS[@]}")
fi

[[ ${#SUBMODULE_PATHS[@]} -gt 0 ]] || error "No valid submodules to migrate."

# ── Summary ───────────────────────────────────────────────────────────────────
echo ""
log "${BOLD}=== Submodule → Mono-repo Migration ===${RESET}"
info "Root repo   : $(git rev-parse --show-toplevel)"
info "Submodules  : ${SUBMODULE_PATHS[*]}"
info "Dry-run     : $DRY_RUN"
info "History     : $PRESERVE_HISTORY"
info "Log file    : $LOG_FILE"
echo ""

if ! $DRY_RUN; then
  read -r -p "$(echo -e "${YELLOW}Proceed? This will modify the repository. [y/N]: ${RESET}")" confirm
  [[ "$confirm" =~ ^[Yy]$ ]] || { info "Aborted by user."; exit 0; }
fi

# ── Create backup branch ──────────────────────────────────────────────────────
CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
info "Creating backup branch: $BACKUP_BRANCH"
run git checkout -b "$BACKUP_BRANCH" 2>/dev/null || \
  run git branch -f "$BACKUP_BRANCH" HEAD
run git checkout "$CURRENT_BRANCH"
success "Backup branch '$BACKUP_BRANCH' ready."

# ── Main migration loop ───────────────────────────────────────────────────────
MIGRATED=()
FAILED=()

migrate_submodule() {
  local submodule_path="$1"
  local submodule_path_norm="${submodule_path//\\//}"   # normalise slashes
  local submodule_name
  submodule_name=$(basename "$submodule_path_norm")

  echo ""
  log "${BOLD}── Migrating: $submodule_path_norm ──${RESET}"

  # -- Ensure the submodule is initialised & up-to-date ----------------------
  if [[ ! -d "$submodule_path_norm/.git" ]] && [[ ! -f "$submodule_path_norm/.git" ]]; then
    info "Initialising submodule..."
    run git submodule update --init --recursive -- "$submodule_path_norm"
  fi

  # -- Get submodule remote URL for history rewrite --------------------------
  local sub_url
  sub_url=$(git config --file .gitmodules "submodule.${submodule_path_norm}.url" 2>/dev/null || echo "")

  # -- Rewrite history: prefix all paths with the submodule directory --------
  if $PRESERVE_HISTORY && [[ -n "$sub_url" ]]; then
    info "Rewriting submodule history to prefix paths with '$submodule_path_norm/'..."
    local tmp_clone
    tmp_clone=$(mktemp -d)

    if ! $DRY_RUN; then
      # Clone submodule repo into a temp dir and rewrite its history
      git clone "$sub_url" "$tmp_clone" --quiet 2>>"$LOG_FILE" || {
        warn "Could not clone '$sub_url'; skipping history rewrite."
        PRESERVE_HISTORY_FOR_THIS=false
      }

      if [[ "${PRESERVE_HISTORY_FOR_THIS:-true}" != "false" ]]; then
        git -C "$tmp_clone" filter-repo \
          --to-subdirectory-filter "$submodule_path_norm" \
          --quiet 2>>"$LOG_FILE"

        # Fetch and merge the rewritten history into the root repo
        git remote add "_tmp_${submodule_name}" "$tmp_clone" 2>>"$LOG_FILE"
        git fetch "_tmp_${submodule_name}" --quiet 2>>"$LOG_FILE"
        git merge --allow-unrelated-histories \
          "_tmp_${submodule_name}/HEAD" \
          -m "chore: import history of submodule '$submodule_path_norm'" \
          --no-edit 2>>"$LOG_FILE"
        git remote remove "_tmp_${submodule_name}" 2>>"$LOG_FILE"
        rm -rf "$tmp_clone"
        success "History rewritten and merged for '$submodule_path_norm'."
      fi
    else
      dry "Would clone '$sub_url', rewrite paths, and merge history."
    fi
  fi

  # -- STEP 1: Copy files out BEFORE deinit (deinit wipes the directory) -----
  local tmp_files
  tmp_files=$(mktemp -d)
  info "Backing up submodule files to temp dir: $tmp_files"
  if ! $DRY_RUN; then
    if [[ -d "$submodule_path_norm" ]]; then
      cp -a "$submodule_path_norm/." "$tmp_files/"
      # Remove nested .git now so it isn't restored
      rm -rf "$tmp_files/.git"
    else
      error "Submodule directory '$submodule_path_norm' does not exist. Was it initialised?"
    fi
  else
    dry "Would copy '$submodule_path_norm/' → '$tmp_files/'"
  fi

  # -- STEP 2: Remove the submodule registration -----------------------------
  info "Deinitialising submodule (this empties the directory — files are safe in temp)..."
  run git submodule deinit -f -- "$submodule_path_norm"

  info "Removing submodule from index..."
  run git rm -f --cached "$submodule_path_norm" 2>/dev/null || true

  info "Removing .git/modules/$submodule_path_norm..."
  if ! $DRY_RUN; then
    local modules_dir=".git/modules/${submodule_path_norm}"
    [[ -d "$modules_dir" ]] && rm -rf "$modules_dir"
  else
    dry "Would remove .git/modules/$submodule_path_norm"
  fi

  # -- STEP 3: Remove entry from .gitmodules ---------------------------------
  info "Removing entry from .gitmodules..."
  run git config --file .gitmodules --remove-section "submodule.${submodule_path_norm}" 2>/dev/null || true

  # -- STEP 4: Restore files from temp copy into now-empty directory ---------
  info "Restoring files from temp backup into '$submodule_path_norm/'..."
  if ! $DRY_RUN; then
    mkdir -p "$submodule_path_norm"
    cp -a "$tmp_files/." "$submodule_path_norm/"
    rm -rf "$tmp_files"
    success "Files restored to '$submodule_path_norm/'."
  else
    dry "Would restore files from '$tmp_files/' → '$submodule_path_norm/'"
  fi

  # -- Stage the restored directory ------------------------------------------
  info "Staging '$submodule_path_norm' as regular source..."
  run git add "$submodule_path_norm" 2>/dev/null || true
  run git add .gitmodules

  success "Submodule '$submodule_path_norm' migrated."
  MIGRATED+=("$submodule_path_norm")
}

for submodule in "${SUBMODULE_PATHS[@]}"; do
  if migrate_submodule "$submodule"; then
    : # success tracked inside function
  else
    warn "Migration of '$submodule' encountered errors."
    FAILED+=("$submodule")
  fi
done

# ── Commit results ────────────────────────────────────────────────────────────
if [[ ${#MIGRATED[@]} -gt 0 ]]; then
  # Remove .gitmodules if it's now empty
  if ! $DRY_RUN; then
    remaining=$(git config --file .gitmodules --list 2>/dev/null | wc -l)
    if [[ "$remaining" -eq 0 ]]; then
      info ".gitmodules is now empty — removing it."
      git rm -f .gitmodules 2>/dev/null || rm -f .gitmodules
    fi
  fi

  commit_msg="chore: migrate submodule(s) to mono-repo

Migrated submodules:
$(printf '  - %s\n' "${MIGRATED[@]}")

Generated by migrate_submodules.sh
Backup branch: $BACKUP_BRANCH"

  info "Committing migration..."
  run git add -A
  run git commit -m "$commit_msg" || warn "Nothing new to commit (may already be staged)."
fi

# ── Final report ──────────────────────────────────────────────────────────────
echo ""
log "${BOLD}=== Migration Complete ===${RESET}"
if [[ ${#MIGRATED[@]} -gt 0 ]]; then
  success "Migrated (${#MIGRATED[@]}): ${MIGRATED[*]}"
fi
if [[ ${#FAILED[@]} -gt 0 ]]; then
  warn "Failed    (${#FAILED[@]}): ${FAILED[*]}"
fi
info "Backup branch : $BACKUP_BRANCH"
info "Log file      : $LOG_FILE"
echo ""
info "Next steps:"
info "  1. Run your build/test suite to verify everything works."
info "  2. Update CI configs, package.json workspaces, tsconfig paths, etc."
info "  3. Push: git push origin $CURRENT_BRANCH"
info "  4. Delete the backup branch when satisfied: git branch -D $BACKUP_BRANCH"
