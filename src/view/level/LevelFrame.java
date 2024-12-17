package view.level;

import controller.FrameController;
import model.MapMatrix;
import view.FrameUtil;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;

public class LevelFrame extends JFrame {
    private FrameController frameController=new FrameController();
    //一个LevelFrame只绑定一个FrameController,所以设置成静态的

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
            MapMatrix mapMatrix = new MapMatrix(new int[][]{
                    {1, 1, 1, 1, 1, 1},
                    {1, 20, 0, 0, 0, 1},
                    {1, 0, 0, 10, 2, 1},
                    {1, 0, 2, 10, 0, 1},
                    {1, 1, 1, 1, 1, 1},
            });
            GameFrame gameFrame = new GameFrame(600, 550, mapMatrix,frameController);
            frameController.setGameFrame(gameFrame);
            this.setVisible(false);
            gameFrame.setVisible(true);
        });

        level2.addActionListener(l->{
            frameController.setLevel(2);
            MapMatrix mapMatrix = new MapMatrix(new int[][]{
                    {1, 1, 1, 1, 1, 1, 0},
                    {1, 20, 0, 0, 0, 1, 1},
                    {1, 0, 10, 10, 0, 0, 1},
                    {1, 0, 1, 2, 0, 2, 1},
                    {1, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1},
            });
            GameFrame gameFrame = new GameFrame(600, 550, mapMatrix,frameController);
            frameController.setGameFrame(gameFrame);
            this.setVisible(false);
            gameFrame.setVisible(true);
        });

        level3.addActionListener(l->{
            frameController.setLevel(3);
            MapMatrix mapMatrix = new MapMatrix(new int[][]{
                    {0, 0, 1, 1, 1, 1, 0},
                    {1, 1, 1, 0, 0, 1, 0},
                    {1, 20, 0, 2, 10, 1, 1},
                    {1, 0, 0, 0, 10, 0, 1},
                    {1, 0, 1, 2, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1},
            });
            GameFrame gameFrame = new GameFrame(600, 550, mapMatrix,frameController);
            frameController.setGameFrame(gameFrame);
            this.setVisible(false);
            gameFrame.setVisible(true);
        });

        level4.addActionListener(l->{
            frameController.setLevel(4);
            MapMatrix mapMatrix = new MapMatrix(new int[][]{
                    {0, 1, 1, 1, 1, 1, 0},
                    {1, 1, 20, 0, 0, 1, 1},
                    {1, 0, 0, 1, 0, 0, 1},
                    {1, 0, 10, 12, 10, 0, 1},
                    {1, 0, 0, 2, 0, 0, 1},
                    {1, 1, 0, 2, 0, 1, 1},
                    {0, 1, 1, 1, 1, 1, 0},
            });
            GameFrame gameFrame = new GameFrame(600, 550, mapMatrix,frameController);
            frameController.setGameFrame(gameFrame);
            this.setVisible(false);
            gameFrame.setVisible(true);
        });

        level5.addActionListener(l->{
            frameController.setLevel(5);
            MapMatrix mapMatrix = new MapMatrix(new int[][]{
                    {1, 1, 1, 1, 1, 1, 0, 0},
                    {1, 0, 0, 0, 0, 1, 1, 1},
                    {1, 0, 0, 0, 2, 2, 0, 1},
                    {1, 0, 10, 10, 10, 20, 0, 1},
                    {1, 0, 0, 1, 0, 2, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
            });
            GameFrame gameFrame = new GameFrame(600, 550, mapMatrix,frameController);
            frameController.setGameFrame(gameFrame);
            this.setVisible(false);
            gameFrame.setVisible(true);
        });
        //todo: complete all level.

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public FrameController getFrameController() { return frameController; }

}
