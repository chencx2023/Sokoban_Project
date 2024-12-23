package view.login;

import controller.FrameController;
import util.FileUtil;
import view.FrameUtil;
import view.level.LevelFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class LoginSelectionFrame extends JFrame {
    private JButton guestBtn;
    private JButton loginBtn;
    private FrameController frameController;

    public LoginSelectionFrame(int width, int height, FrameController frameController) {
        this.setTitle("Login Selection Frame");
        this.setLayout(null);
        this.setSize(width, height);
        this.frameController = frameController;

        guestBtn = FrameUtil.createButton(this, "Guest", new Point(40, 140), 100, 40);
        loginBtn = FrameUtil.createButton(this, "Login", new Point(170, 140), 100, 40);

        guestBtn.addActionListener(e -> {
            frameController.showLevelFrame();
            frameController.setUser("");
        });

        loginBtn.addActionListener(e -> {
            frameController.showLoginFrame();
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}