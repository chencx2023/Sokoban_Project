package view.login;

import controller.FrameController;
import util.FileUtil;
import view.FrameUtil;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.ArrayList;


public class LoginFrame extends JFrame {
    private JTextField username;
    private JTextField password;
    private JButton submitBtn;
    private JButton resetBtn;
    private FrameController frameController;
    FileUtil fileUtil=new FileUtil();


    public LoginFrame(int width, int height, FrameController frameController) {
        this.frameController=frameController;
        this.setTitle("Login Frame");
        this.setLayout(null);
        this.setSize(width, height);
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "password:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);
        password = FrameUtil.createJTextField(this, new Point(120, 80), 120, 40);

        submitBtn = FrameUtil.createButton(this, "Confirm", new Point(40, 140), 100, 40);
        resetBtn = FrameUtil.createButton(this, "Reset", new Point(160, 140), 100, 40);

        submitBtn.addActionListener(e -> {
            System.out.println("Username = " + username.getText());
            System.out.println("Password = " + password.getText());

            //todo: check login info
            String inputUsername=username.getText();
            String inputPassword=password.getText();

            System.out.println("LoginFrame: Username = " + inputUsername); // 打印用户名（调试）

            if(validateLogin(inputUsername,inputPassword)){
                JOptionPane.showMessageDialog(LoginFrame.this,"Login successful!");
                frameController.setUser(inputUsername);
                frameController.showLevelFrame();
            }else{
                JOptionPane.showMessageDialog(LoginFrame.this,"Invalid username or password");
            }
        });
        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });
        this.frameController.setLoginFrame(this);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private boolean validateLogin(String username, String password) {
        // 检查用户名是否为空
        if(username.equals("")){
            return false;
        }
        List<String> lines = fileUtil.readFileToList("data/users.csv");
        for (int i = 0; i < lines.size(); i++) {
            //遍历user数据库的每一个用户
            String name = lines.get(i).split(",")[0];
            String key = lines.get(i).split(",")[1];
            if (name.equals(username)) {
                //找到匹配用户，检查密码
                if (key.equals(password)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        //未找到匹配用户，为用户注册账号，将数据添加到user库中
        lines.add(username+','+password);
        fileUtil.writeFileFromList("data/users.csv",lines);
        return true;
    }

}
