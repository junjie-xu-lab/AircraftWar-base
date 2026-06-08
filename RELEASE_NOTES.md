# Release Notes

## v2.0.0

Second public release focused on easier access for non-technical players.

### Highlights

- Added a Windows release package with `AircraftWar.exe`.
- Bundled a Java runtime in the Windows package, so players do not need to install Java.
- Added a packaging script based on the official JDK `jpackage` tool.
- Updated README instructions for players and developers.

### Player Download

Download from GitHub Releases:

```text
AircraftWar-v2.0.0-windows.zip
```

Unzip it and double-click:

```text
AircraftWar.exe
```

### Developer Packaging Command

```powershell
powershell -ExecutionPolicy Bypass -File tools/package-windows.ps1 -Version 2.0.0
```

## v1.0.0

Initial public release of Aircraft War.

### Highlights

- Java Swing aircraft shooting gameplay.
- Chinese and English UI selectable from the start screen.
- Easy, Normal, and Hard difficulty modes.
- Multiple enemy types, boss fights, props, and sound effects.
- Local leaderboard stored by difficulty.
- No Maven, Gradle, IntelliJ GUI Designer, or third-party runtime dependency required.

### How to Run

Run from the project root:

```sh
java RunAircraftWar.java
```

JDK 11 or newer is required.

### Notes

- Runtime leaderboard files are generated locally as `scores_difficulty_*.tsv`.
- Score files, build output, IDE files, and compiled `.class` files are ignored by Git.
