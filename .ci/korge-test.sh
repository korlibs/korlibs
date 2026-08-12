#!/bin/sh
# This script is used on CI (ubuntu-korge-unit-test) to patch the version string for local releasing

echo "KORGE_BRANCH: $KORGE_BRANCH"
echo "KORLIBS_FORK: $KORLIBS_FORK"
echo "IS_KORLIBS_FORK: $IS_KORLIBS_FORK"

exit 0

# Clone locally Korge repo for running linux unit tests with locally released Korlibs version
rm -rf dep-korge
mkdir dep-korge
git clone https://github.com/korlibs/korge.git dep-korge
git checkout main

# Create Git-Hash (short form)
GIT_HASH=$(git rev-parse --short HEAD)

# Patch Korlibs version SNAPSHOT with Git-Hash
sed -i "s/SNAPSHOT/$GIT_HASH/" gradle/libs.versions.toml
sed -i "s/SNAPSHOT/$GIT_HASH/" dep-korge/gradle/libs.versions.toml

echo "✓ Korlibs version updated: SNAPSHOT → $GIT_HASH"

#echo "export GIT_HASH=$GIT_HASH" >.ci/gitHashKorlibs
