#!/bin/sh
# This script is used on CI (ubuntu-korge-unit-test) to patch the version string for local releasing

echo "PR_BRANCH: $PR_BRANCH"
echo "FORK_OWNER: $FORK_OWNER"
echo "IS_FORK: $IS_FORK"

# Determine which Korge repo to clone: the fork owner's repo for fork PRs, otherwise the base repo
if [ "$IS_FORK" == "true" ]; then
  KORGE_REPO="https://github.com/$FORK_OWNER/korge.git"
else
  KORGE_REPO="https://github.com/korlibs/korge.git"
fi
echo "KORGE_REPO: $KORGE_REPO"

exit 0

# Clone locally Korge repo for running linux unit tests with locally released Korlibs version
rm -rf dep-korge
git clone "$KORGE_REPO" dep-korge

# Checkout the PR branch if it exists in that repo, otherwise stay on the default branch (main)
if [ -n "$PR_BRANCH" ] && git -C dep-korge ls-remote --exit-code --heads origin "$PR_BRANCH" >/dev/null 2>&1; then
  git -C dep-korge checkout "$PR_BRANCH"
else
  echo "Branch '$PR_BRANCH' not found in $KORGE_REPO, using default branch"
fi

# Create Git-Hash (short form)
GIT_HASH=$(git rev-parse --short HEAD)

# Patch Korlibs version SNAPSHOT with Git-Hash
sed -i "s/SNAPSHOT/$GIT_HASH/" gradle/libs.versions.toml
sed -i "s/SNAPSHOT/$GIT_HASH/" dep-korge/gradle/libs.versions.toml

echo "✓ Korlibs version updated: SNAPSHOT → $GIT_HASH"

#echo "export GIT_HASH=$GIT_HASH" >.ci/gitHashKorlibs
