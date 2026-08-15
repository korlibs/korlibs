# Contributing changes that affect both Korlibs and Korge

[Korlibs](https://github.com/korlibs/korlibs) is the foundation library set used by
[Korge](https://github.com/korlibs/korge).
To make sure a change in Korlibs does not silently break Korge, every pull request in this
repository runs the **`korge-test`** CI job (see `.github/workflows/checks.yml`). This job:

1. Builds your Korlibs PR and publishes it to the local Maven repository of the CI runner
   (`gradlew publishToMavenLocal`), with the version `SNAPSHOT` replaced by the short Git
   `hash` of your PR commit. This ensures that Korge is built against the exact version of
   Korlibs that your PR introduces and in any case does not fall back to the `SNAPSHOT`
   version from Maven Central.
2. Clones the Korge repository next to it (into `deps-korge` sub-folder).
3. Patches Korge's `gradle/libs.versions.toml` to use exactly that locally published
   Korlibs version.
4. Builds Korge and runs its unit tests against your Korlibs changes.

Most of the time this "just works": your Korlibs change is backwards compatible, Korge
compiles and its tests pass, and you don't need to do anything special.

This document describes what to do in the **other** case: your Korlibs PR intentionally
changes behavior or APIs, and the Korge build or its unit tests break because of it.

---

## The problem: a chicken-and-egg situation

If your Korlibs change requires a matching change in Korge (an API rename, changed
behavior, updated test expectations, …), you end up in a deadlock:

* Your **Korlibs PR** cannot be merged, because the `korge-test` job fails — Korge's code
  or tests are still written against the *old* Korlibs behavior.
* A **Korge PR** with the fix cannot be merged (or even fully tested) either, because the
  new Korlibs version containing your change has not been released yet.

To break this cycle, the `korge-test` job supports testing your Korlibs PR against a
**matching branch in your own fork of Korge** instead of the `main` branch in the base korge
repo (`korlibs/korge`).

## How the CI picks the Korge sources

When the `korge-test` job runs for your pull request, it decides where to get the Korge
sources from in two checkout steps:

1. **Attempt checkout of the companion branch.**
   The job tries to check out a branch with the *same name as your PR branch* from a Korge
   repository owned by the *same GitHub account* that owns the Korlibs fork, i.e.
   `https://github.com/<your-user>/korge`. This step is allowed to fail
   (`continue-on-error`), so a red ❌ on it is expected and harmless when no companion
   branch exists.
2. **Fallback.**
   If that repository does not exist (or is not accessible), or the branch is not found,
   the job checks out the `main` branch of `korlibs/korge` instead.

In short: **fork + identical branch name = your Korge fixes are picked up automatically.**
There is no extra configuration, no label, and no magic comment needed — the matching is
done purely by GitHub user name and branch name.

## Step-by-step: contributing a feature that needs changes in both repos

Let's assume your GitHub user is `your-user` and your feature branch is called
`feat/my-feature`.

### Step 1 — Create your Korlibs PR as usual

Fork `korlibs/korlibs`, create your feature branch, and open the pull request:

```bash
git clone https://github.com/your-user/korlibs.git
cd korlibs
git checkout -b feat/my-feature
# ... implement your change ...
git push origin feat/my-feature
```

**Why:** This is the normal contribution flow. When the PR is opened, the CI runs the
`korge-test` job. Watch its result: if it passes, you are done and none of the following
steps are needed.

### Step 2 — Check *why* `korge-test` fails

If the job fails, open the CI logs and look at the Korge build/test output. Confirm the
failure is really caused by your change (compile errors against the new API, test
assertions that reflect the old behavior, …) and not by an unrelated flaky test.

**Why:** You should only continue with the steps below if Korge genuinely needs to be
adapted to your change. If the failure is unrelated, fixing it in your Korge branch would
hide a real problem and make the review harder.

### Step 3 — Fork the Korge repository

Fork `korlibs/korge` to your own account, so that
`https://github.com/your-user/korge` exists.

**Why:** The CI only looks for the companion branch in a fork owned by the **same account**
as the Korlibs fork. Contributors don't have push access to `korlibs/korge`, so your own
fork is the place where the CI can find your Korge fixes.

> Note: If your Korlibs PR comes directly from a branch in `korlibs/korlibs` (i.e. you are
> a maintainer, not working from a fork), the CI looks for the branch in `korlibs/korge`
> instead — push your companion branch there.

### Step 4 — Create a branch in your Korge fork with the *exact same name*

```bash
git clone https://github.com/your-user/korge.git
cd korge
git checkout -b feat/my-feature   # must match the Korlibs branch name exactly!
```

**Why:** The branch name is the only link between your two PRs. The CI takes the head
branch name of your Korlibs PR (`feat/my-feature`) and checks whether a branch with
exactly that name exists in your Korge fork. Case, prefixes and slashes all matter —
`feat/my-feature` and `feat/my_feature` are different branches. If the names don't match,
the CI silently falls back to `korlibs/korge` `main` and your fixes are ignored.

### Step 5 — Fix Korge on that branch

Adapt Korge to your Korlibs change: update call sites to the new API, adjust unit tests to
the new expected behavior, etc. Then push the branch:

```bash
# ... fix code / tests ...
git push origin feat/my-feature
```

You can verify your fixes locally before pushing:

```bash
# In your local korlibs checkout: publish your Korlibs build locally
cd ../korlibs
./gradlew publishToMavenLocal

# Point your local Korge checkout at that version
# (edit korge/gradle/libs.versions.toml to reference the published Korlibs version),
# then run the Korge tests:
cd ../korge
./gradlew check
```

**Why:** This mirrors exactly what the CI does (publish Korlibs locally → build Korge
against it → run tests), so a green local run is a strong signal the CI will pass too.
Keep the Korge branch focused: it should contain *only* the changes required to make Korge
work with your Korlibs PR, nothing else.

### Step 6 — Re-run the Korlibs CI

Trigger a new CI run on your Korlibs PR — either by pushing a new commit (an amended or
empty commit is fine) or by using "Re-run jobs" in the GitHub Actions UI:

```bash
cd ../korlibs
git commit --allow-empty -m "ci: re-run with korge companion branch"
git push origin feat/my-feature
```

In the `korge-test` job, check the step **"Report which Korge sources are used"**. It prints
which sources were picked up:

```
-- ✓ Using branch 'feat/my-feature' from 'your-user/korge'.
```

confirming that your Korge branch was picked up. If instead you see the fallback message
(`-- ✓ Using 'main' branch from 'korlibs/korge'.`), the step
*"Attempt checkout of feature branch in fork's Korge repo"* failed — double-check the fork
owner and the branch name spelling in its log.

**Why:** The Korge branch is resolved at CI run time. The run that failed before your
Korge fixes existed will not update itself — a fresh run is needed.

### Step 7 — Open a PR in Korge and cross-link both PRs

Once the Korlibs CI is green:

1. Open a pull request in `korlibs/korge` from `your-user:feat/my-feature` targeting
   `main`.
2. In both PR descriptions, link to the other PR (e.g. *"Companion PR:
   korlibs/korge#123"* / *"Requires korlibs/korlibs#456"*).

**Why:** Your Korge fixes must eventually land in `korlibs/korge` — the CI trick only
proves compatibility, it doesn't merge anything into Korge. Cross-linking tells the
maintainers that the two PRs belong together and in which order they need to be handled.

### Step 8 — Merge order (done by the maintainers)

The usual integration order is:

1. The **Korlibs PR** is merged first (its CI is green thanks to your Korge branch).
2. A new Korlibs snapshot/release containing your change is published.
3. The **Korge PR** is updated to that Korlibs version (if needed) and merged.

**Why:** The Korge PR can only pass its own CI once a Korge-consumable Korlibs version
containing your change exists. Until then it stays open as a companion PR.

## Quick checklist

- [ ] Korlibs PR opened; `korge-test` fails *because of* my change
- [ ] Forked `korlibs/korge` under the same GitHub account as my Korlibs fork
- [ ] Created a branch in the Korge fork with the **exact same name** as my Korlibs PR branch
- [ ] Fixed Korge code/tests on that branch and pushed it
- [ ] Re-ran the Korlibs CI and verified the log picks up my Korge branch
- [ ] Opened the companion Korge PR and cross-linked both PRs

## Troubleshooting

| Symptom                                                                                            | Likely cause / fix |
|----------------------------------------------------------------------------------------------------| --- |
| Step "Report which Korge sources are used" prints `-- ✓ Using 'main' branch from 'korlibs/korge'.` | Your account has no (public/accessible) `korge` fork, the branch name doesn't match, or the branch was not pushed. Check the log of the failed *"Attempt checkout ..."* step, then see Steps 3–5. |
| Step "Attempt checkout of feature branch in fork's Korge repo" shows a red ❌                       | Expected whenever no matching companion branch exists — the job continues with the fallback. Only relevant if you *did* create a companion branch: then check fork owner and branch name spelling. |
| Korge tests still fail with your branch checked out                                                | Your Korge fixes are incomplete, or the failure is unrelated. Reproduce locally with `publishToMavenLocal` (Step 5). |
| Korge build can't resolve the Korlibs version                                                      | The CI patches `SNAPSHOT` in both `libs.versions.toml` files with the PR's git hash. Make sure your Korge branch doesn't pin a different, hardcoded Korlibs version. |
