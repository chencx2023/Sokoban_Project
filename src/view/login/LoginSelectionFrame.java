package view.login;

import controller.FrameController;
import util.FileUtil;
import view.FrameUtil;
import view.level.LevelFrame;

import javax.swing.*;
import java.awt.*;

public class LoginSelectionFrame extends JFrame {
    private JButton guestBtn;
    private JButton loginBtn;
    private FrameController frameController;
    private static final Color BACKGROUND_COLOR = new Color(245, 222, 179);
    private static final Color BUTTON_COLOR = new Color(210, 180, 140);
    private static final Color TEXT_COLOR = new Color(139, 69, 19);

    public LoginSelectionFrame(int width, int height, FrameController frameController) {
        this.setTitle("Login Selection");
        this.setLayout(null);
        this.setSize(width, height);
        this.frameController = frameController;
        this.getContentPane().setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Choose Login Method", SwingConstants.CENTER);
        titleLabel.setBounds(30, 50, 250, 50);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);

        guestBtn = createStyledButton("Guest", new Point(40, 140));
        loginBtn = createStyledButton("Login", new Point(170, 140));

        guestBtn.addActionListener(e -> {
            frameController.showLevelFrame();
            frameController.setUser("");
        });

        loginBtn.addActionListener(e -> {
            frameController.showLoginFrame();
        });

        this.add(titleLabel);
        this.add(guestBtn);
        this.add(loginBtn);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JButton createStyledButton(String text, Point location) {
        JButton button = new JButton(text);
        button.setBounds(location.x, location.y, 100, 40);
        button.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        return button;
    }
}