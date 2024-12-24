package util;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javax.sound.sampled.FloatControl;
import java.io.FileInputStream;
import java.io.IOException;

public class MP3Player {
    private Player player;
    private FileInputStream fis;
    private String filePath;
    private Thread playThread;
    private FloatControl volumeControl;

    public MP3Player(String filePath) {
        this.filePath = filePath;
    }

    public void play() {
        try {
            fis = new FileInputStream(filePath);
            player = new Player(fis);

            // 创建一个线程来播放音乐
            playThread = new Thread(() -> {
                try {
                    while (true) { // 循环播放
                        player.play();
                        if (player.isComplete()) { // 如果播放完成，重新开始
                            player.close();
                            fis = new FileInputStream(filePath);
                            player = new Player(fis);
                        }
                    }
                } catch (JavaLayerException | IOException e) {
                    e.printStackTrace();
                    System.err.println("Failed to play music: " + e.getMessage());
                }
            });

            playThread.start(); // 启动播放线程
        } catch (JavaLayerException e) {
            e.printStackTrace();
            System.err.println("Failed to initialize player: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to open file: " + e.getMessage());
        }
    }

    public void stop() {
        if (player != null) {
            player.close(); // 停止播放
            playThread.interrupt(); // 中断播放线程
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            volumeControl.setValue(dB);
        }
    }
}



