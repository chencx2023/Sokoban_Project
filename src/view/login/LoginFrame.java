package view.login;

import controller.FrameController;
import util.FileUtil;
import view.FrameUtil;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class LoginFrame extends JFrame {
    private JTextField username;
    private JTextField password;
    private JButton submitBtn;
    private JButton resetBtn;
    private FrameController frameController;
    FileUtil fileUtil = new FileUtil();
    private static final Color BACKGROUND_COLOR = new Color(245, 222, 179);
    private static final Color BUTTON_COLOR = new Color(210, 180, 140);
    private static final Color TEXT_COLOR = new Color(139, 69, 19);

    public LoginFrame(int width, int height, FrameController frameController) {
        this.frameController = frameController;
        this.setTitle("Login Frame");
        this.setLayout(null);
        this.setSize(width, height);
        this.getContentPane().setBackground(BACKGROUND_COLOR);

        Font labelFont = new Font("Comic Sans MS", Font.BOLD, 14);
        Font textFont = new Font("Comic Sans MS", Font.PLAIN, 14);

        JLabel userLabel = new JLabel("Username:", SwingConstants.RIGHT);
        userLabel.setBounds(40, 30, 80, 40);
        userLabel.setFont(labelFont);
        userLabel.setForeground(TEXT_COLOR);

        JLabel passLabel = new JLabel("Password:", SwingConstants.RIGHT);
        passLabel.setBounds(50, 90, 70, 40);
        passLabel.setFont(labelFont);
        passLabel.setForeground(TEXT_COLOR);

        username = new JTextField();
        username.setBounds(120, 30, 120, 40);
        username.setFont(textFont);

        password = new JTextField();
        password.setBounds(120, 90, 120, 40);
        password.setFont(textFont);

        submitBtn = new JButton("Confirm");
        submitBtn.setBounds(40, 150, 100, 40);
        styleButton(submitBtn);

        resetBtn = new JButton("Reset");
        resetBtn.setBounds(160, 150, 100, 40);
        styleButton(resetBtn);

        submitBtn.addActionListener(e -> {
            System.out.println("Username = " + username.getText());
            System.out.println("Password = " + password.getText());

            String inputUsername = username.getText();
            String inputPassword = password.getText();

            System.out.println("LoginFrame: Username = " + inputUsername);

            if(validateLogin(inputUsername, inputPassword)) {
                JOptionPane.showMessageDialog(LoginFrame.this, "Login successful!");
                frameController.setUser(inputUsername);
                frameController.showLevelFrame();
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password");
            }
        });

        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });

        this.add(userLabel);
        this.add(passLabel);
        this.add(username);
        this.add(password);
        this.add(submitBtn);
        this.add(resetBtn);

        this.frameController.setLoginFrame(this);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
    }

    private boolean validateLogin(String username, String password) {
        if(username.equals("")) {
            return false;
        }
        List<String> lines = fileUtil.readFileToList("data/users.csv");
        for (int i = 0; i < lines.size(); i++) {
            String name = lines.get(i).split(",")[0];
            String key = lines.get(i).split(",")[1];
            if (name.equals(username)) {
                if (key.equals(password)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        lines.add(username+','+password);
        fileUtil.writeFileFromList("data/users.csv",lines);
        return true;
    }
}