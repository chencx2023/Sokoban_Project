package controller;

import view.level.LevelFrame;

import javax.swing.*;

public class FrameController {
    private LevelFrame levelFrame;

    public LevelFrame getLevelFrame() {
        return levelFrame;
    }

    public void setLevelFrame(LevelFrame levelFrame) {
        this.levelFrame = levelFrame;
    }

    public void returnLevelFrame(JFrame frame) {
        frame.dispose(); //彻底关闭GameFrame并释放资源
        levelFrame.setVisible(true); //显示LevelFrame

    }
}
