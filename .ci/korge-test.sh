#!/bin/sh

# This script is used on CI (korge-test job)
#
# (1) Check if the PR is done from a fork or from the base branch
# (2) Check out the korge repo from the fork owner if it exist. This makes it possible to prepare
#     a branch in korge repo which fixes/updates unit tests in korge if that is needed
# (3) Check out the feature branch name in the korge repo if it exists otherwise use "main"

# If envnironment variables are not set, set them to default values for local testing
if [ -z "$PR_BRANCH" ]; then PR_BRANCH="feat/text-alignment-fixes"; fi
if [ -z "$FORK_OWNER" ]; then FORK_OWNER="jobe-m"; fi
if [ -z "$IS_FORK" ]; then IS_FORK="true"; fi

echo "-- PR_BRANCH: $PR_BRANCH"
echo "-- FORK_OWNER: $FORK_OWNER"
echo "-- IS_FORK: $IS_FORK"

# Determine which Korge repo to clone: the fork owner's repo for fork PRs, otherwise the base repo
if [ "$IS_FORK" == "true" ]; then
  KORGE_REPO="https://github.com/$FORK_OWNER/korge.git"
else
  KORGE_REPO="https://github.com/korlibs/korge.git"
fi
echo "KORGE_REPO: $KORGE_REPO"

# Clone locally Korge repo for running linux unit tests with locally released Korlibs version
rm -rf deps-korge

# Check if the KORGE_REPO is accessible before cloning
if ! git ls-remote "$KORGE_REPO" >/dev/null 2>&1; then
  echo "-- No fork available for Korge repo, using 'korlibs/korge' repo."
  KORGE_REPO="https://github.com/korlibs/korge.git"
  IS_FORK="false"
fi

git clone "$KORGE_REPO" deps-korge

# Checkout the PR branch if it exists in that repo, otherwise stay on the default branch (main)
if [ -n "$PR_BRANCH" ] && git -C deps-korge ls-remote --exit-code --heads origin "$PR_BRANCH" >/dev/null 2>&1; then
  git -C deps-korge checkout "$PR_BRANCH"
  if [ "$IS_FORK" == "true" ]; then
    echo "-- Check out branch '$PR_BRANCH' from '$FORK_OWNER/korge'."
  else
    echo "-- Check out branch '$PR_BRANCH' from 'korlibs/korge'."
  fi
else
  # If the PR branch is not found in a fork, we need to clone the base repo and check out 'main' branch there
  if [ "$IS_FORK" == "true" ]; then
    echo "-- Cloning base repo 'korlibs/korge' to check out 'main' branch."
    rm -rf deps-korge
    git clone https://github.com/korlibs/korge.git deps-korge
    git -C deps-korge checkout main
    echo "-- Branch '$PR_BRANCH' not found in '$FORK_OWNER/korge', using 'main' branch from 'korlibs/korge' instead."
  else
    echo "-- Branch '$PR_BRANCH' not found in 'korlibs/korge', using 'main' branch instead."
  fi

  git -C deps-korge checkout main
fi

# Create Git-Hash (short form)
GIT_HASH=$(git rev-parse --short HEAD)

# Patch Korlibs version SNAPSHOT with Git-Hash
sed -i "s/SNAPSHOT/$GIT_HASH/" gradle/libs.versions.toml
sed -i "s/SNAPSHOT/$GIT_HASH/" deps-korge/gradle/libs.versions.toml

echo "-- ✓ Korlibs version updated: SNAPSHOT → $GIT_HASH"
