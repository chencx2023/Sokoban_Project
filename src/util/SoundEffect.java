package util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEffect {
    private Clip clip;
    private FloatControl volumeControl;

    public SoundEffect(String filePath) {
        try {
            // 加载音频文件
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            // 获取音频格式
            AudioFormat format = audioInputStream.getFormat();

            // 创建数据行信息
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            // 获取音频剪辑
            clip = (Clip) AudioSystem.getLine(info);

            // 打开音频剪辑并加载音频数据
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0); // 重置音频到开头
            clip.start(); // 开始播放
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            volumeControl.setValue(dB);
        }
    }

}
