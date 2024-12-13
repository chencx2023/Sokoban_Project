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
    private LevelFrame levelFrame;
    private LoginFrame loginFrame;

    public LoginSelectionFrame(int width, int height, FrameController frameController) {
        this.setTitle("Login Selection Frame");
        this.setLayout(null);
        this.setSize(width, height);
        this.frameController=frameController;

        guestBtn = FrameUtil.createButton(this, "Guest", new Point(20, 140), 100, 40);
        loginBtn=FrameUtil.createButton(this, "Login", new Point(150, 140), 100, 40);

        guestBtn.addActionListener(e -> {
            this.setVisible(false);
            levelFrame.setVisible(true);
            frameController.setUser("");
        });

        loginBtn.addActionListener(e -> {
            this.setVisible(false);
            loginFrame.setVisible(true);
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public LevelFrame getLevelFrame() {
        return levelFrame;
    }

    public void setLevelFrame(LevelFrame levelFrame) {
        this.levelFrame = levelFrame;
    }

    public LoginFrame getLoginFrame() {
        return loginFrame;
    }

    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }
}
