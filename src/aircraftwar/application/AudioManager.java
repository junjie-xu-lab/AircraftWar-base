package aircraftwar.application;

public class AudioManager {

    private static AudioManager instance = null;

    private MusicThread bgmThread;
    private MusicThread bossBgmThread;

    private AudioManager() {
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            synchronized (AudioManager.class) {
                if (instance == null) {
                    instance = new AudioManager();
                }
            }
        }
        return instance;
    }

    public void playBackgroundMusic() {
        stopAllMusic();
        bgmThread = new MusicThread("videos/bgm.wav");
        bgmThread.setLooping(true);
        bgmThread.start();
    }

    public void playBossMusic() {
        if (bgmThread != null && bgmThread.isPlaying()) {
            bgmThread.stopMusic();
            bgmThread = null;
        }
        if (bossBgmThread != null && bossBgmThread.isPlaying()) {
            bossBgmThread.stopMusic();
        }
        bossBgmThread = new MusicThread("videos/bgm_boss.wav");
        bossBgmThread.setLooping(true);
        bossBgmThread.start();
    }

    public void stopBossMusic() {
        if (bossBgmThread != null && bossBgmThread.isPlaying()) {
            bossBgmThread.stopMusic();
            bossBgmThread = null;
        }
        playBackgroundMusic();
    }

    public void stopAllMusic() {
        if (bgmThread != null) {
            bgmThread.stopMusic();
            bgmThread = null;
        }
        if (bossBgmThread != null) {
            bossBgmThread.stopMusic();
            bossBgmThread = null;
        }
    }

    public void playBulletHit() {
        new MusicThread("videos/bullet_hit.wav").start();
    }

    public void playBombExplosion() {
        new MusicThread("videos/bomb_explosion.wav").start();
    }

    public void playGetSupply() {
        new MusicThread("videos/get_supply.wav").start();
    }

    public void playGameOver() {
        stopAllMusic();
        new MusicThread("videos/game_over.wav").start();
    }
}

