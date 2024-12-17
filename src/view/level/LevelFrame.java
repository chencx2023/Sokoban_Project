package view.level;

import controller.FrameController;
import controller.GameController;
import model.MapMatrix;
import view.FrameUtil;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelFrame extends JFrame {
    private static FrameController frameController=new FrameController();
    //一个LevelFrame只绑定一个FrameController,所以设置成静态的
    public static final List<int[][]> levels = new ArrayList<>();

    public  List<int[][]> getLevels() {
        return levels;
    }

    static {
        levels.add(frameController.loadmatrix("resource/level1.txt"));
        levels.add(frameController.loadmatrix("resource/level2.txt"));
        levels.add(frameController.loadmatrix("resource/level3.txt"));
        levels.add(frameController.loadmatrix("resource/level4.txt"));
        levels.add(frameController.loadmatrix("resource/level5.txt"));
    }
    public LevelFrame(int width, int height,FrameController frameController) {
        this.frameController=frameController;
        this.setTitle("Level");
        this.setLayout(null);
        this.setSize(width, height);
        JButton level1 = FrameUtil.createButton(this, "Level1", new Point(30, height / 2 - 50), 100, 100);
        JButton level2 = FrameUtil.createButton(this, "Level2", new Point(150, height / 2 - 50), 100, 100);
        JButton level3 = FrameUtil.createButton(this, "Level3", new Point(270, height / 2 - 50), 100, 100);
        JButton level4 = FrameUtil.createButton(this, "Level4", new Point(390, height / 2 - 50), 100, 100);
        JButton level5 = FrameUtil.createButton(this, "Level5", new Point(510, height / 2 - 50), 100, 100);

        level1.addActionListener(l->{
            frameController.setLevel(1);
            frameController.loadGame("resource/level1.txt");
        });

        level2.addActionListener(l->{
            frameController.setLevel(2);
            frameController.loadGame("resource/level2.txt");
        });

        level3.addActionListener(l->{
            frameController.setLevel(3);
            frameController.loadGame("resource/level3.txt");
        });

        level4.addActionListener(l->{
            frameController.setLevel(4);
            frameController.loadGame("resource/level4.txt");
        });

        level5.addActionListener(l->{
            frameController.setLevel(5);
            frameController.loadGame("resource/level5.txt");
        });
        //todo: complete all level.

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static FrameController getFrameController() { return frameController; }

}
