package controller;

import model.MapMatrix;
import util.FileUtil;
import view.game.GameFrame;
import view.level.LevelFrame;

import javax.swing.*;
import java.util.List;

public class FrameController {
    private LevelFrame levelFrame;
    FileUtil fileUtil = new FileUtil();

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

    public void loadGame(String path, JFrame frame) {
        frame.dispose();

        //file path-->string-->array-->MapMatrix model

        //read file to String
        List<String> lines = fileUtil.readFileToList(path);
        for (String line : lines) {
            System.out.println(line);
        }

        //turn String to array
        int[][] map = new int[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            //todo: identify invalid maps
            String[] elements = lines.get(i).split(" ");
            map[i] = new int[elements.length];
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = Integer.parseInt(elements[j]);
            }
        }
        //turn array to mapMatrix model
        MapMatrix mapMatrix=new MapMatrix(map);
        GameFrame gameFrame=new GameFrame(600, 450, mapMatrix);
        gameFrame.setVisible(true);
    }
}
