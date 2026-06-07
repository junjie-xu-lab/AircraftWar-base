package aircraftwar.application;

import aircraftwar.aircraft.*;
import aircraftwar.bullet.BaseBullet;
import aircraftwar.basic.AbstractFlyingObject;
import aircraftwar.prop.IProp;
import aircraftwar.prop.AbstractProp;
import aircraftwar.prop.BombProp;
import aircraftwar.prop.IPropObserver;
import aircraftwar.prop.PropFactory;
import aircraftwar.dao.ScoreDAOImpl;
import aircraftwar.dao.ScoreRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 * Base panel for all difficulty modes.
 */
public abstract class GamePanel extends JPanel {

    protected int backGroundTop = 0;
    protected String backgroundImage = "images/bg.jpg";
    private BufferedImage cachedBackgroundImage;
    protected int difficulty = 1;

    private final Timer timer;
    private final int timeInterval = 40;

    protected HeroAircraft heroAircraft;
    protected List<AbstractAircraft> enemyAircrafts;
    protected List<BaseBullet> heroBullets;
    protected List<BaseBullet> enemyBullets;
    protected List<IProp> props;

    protected int enemyMaxNumber = 5;

    protected double enemySpawnCycle = 20;
    protected int enemySpawnCounter = 0;

    protected double heroShootCycle = 20;
    protected int heroShootCounter = 0;

    protected double enemyShootCycle = 20;
    protected int enemyShootCounter = 0;

    protected int score = 0;

    protected boolean gameOverFlag = false;

    protected boolean bossSpawned = false;

    protected final IEnemyFactory[] enemyFactories = {
            new MobEnemyFactory(),
            new EliteEnemyFactory(),
            new ElitePlusEnemyFactory(),
            new EliteProEnemyFactory()
    };

    protected ScoreDAOImpl scoreDAO;

    protected GamePanel() {
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        this.timer = new Timer(timeInterval, e -> {
            gameLoop();
            repaint();
        });
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        this.scoreDAO = new ScoreDAOImpl(difficulty);
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
        this.cachedBackgroundImage = ImageManager.getBackgroundImage(backgroundImage);
    }

    public void startGame() {
        resetGame();
        initializeSettings();

        AudioManager.getInstance().playBackgroundMusic();
        timer.start();
    }

    protected void gameLoop() {
        enemySpawnCounter++;
        if (enemySpawnCounter >= enemySpawnCycle) {
            enemySpawnCounter = 0;
            if (enemyAircrafts.size() < enemyMaxNumber) {
                spawnEnemy();
            }
        }

        shootAction();
        bulletsMoveAction();
        aircraftsMoveAction();
        crashCheckAction();
        postProcessAction();
        applyDifficultyProgression();
        checkBossSpawn();
        checkResultAction();
    }

    protected abstract void initializeSettings();

    protected abstract void spawnEnemy();

    protected abstract void checkBossSpawn();

    protected abstract void applyDifficultyProgression();

    private void resetGame() {
        timer.stop();
        HeroAircraft.resetInstance();
        heroAircraft = HeroAircraft.getInstance(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                0, 0, 100);
        new HeroController(this, heroAircraft);

        enemyAircrafts.clear();
        heroBullets.clear();
        enemyBullets.clear();
        props.clear();
        score = 0;
        gameOverFlag = false;
        bossSpawned = false;
        enemySpawnCounter = 0;
        heroShootCounter = 0;
        enemyShootCounter = 0;
        backGroundTop = 0;
        if (cachedBackgroundImage == null) {
            cachedBackgroundImage = ImageManager.getBackgroundImage(backgroundImage);
        }
    }

