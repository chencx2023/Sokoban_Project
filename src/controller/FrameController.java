package controller;

import model.MapMatrix;
import util.FileUtil;
import view.game.GameFrame;
import view.level.LevelFrame;
import view.login.LoginFrame;
import view.login.LoginSelectionFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public void loadGame1(String path, JFrame frame) {
        frame.dispose();

        //file path-->string-->array-->MapMatrix model
        //read file to String
        List<String> lines = fileUtil.readFileToList(path);
        for (String line : lines) {
            System.out.println(line);
        }

        //ToDo:identify invalid maps
        // 检查文件格式
        if (lines.size() < 3) {
            System.out.println("Invalid save file: Not enough lines.");
            JOptionPane.showMessageDialog(null, "Invalid save file: Not enough lines.");
            return;
        }

        // 检查倒数第二行是否为步数
        String lastLine = lines.get(lines.size() - 2);
        if (!lastLine.matches("\\d+")) {
            System.out.println("Invalid save file: Last line is not a valid step count.");
            JOptionPane.showMessageDialog(null, "Invalid save file: Last line is not a valid step count.");
            return;
        }

        // 检查最后一行是否为哈希值
        String savedHash = lines.get(lines.size() - 1);
        lines.remove(lines.size() - 1); // 移除哈希值

        // 重新计算哈希值
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            for (String line : lines) {
                md.update(line.getBytes());
            }
            byte[] hash = md.digest();

            // 将哈希值转换为字符串
            StringBuilder hashString = new StringBuilder();
            for (byte b : hash) {
                hashString.append(String.format("%02x", b));
            }

            // 比较哈希值
            if (!hashString.toString().equals(savedHash)) {
                System.out.println("Fail to load: File has been modified.");
                JOptionPane.showMessageDialog(null, "Fail to load: File has been modified.");
                return;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        //turn String to array
        int[][] map = new int[lines.size()-1][];
        for (int i = 0; i < lines.size()-1; i++) {
            String[] elements = lines.get(i).split(",");
            map[i] = new int[elements.length];
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = Integer.parseInt(elements[j]);
            }
        }
        //turn array to mapMatrix model
        MapMatrix mapMatrix=new MapMatrix(map);
        GameFrame gameFrame=new GameFrame(800, 750, mapMatrix,this);
        gameFrame.getGamePanel().setSteps(Integer.parseInt(lines.get(lines.size()-1)));
        gameFrame.updateStepLabel();
        gameFrame.setVisible(true);
        gameFrame.getGamePanel().requestFocusInWindow();
    }

    public int[][] loadmatrix(String path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + path, e);
        }

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("The file is empty or invalid: " + path);
        }

        int[][] map = new int[lines.size()][];
        int expectedLength = -1;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue; // 跳过空行
            }

            String[] elements = line.split("\\s+");
            if (expectedLength == -1) {
                expectedLength = elements.length; // 设置第一行的长度为期望长度
            } else if (elements.length != expectedLength) {
                throw new IllegalArgumentException("Inconsistent row lengths in the file at line " + (i + 1));
            }

            map[i] = new int[elements.length];
            for (int j = 0; j < elements.length; j++) {
                try {
                    map[i][j] = Integer.parseInt(elements[j]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number format at line " + (i + 1) + ", column " + (j + 1) + ": '" + elements[j] + "'");
                }
            }
        }
        return map;
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


    public void loadGame(String path, JFrame frame) {
        frame.dispose();
        loadGame(path);
    }

    public void loadGame(String path) {
        MapMatrix mapMatrix=new MapMatrix(loadmatrix(path));
        GameFrame gameFrame=new GameFrame(800, 750, mapMatrix,this);
        gameFrame.setVisible(true);
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
