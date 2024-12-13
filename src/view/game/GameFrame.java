package view.game;

import javax.swing.*;
import java.awt.*;

import controller.FrameController;
import controller.GameController;
import model.MapMatrix;
import view.FrameUtil;
import view.level.LevelFrame;

public class GameFrame extends JFrame {

    private GameController controller;
    private FrameController frameController;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton returnBtn;
    private JButton saveBtn;

    private JLabel stepLabel;
    private GamePanel gamePanel;

    public GameFrame(int width, int height, MapMatrix mapMatrix,FrameController frameController) {
        this.setTitle("2024 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapMatrix);//new的同时initiateGame
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(this,gamePanel, mapMatrix);
        this.frameController=frameController;

        System.out.println("GameFrame: Username = " + frameController.getUser()); // 打印用户名（调试）

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.returnBtn = FrameUtil.createButton(this, "Return", new Point(gamePanel.getWidth() + 80, 300), 80, 50);
        this.saveBtn = FrameUtil.createButton(this, "Save", new Point(gamePanel.getWidth() + 80, 390), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        gamePanel.setStepLabel(stepLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener

        });
        this.loadBtn.addActionListener(e -> {
            //String path = JOptionPane.showInputDialog(this, "Input path:");
            String path=String.format("resource/%s/level%d.txt",frameController.getUser(),frameController.getLevel());

            // 获取 LevelFrame 的实例
            LevelFrame levelFrame = frameController.getLevelFrame();
            levelFrame.getFrameController().loadGame(path,this);
            frameController.loadGame(path, this);

            //LevelFrame.getFrameController().loadGame(path,this);
            System.out.println(path);
//          gamePanel.requestFocusInWindow();//enable key listener
        });
        //todo: add other button here
        this.returnBtn.addActionListener(e -> {

            // 获取 LevelFrame 的实例
            LevelFrame levelFrame = frameController.getLevelFrame();
            levelFrame.getFrameController().returnLevelFrame(this);

           // LevelFrame.getFrameController().returnLevelFrame(this);
            gamePanel.requestFocusInWindow();//enable key listener
        });

        this.saveBtn.addActionListener(e -> {
            controller.saveGame();
            System.out.println("Save successfully!");
            gamePanel.requestFocusInWindow();
        });


        initGameLoop();

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void initGameLoop() {
        new Timer(1000 / 60, e -> {
            controller.checkwin();
            controller.checklose();
        }).start();
    }

    public FrameController getFrameController() {return frameController;}
    public GamePanel getGamePanel() {return gamePanel;}

}
