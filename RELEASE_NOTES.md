# Release Notes

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
