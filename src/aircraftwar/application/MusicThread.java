package aircraftwar.application;

import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicThread extends Thread {

    private String filename;
    private AudioFormat audioFormat;
    private byte[] samples;

    private volatile boolean isLooping = false;
    private volatile boolean isRunning = false;
    private SourceDataLine dataLine;

    public MusicThread(String filename) {
        this.filename = filename;
        reverseMusic();
    }

    public void reverseMusic() {
        try (InputStream inputStream = new BufferedInputStream(ResourceLoader.open(filename));
             AudioInputStream stream = AudioSystem.getAudioInputStream(inputStream)) {
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;
    }

    public void play(InputStream source) {
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        dataLine = null;

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
            dataLine.start();

            int numBytesRead;
            while (isRunning && (numBytesRead = source.read(buffer, 0, buffer.length)) != -1) {
                dataLine.write(buffer, 0, numBytesRead);
            }
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        } finally {
            SourceDataLine line = this.dataLine;
            if (line != null) {
                try {
                    line.stop();
                    line.flush();
                    line.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.dataLine = null;
            }
            try {
                source.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        if (audioFormat == null || samples == null) {
            return;
        }
        isRunning = true;
        try {
            do {
                InputStream stream = new ByteArrayInputStream(samples);
                play(stream);
            } while (isLooping && isRunning);
        } finally {
            isRunning = false;
        }
    }

    public void setLooping(boolean looping) {
        this.isLooping = looping;
    }

    public void stopMusic() {
        isRunning = false;
        isLooping = false;
        SourceDataLine line = this.dataLine;
        if (line != null) {
            try {
                line.stop();
                line.flush();
                line.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.dataLine = null;
        }
        interrupt();
    }

    public boolean isPlaying() {
        return isRunning;
    }
}

