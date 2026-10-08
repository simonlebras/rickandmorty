# Rick & Morty
Android application using the REST API from https://rickandmortyapi.com

## Building

The project is built with the [Kotlin Toolchain](https://kotlin-toolchain.org). The `kotlin` wrapper
downloads everything it needs (JDK, Android SDK) on first use.

```bash
./kotlin build                 # build all modules (iOS targets need Xcode)
./kotlin test --platform=jvm   # run the tests
./kotlin package --module=app  # build the signed release bundle
```

Debug-only code (debug drawer, StrictMode, debug logging) is gated by `BuildFlags.isDebug` and
removed from release builds by R8, see `app/proguard-rules.pro`.

## Checks

```bash
./kotlin check badging licenses --module=app
```

* `badging` compares `aapt2 dump badging` of the release bundle against `app/release-badging.txt`.
  Run `./kotlin package --module=app` first, and `./kotlin do updateBadging --module=app` to accept
  changes.
* `licenses` verifies that `app/resources/aboutlibraries.json` matches the runtime dependencies and
  that they only use allowed licenses. Run `./kotlin do updateLicenses --module=app` after changing
  dependencies.

Both checks are local plugins in `plugins/`.
