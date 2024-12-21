package view.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton undoBtn;
    private JButton replayBtn;

    private JLabel stepLabel;
    private JLabel trailLabel;
    private GamePanel gamePanel;

    private Timer timer;
    private int seconds=0;
    private JLabel timeLabel;

    public GameFrame(int width, int height, MapMatrix mapMatrix,FrameController frameController) {
        this.frameController = frameController;
        this.setTitle("2024 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapMatrix);//new的同时initiateGame
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2 - 100);
        this.add(gamePanel);
        this.controller = new GameController(this,gamePanel, mapMatrix);
        this.frameController=frameController;

        System.out.println("GameFrame: Username = " + frameController.getUser()); // 打印用户名（调试）

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 190), 80, 50);
        this.returnBtn = FrameUtil.createButton(this, "Return", new Point(gamePanel.getWidth() + 80, 260), 80, 50);
        this.undoBtn = FrameUtil.createButton(this, "Undo", new Point(gamePanel.getWidth() + 80, 330), 80, 50);
        this.replayBtn = FrameUtil.createButton(this, "Replay", new Point(gamePanel.getWidth() + 80, 400), 80, 50);
        this.saveBtn = FrameUtil.createButton(this, "Save", new Point(gamePanel.getWidth() + 80, 470), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Step:0", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        this.trailLabel = FrameUtil.createJLabel(this, "Trail", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 20), 180, 50);
        gamePanel.setStepLabel(stepLabel);
        gamePanel.setTrailLabel(trailLabel);

        this.timeLabel=FrameUtil.createJLabel(this, "Time:0", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 600), 180, 50);

        timer=new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                updateTimeLabel();
            }
        });
        timer.start();

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener

        });
        this.loadBtn.addActionListener(e -> {
            //String path = JOptionPane.showInputDialog(this, "Input path:");
            String user=frameController.getUser();

            if (user == null || user.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Guest Mode user cannot load game.");
                return; // 阻止加载游戏
            }

            String path=String.format("resource/%s/level%d.txt",user,frameController.getLevel());

            frameController.loadGame1(path, this);

            System.out.println(path);
        });

        this.undoBtn.addActionListener(e -> {
            controller.undo();
            gamePanel.requestFocusInWindow();//enable key listener
        });

        this.replayBtn.addActionListener(e -> {
            controller.startReplay();
            gamePanel.requestFocusInWindow();//enable key listener
        });


        //todo: add other button here
        this.returnBtn.addActionListener(e -> {
            // 获取 LevelFrame 的实例
            LevelFrame levelFrame = frameController.getLevelFrame();
            LevelFrame.getFrameController().returnLevelFrame(this);
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
    public void updateStepLabel() {
        int steps = gamePanel.getSteps();
        String stepText = String.format("Steps: %d", steps);
        stepLabel.setText(stepText); // 更新 stepLabel 的文本
    }

    private void initGameLoop() {
        new Timer(1000 / 60, e -> {
            controller.checkwin();
            controller.checklose();
        }).start();
    }

    //更新计时器标签
    private void updateTimeLabel(){
        timeLabel.setText("Time:"+seconds);
    }

    //停止计时器
    public void stopTimer(){
        timer.stop();
    }


    public FrameController getFrameController() {return frameController;}
    public GamePanel getGamePanel() {return gamePanel;}

}
