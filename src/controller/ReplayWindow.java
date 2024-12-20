package controller;


import model.MapMatrix;
import view.FrameUtil;
import view.game.GamePanel;
import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class ReplayWindow extends JFrame {
    private GamePanel gamePanel;  // 用于展示回放的游戏视图
    private MapMatrix modelState;// 当前的游戏状态
    private final GameController gameController;
    private int step;
    private JLabel stepLabel;
    private JLabel trailLabel;

    public ReplayWindow(MapMatrix model,GameController gameController) {
        // 设置窗口基本属性
        this.modelState = model;
        this.gameController = gameController;
        setTitle("Game Replay");
        setSize(800, 600); // 可以根据需要调整大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // 居中显示
        // 创建并设置 GamePanel（你可以通过 constructor 或者 setter 传入 model）
        gamePanel = new GamePanel(model);
        this.step = gameController.getView().getSteps();
        gamePanel.setSteps(step);
        gamePanel.setSize(model.getWidth() * 50, model.getHeight() * 50);
        gamePanel.setLayout(null);
        this.stepLabel = FrameUtil.createJLabel(this, String.format("Step:%d",step), new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        this.trailLabel = FrameUtil.createJLabel(this, "Trail", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 20), 180, 50);
        gamePanel.setStepLabel(stepLabel);
        gamePanel.setTrailLabel(trailLabel);
        add(gamePanel, BorderLayout.CENTER);
    }

    public void updateView(int[][] newModel) {
        MapMatrix newmodel = new MapMatrix(newModel);
        gamePanel.setSteps(step+1);
        step--;
        gamePanel.setModel(newmodel);
        gamePanel.ResetGamePanel(); // 调用 GamePanel 更新视图
    }
}

