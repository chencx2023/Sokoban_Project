package controller;

import model.MapMatrix;
import util.FileUtil;
import view.game.GameFrame;
import view.level.LevelFrame;
import view.login.LoginFrame;
import view.login.LoginSelectionFrame;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class FrameController {
    private LevelFrame levelFrame;
    private LoginFrame loginFrame;
    private LoginSelectionFrame loginSelectionFrame;
    private GameFrame gameFrame;
    private String user; //保存用户名
    private int level;
    FileUtil fileUtil = new FileUtil();

    public String getUser() {return user;}

    public void setUser(String user) {
        System.out.println("FrameController: Setting username = " + user); // 打印用户名（调试）
        this.user = user;
        createUserDirectory(user); // 设置用户时自动创建目录
    }

    public int getLevel() {return level;}
    public void setLevel(int level) {this.level = level;}


    private void createUserDirectory(String user) {
        //为新注册的用户创建目录
        if (user.equals("")) {
            System.out.println("Username is empty, directory not created.");
            return; // 如果 username 为空，直接返回，不创建目录
        }
        String path = String.format("resource/%s", user);
        File directory = new File(path);  //创建File对象
        if(!directory.exists()){
            boolean created = directory.mkdirs();
            if(created){
                System.out.println("User directory created:"+path);
            }else{
                System.out.println("Failed to create user directory: " + path);
            }
        }else{
            // 已经注册过，不创建目录
            System.out.println("User directory already exists: " + path);
        }
    }

    public LevelFrame getLevelFrame() {
        return levelFrame;
    }
    public void setLevelFrame(LevelFrame levelFrame) {
        this.levelFrame = levelFrame;
    }

    public void returnLevelFrame(JFrame frame) {
        frame.dispose(); //彻底关闭GameFrame并释放资源
        levelFrame.setVisible(true); //显示LevelFrame
    }

    public void loadGame(String path, JFrame frame) {
        frame.dispose();

        //file path-->string-->array-->MapMatrix model

        //read file to String
        List<String> lines = fileUtil.readFileToList(path);
        for (String line : lines) {
            System.out.println(line);
        }

        //turn String to array
        int[][] map = new int[lines.size()-1][];
        for (int i = 0; i < lines.size()-1; i++) {
            //todo: identify invalid maps
            String[] elements = lines.get(i).split(",");
            map[i] = new int[elements.length];
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = Integer.parseInt(elements[j]);
            }
        }
        //turn array to mapMatrix model
        MapMatrix mapMatrix=new MapMatrix(map);
        GameFrame gameFrame=new GameFrame(600, 550, mapMatrix,this);
        gameFrame.getGamePanel().setSteps(Integer.parseInt(lines.get(lines.size()-1)));
        gameFrame.setVisible(true);
    }

    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame=loginFrame;
    }
    public void setLoginSelectionFrame(LoginSelectionFrame loginSelectionFrame) {
        this.loginSelectionFrame = loginSelectionFrame;
    }
    public void setGameFrame(GameFrame gameFrame){
        this.gameFrame=gameFrame;
    }
    public GameFrame getGameFrame(){
        return gameFrame;
    }

    public void showLoginSelectionFrame() {
        loginSelectionFrame.setVisible(true);
        loginFrame.setVisible(false);
        levelFrame.setVisible(false);
    }

    // 显示 LoginFrame
    public void showLoginFrame() {
        loginSelectionFrame.setVisible(false);
        loginFrame.setVisible(true);
        levelFrame.setVisible(false);
    }

    // 显示 LevelFrame
    public void showLevelFrame() {
        loginSelectionFrame.setVisible(false);
        loginFrame.setVisible(false);
        levelFrame.setVisible(true);
    }
}

