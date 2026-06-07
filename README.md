# Aircraft War

A Java Swing aircraft shooting game with bilingual UI, three difficulty modes, props, boss fights, sound effects, and a local leaderboard.

## Quick Start

You only need JDK 11 or newer. No Maven, Gradle, IntelliJ GUI Designer, or third-party library is required to play.

### Recommended

Run this command from the project root:

```sh
java RunAircraftWar.java
```

This compiles the source code into `build/classes` and starts `aircraftwar.application.Main`.

### Optional Scripts

Windows may block downloaded `.bat` files through Smart App Control. If your system allows scripts, you can also run:

```bat
run.bat
```

On macOS / Linux, you can run:

```sh
sh run.sh
```

## Controls

Drag the mouse to move the hero aircraft. Avoid enemy bullets and collect props.

For detailed rules and gameplay tips, see [HOW_TO_PLAY.md](HOW_TO_PLAY.md).

## Features

- Chinese and English UI selectable from the start screen
- Easy, Normal, and Hard difficulty modes
- Multiple enemy types with different movement and shooting behavior
- Boss enemies with radial bullet patterns
- Blood, bomb, freeze, spread-shot, and circle-shot props
- Background music, boss music, and sound effects
- Local leaderboard by difficulty

## Design Highlights

- Template Method: shared game loop in `GamePanel`, difficulty-specific behavior in subclasses
- Factory Method: enemy creation through enemy factories
- Simple Factory: prop creation through `PropFactory`
- Strategy: hero bullet patterns through shooting strategies
- Observer: freeze props notify enemies and bullets
- DAO: leaderboard persistence through `ScoreDAO`

## Project Structure

```text
src/
  aircraftwar/application/ main UI, game loop, resource and audio managers
  aircraftwar/aircraft/    hero, enemies, and enemy factories
  aircraftwar/bullet/      hero and enemy bullets
  aircraftwar/prop/        collectible props
  aircraftwar/strategy/    shooting strategies
  aircraftwar/dao/         leaderboard storage
  images/                  game images
  videos/                  sound files
uml/                       design diagrams
```

## Notes

Runtime score files are generated as `scores_difficulty_*.tsv` and are ignored by Git.
