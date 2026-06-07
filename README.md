# Aircraft War

A Java Swing aircraft shooting game with bilingual UI, three difficulty modes, props, boss fights, sound effects, and a local leaderboard.

## Quick Start

You only need JDK 11 or newer. No Maven, Gradle, IntelliJ GUI Designer, or third-party library is required to play.

### Windows

Double-click `run.bat`, or run:

```bat
run.bat
```

### macOS / Linux

Run:

```sh
sh run.sh
```

The scripts compile the source code into `build/classes` and start `edu.hitsz.application.Main`.

## Controls

Drag the mouse to move the hero aircraft. Avoid enemy bullets and collect props.

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
- Observer: bomb and freeze props notify enemies and bullets
- DAO: leaderboard persistence through `ScoreDAO`

## Project Structure

```text
src/
  edu/hitsz/application/   main UI, game loop, resource and audio managers
  edu/hitsz/aircraft/      hero, enemies, and enemy factories
  edu/hitsz/bullet/        hero and enemy bullets
  edu/hitsz/prop/          collectible props
  edu/hitsz/strategy/      shooting strategies
  edu/hitsz/dao/           leaderboard storage
  images/                  game images
  videos/                  sound files
test/                      optional JUnit tests
uml/                       design diagrams
```

## Notes

Runtime score files are generated as `scores_difficulty_*.tsv` and are ignored by Git.