    private void shootAction() {
        heroShootCounter++;
        if (heroShootCounter >= heroShootCycle) {
            heroShootCounter = 0;
            heroBullets.addAll(heroAircraft.shoot());
        }

        enemyShootCounter++;
        if (enemyShootCounter >= enemyShootCycle) {
            enemyShootCounter = 0;
            for (AbstractAircraft enemy : enemyAircrafts) {
                if (enemy instanceof EliteEnemy || enemy instanceof ElitePlusEnemy ||
                        enemy instanceof EliteProEnemy || enemy instanceof BossEnemy) {
                    enemyBullets.addAll(enemy.shoot());
                }
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void crashCheckAction() {
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();

                    AudioManager.getInstance().playBulletHit();

                    if (enemyAircraft.notValid()) {
                        score += 10;
                        if (enemyAircraft instanceof ElitePlusEnemy) {
                            propDropAction(enemyAircraft.getLocationX(), enemyAircraft.getLocationY(), false, false);
                        } else if (enemyAircraft instanceof EliteProEnemy) {
                            propDropAction(enemyAircraft.getLocationX(), enemyAircraft.getLocationY(), true, false);
                        } else if (enemyAircraft instanceof BossEnemy) {
                            propDropAction(enemyAircraft.getLocationX(), enemyAircraft.getLocationY(), true, true);

                            AudioManager.getInstance().stopBossMusic();
                        } else if (enemyAircraft instanceof EliteEnemy) {
                            propDropAction(enemyAircraft.getLocationX(), enemyAircraft.getLocationY(), false, false);
                        }
                    }
                }
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        for (IProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash((AbstractFlyingObject) prop)) {
                if (prop instanceof BombProp) {
                    activateBombProp((BombProp) prop);
                } else {
                    registerPropObservers(prop);
                    prop.activate(heroAircraft);
                }

                prop.vanish();

                AudioManager.getInstance().playGetSupply();
            }
        }
    }

    private void propDropAction(int x, int y, boolean canDropFreeze, boolean isBoss) {
        if (isBoss) {
            for (int i = 0; i < 3; i++) {
                double rand = Math.random();
                IProp prop;
                if (rand < 0.25) {
                    prop = PropFactory.createProp("blood", x + (int)(Math.random() * 40 - 20), y);
                } else if (rand < 0.5) {
                    prop = PropFactory.createProp("bullet", x + (int)(Math.random() * 40 - 20), y);
                } else if (rand < 0.75) {
                    prop = PropFactory.createProp("bulletPlus", x + (int)(Math.random() * 40 - 20), y);
                } else if (rand < 0.9) {
                    prop = PropFactory.createProp("freeze", x + (int)(Math.random() * 40 - 20), y);
                } else {
                    prop = PropFactory.createProp("bomb", x + (int)(Math.random() * 40 - 20), y);
                }

                props.add(prop);
            }
        } else {
            if (Math.random() < 0.6) {
                IProp prop;
                double rand = Math.random();

                if (canDropFreeze) {
                    if (rand < 0.2) {
                        prop = PropFactory.createProp("blood", x, y);
                    } else if (rand < 0.4) {
                        prop = PropFactory.createProp("bullet", x, y);
                    } else if (rand < 0.6) {
                        prop = PropFactory.createProp("bulletPlus", x, y);
                    } else if (rand < 0.8) {
                        prop = PropFactory.createProp("freeze", x, y);
                    } else {
                        prop = PropFactory.createProp("bomb", x, y);
                    }
                } else {
                    if (rand < 0.25) {
                        prop = PropFactory.createProp("blood", x, y);
                    } else if (rand < 0.5) {
                        prop = PropFactory.createProp("bullet", x, y);
                    } else if (rand < 0.75) {
                        prop = PropFactory.createProp("bulletPlus", x, y);
                    } else {
                        prop = PropFactory.createProp("bomb", x, y);
                    }
                }

                props.add(prop);
            }
        }
    }

    private void activateBombProp(BombProp bombProp) {
        bombProp.activate(heroAircraft);

        int bombScore = 0;
        for (AbstractAircraft enemy : enemyAircrafts) {
            if (enemy.notValid() || enemy instanceof BossEnemy) {
                continue;
            }
            enemy.vanish();
            bombScore += 10;
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.vanish();
        }

        score += bombScore;
        System.out.println("Bomb cleared enemies and bullets. Bonus score: " + bombScore);
    }

    private void registerPropObservers(IProp prop) {
        if (prop instanceof AbstractProp) {
            AbstractProp abstractProp = (AbstractProp) prop;

            for (AbstractAircraft enemy : enemyAircrafts) {
                if (enemy instanceof IPropObserver) {
                    abstractProp.registerObserver((IPropObserver) enemy);
                }
            }

            for (BaseBullet bullet : enemyBullets) {
                if (bullet instanceof IPropObserver) {
                    abstractProp.registerObserver((IPropObserver) bullet);
                }
            }
        }
    }


    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(IProp::notValid);
    }

