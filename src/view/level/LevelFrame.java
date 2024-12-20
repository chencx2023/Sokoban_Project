package view.level;

import controller.FrameController;
import controller.GameController;
import model.MapMatrix;
import view.FrameUtil;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelFrame extends JFrame {
    private static FrameController frameController=new FrameController();
    //一个LevelFrame只绑定一个FrameController,所以设置成静态的
    public static final List<int[][]> levels = new ArrayList<>();

    public  List<int[][]> getLevels() {
        return levels;
    }

    static {
        levels.add(frameController.loadmatrix("resource/level/level1.txt"));
        levels.add(frameController.loadmatrix("resource/level/level2.txt"));
        levels.add(frameController.loadmatrix("resource/level/level3.txt"));
        levels.add(frameController.loadmatrix("resource/level/level4.txt"));
        levels.add(frameController.loadmatrix("resource/level/level5.txt"));
    }
//    public LevelFrame(int width, int height,FrameController frameController) {
//        this.frameController=frameController;
//        this.setTitle("Level");
//        this.setLayout(null);
//        this.setSize(width, height);
//        JButton level1 = FrameUtil.createButton(this, "Level1", new Point(30, height / 2 - 50), 100, 100);
//        JButton level2 = FrameUtil.createButton(this, "Level2", new Point(150, height / 2 - 50), 100, 100);
//        JButton level3 = FrameUtil.createButton(this, "Level3", new Point(270, height / 2 - 50), 100, 100);
//        JButton level4 = FrameUtil.createButton(this, "Level4", new Point(390, height / 2 - 50), 100, 100);
//        JButton level5 = FrameUtil.createButton(this, "Level5", new Point(510, height / 2 - 50), 100, 100);
//
//        level1.addActionListener(l->{
//            frameController.setLevel(1);
//            frameController.loadGame("resource/level/level1.txt");
//        });
//
//        level2.addActionListener(l->{
//            frameController.setLevel(2);
//            frameController.loadGame("resource/level/level2.txt");
//        });
//
//        level3.addActionListener(l->{
//            frameController.setLevel(3);
//            frameController.loadGame("resource/level/level3.txt");
//        });
//
//        level4.addActionListener(l->{
//            frameController.setLevel(4);
//            frameController.loadGame("resource/level/level4.txt");
//        });
//
//        level5.addActionListener(l->{
//            frameController.setLevel(5);
//            frameController.loadGame("resource/level/level5.txt");
//        });
//        //todo: complete all level.
//
//        this.setLocationRelativeTo(null);
//        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//    }

    public LevelFrame(int width, int height, FrameController frameController) {
        this.frameController = frameController;
        this.setTitle("Level");
        this.setLayout(null);
        this.setSize(width, height);

        // 创建下拉菜单 (JComboBox)
        String[] levels = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"};
        JComboBox<String> levelDropdown = new JComboBox<>(levels);

        // 设置下拉菜单的位置和大小
        levelDropdown.setBounds(width / 2 - 100, height / 2 - 250, 200, 40);
        // 设置下拉菜单的字体和颜色
        levelDropdown.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        levelDropdown.setBackground(Color.LIGHT_GRAY);
        levelDropdown.setForeground(Color.BLACK);

        // 添加监听器，当用户选择一个关卡时，加载对应的关卡
        levelDropdown.addActionListener(e -> {
            String selectedLevel = (String) levelDropdown.getSelectedItem();
            switch (selectedLevel) {
                case "Level 1":
                    frameController.setLevel(1);
                    frameController.loadGame("resource/level/level1.txt");
                    break;
                case "Level 2":
                    frameController.setLevel(2);
                    frameController.loadGame("resource/level/level2.txt");
                    break;
                case "Level 3":
                    frameController.setLevel(3);
                    frameController.loadGame("resource/level/level3.txt");
                    break;
                case "Level 4":
                    frameController.setLevel(4);
                    frameController.loadGame("resource/level/level4.txt");
                    break;
                case "Level 5":
                    frameController.setLevel(5);
                    frameController.loadGame("resource/level/level5.txt");
                    break;
            }
        });

        // 将下拉菜单添加到界面
        this.add(levelDropdown);

        // 设置窗口属性
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public static FrameController getFrameController() { return frameController; }

}
