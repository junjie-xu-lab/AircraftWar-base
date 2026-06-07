# How to Play / 游戏玩法说明

This guide explains the basic rules, controls, props, difficulty modes, and leaderboard behavior of Aircraft War.

本文档说明飞机大战的基本规则、操作方式、道具效果、难度机制和排行榜机制。

## Quick Start / 快速开始

Run the game from the project root:

在项目根目录运行：

```sh
java RunAircraftWar.java
```

The game only requires JDK 11 or newer. No Maven, Gradle, IntelliJ, or third-party library is needed.

游戏只需要 JDK 11 或以上版本，不需要 Maven、Gradle、IntelliJ 或其他第三方依赖。

## Main Menu / 主菜单

When the game starts, choose a language first:

游戏启动后，先选择界面语言：

- 中文版本
- English Version

Then choose one difficulty mode:

然后选择一个难度：

- Easy / 简单模式: slower pace, suitable for learning the rules.
- Normal / 普通模式: balanced enemy spawn rate and difficulty growth.
- Hard / 困难模式: faster enemy spawn and stronger pressure.

## Controls / 操作方式

Move the mouse to control the hero aircraft.

移动鼠标控制英雄飞机。

- The hero aircraft follows the mouse position.
- Avoid enemy aircraft and enemy bullets.
- Pick up props dropped by elite enemies and bosses.
- The game ends when the hero aircraft's HP reaches 0.

- 英雄飞机会跟随鼠标移动。
- 避开敌机和敌方子弹。
- 拾取精英敌机和 Boss 掉落的道具。
- 英雄飞机生命值为 0 时游戏结束。

## Scoring / 得分规则

Destroy enemies to gain score.

击毁敌机可以获得分数。

- Mob enemy: basic enemy score.
- Elite enemies: higher score and may drop props.
- Boss enemy: appears after reaching score thresholds and gives a higher reward.
- Bomb prop: clears non-boss enemies and enemy bullets, and gives bonus score for cleared enemies.

- 普通敌机：基础得分。
- 精英敌机：得分更高，并有概率掉落道具。
- Boss 敌机：达到一定分数后出现，击败后奖励更高。
- 炸弹道具：清除非 Boss 敌机和敌方子弹，并根据清除的敌机获得额外分数。

## Props / 道具说明

Props fall downward after being dropped. Move the hero aircraft onto a prop to activate it.

道具掉落后会向下移动，控制英雄飞机接触道具即可触发效果。

| Prop | Effect |
| --- | --- |
| Blood / 加血 | Restores hero HP. |
| Bomb / 炸弹 | Clears non-boss enemies and enemy bullets. |
| Freeze / 冰冻 | Temporarily slows or freezes enemies and enemy bullets. |
| Bullet / 火力 | Temporarily changes the hero's shooting pattern. |
| Bullet Plus / 强化火力 | Temporarily enables a stronger shooting pattern. |

## Boss Fights / Boss 战

In Normal and Hard modes, the boss appears when your score reaches certain thresholds. Boss enemies have more HP and use stronger bullet patterns.

在普通模式和困难模式中，分数达到一定阈值后会出现 Boss。Boss 生命值更高，并会发射更强的弹幕。

During a boss fight:

Boss 战期间：

- Background music switches to boss music.
- The boss remains on screen until defeated or the game ends.
- Bomb props clear enemy bullets, but they do not instantly remove the boss.

- 背景音乐会切换为 Boss 音乐。
- Boss 会持续存在，直到被击败或游戏结束。
- 炸弹道具会清除敌方子弹，但不会直接消灭 Boss。

## Leaderboard / 排行榜

After the game ends, you can enter your name to save the score.

游戏结束后，可以输入名字保存分数。

- Scores are saved locally.
- Each difficulty mode has its own leaderboard.
- Runtime score files are named `scores_difficulty_*.tsv`.
- These score files are ignored by Git, so your personal scores will not be uploaded to GitHub.

- 分数保存在本地。
- 每个难度都有独立排行榜。
- 运行时生成的排行榜文件名为 `scores_difficulty_*.tsv`。
- 这些文件已被 Git 忽略，因此你的个人分数不会上传到 GitHub。

## Tips / 游戏建议

- Stay near the lower part of the screen to leave more reaction time.
- Pick up blood props when HP is low.
- Save bomb props for crowded enemy waves or dense bullet patterns.
- In boss fights, focus on dodging first and attacking second.

- 尽量待在屏幕下方，给自己更多反应时间。
- 血量较低时优先拾取加血道具。
- 敌机或子弹较密集时，炸弹道具更有价值。
- Boss 战中优先躲避，再寻找输出机会。