    private void checkResultAction() {
        if (heroAircraft.getHp() <= 0) {
            timer.stop();
            gameOverFlag = true;
            System.out.println("Game over.");

            AudioManager.getInstance().playGameOver();

            saveAndShowLeaderboard();
        }
    }

    private void saveAndShowLeaderboard() {
        JTextField nameField = new JTextField(18);
        JPanel inputPanel = new JPanel(new BorderLayout(0, 8));
        inputPanel.add(new JLabel("<html>" + I18n.format("savePrompt", score).replace("\n", "<br>") + "</html>"),
                BorderLayout.NORTH);
        inputPanel.add(nameField, BorderLayout.CENTER);

        int result = JOptionPane.showOptionDialog(
                this,
                inputPanel,
                I18n.text("saveTitle"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{I18n.text("ok"), I18n.text("cancel")},
                I18n.text("ok")
        );

        String playerName = result == JOptionPane.OK_OPTION ? nameField.getText() : null;
        if (playerName != null && !playerName.trim().isEmpty()) {
            ScoreRecord record = new ScoreRecord(playerName.trim(), score, difficulty);
            scoreDAO.add(record);
            System.out.println("Score saved for " + playerName + ": " + score);
        } else {
            System.out.println("Score save skipped.");
        }

        showLeaderboardUI();
    }

    private void showLeaderboardUI() {
        LeaderboardUI leaderboardUI = new LeaderboardUI(
                difficulty,
                () -> {
                    GamePanel game = createGame(difficulty);
                    game.setBackgroundImage(backgroundImage);
                    CardLayoutManager.getInstance().showPanel(game);
                    game.startGame();
                },
                () -> CardLayoutManager.getInstance().showPanel(new DifficultyMenu().getMainPanel())
        );
        CardLayoutManager.getInstance().showPanel(leaderboardUI.getMainPanel());
    }

    private GamePanel createGame(int difficulty) {
        switch (difficulty) {
            case 1:
                return new EasyGame();
            case 2:
                return new NormalGame();
            case 3:
                return new HardGame();
            default:
                throw new IllegalArgumentException("Unsupported difficulty: " + difficulty);
        }
    }

    protected boolean hasActiveBoss() {
        for (AbstractAircraft enemy : enemyAircrafts) {
            if (enemy instanceof BossEnemy && !enemy.notValid()) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage bgImage = cachedBackgroundImage != null ? cachedBackgroundImage : ImageManager.BACKGROUND_IMAGE;
        g.drawImage(bgImage, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(bgImage, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevisedProps(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        paintScoreAndLife(g);
    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : "Missing image for " + objects.getClass().getName();
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintImageWithPositionRevisedProps(Graphics g, List<IProp> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (IProp prop : objects) {
            if (prop instanceof AbstractFlyingObject) {
                AbstractFlyingObject flyingObject = (AbstractFlyingObject) prop;
                BufferedImage image = flyingObject.getImage();
                assert image != null : "Missing prop image.";
                g.drawImage(image, prop.getLocationX() - image.getWidth() / 2,
                        prop.getLocationY() - image.getHeight() / 2, null);
            }
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString(I18n.text("score") + ": " + this.score, x, y);
        y = y + 20;
        g.drawString(I18n.text("hp") + ": " + this.heroAircraft.getHp(), x, y);
    }
}



