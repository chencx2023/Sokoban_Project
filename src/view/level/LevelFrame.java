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
    private static FrameController frameController = new FrameController();
    //一个LevelFrame只绑定一个FrameController,所以设置成静态的
    public static final List<int[][]> levels = new ArrayList<>();
    private static final String LEVELS_PATH = "resource/level/level";
    private static final int NUM_LEVELS = 14;

    public List<int[][]> getLevels() {
        return levels;
    }

    static {
        for (int i = 1; i <= NUM_LEVELS; i++) {
            levels.add(frameController.loadmatrix(LEVELS_PATH + i + ".txt").getMatrix());
        }
    }

    public LevelFrame(int width, int height, FrameController frameController) {
        this.frameController = frameController;
        this.setTitle("Level");
        this.setLayout(null);
        this.setSize(width, height);

        // 创建第一个下拉菜单 (JComboBox) - 初级关卡
        DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>();
        model1.addElement("Low Level");  // 添加默认显示项
        for (int i = 1; i <= 5; i++) {
            model1.addElement("Level " + i);
        }
        JComboBox<String> levelDropdown = new JComboBox<>(model1);

        // 创建第二个下拉菜单 - 高级关卡
        DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>();
        model2.addElement("High Level");  // 添加默认显示项
        for (int i = 6; i <= 10; i++) {
            model2.addElement("Level " + i);
        }
        JComboBox<String> levelDropdown1 = new JComboBox<>(model2);

        DefaultComboBoxModel<String> model3 = new DefaultComboBoxModel<>();
        model3.addElement("Special Level");  // 添加默认显示项
        for (int i = 11; i <= 14; i++) {
            model3.addElement("Level " + i);
        }
        JComboBox<String> levelDropdown2 = new JComboBox<>(model3);

        // 设置下拉菜单的位置和大小
        levelDropdown.setBounds(width / 2 - 100, height / 2 - 250, 200, 40);
        levelDropdown1.setBounds(width / 2 - 100, height / 2 - 150, 200, 40);
        levelDropdown2.setBounds(width / 2 - 100, height / 2 - 50, 200, 40);

        // 设置下拉菜单的字体和颜色
        Font menuFont = new Font("Comic Sans MS", Font.BOLD, 18);
        levelDropdown.setFont(menuFont);
        levelDropdown1.setFont(menuFont);
        levelDropdown2.setFont(menuFont);

        levelDropdown.setBackground(Color.LIGHT_GRAY);
        levelDropdown1.setBackground(Color.LIGHT_GRAY);
        levelDropdown2.setBackground(Color.LIGHT_GRAY);

        levelDropdown.setForeground(Color.BLACK);
        levelDropdown1.setForeground(Color.BLACK);
        levelDropdown2.setForeground(Color.BLACK);

        // 添加监听器，当用户选择一个关卡时，加载对应的关卡
        levelDropdown.addActionListener(e -> {
            String selectedLevel = (String) levelDropdown.getSelectedItem();
            if (selectedLevel != null && !selectedLevel.equals("Low Level")) {
                int level = Integer.parseInt(selectedLevel.substring(6));
                frameController.setLevel(level);
                frameController.loadGame(LEVELS_PATH + level + ".txt");
            }
        });

        levelDropdown1.addActionListener(e -> {
            String selectedLevel = (String) levelDropdown1.getSelectedItem();
            if (selectedLevel != null && !selectedLevel.equals("High Level")) {
                int level = Integer.parseInt(selectedLevel.substring(6));
                frameController.setLevel(level);
                frameController.loadGame(LEVELS_PATH + level + ".txt");
            }
        });

        levelDropdown2.addActionListener(e -> {
            String selectedLevel = (String) levelDropdown2.getSelectedItem();
            if (selectedLevel != null && !selectedLevel.equals("Special Level")) {
                int level = Integer.parseInt(selectedLevel.substring(6));
                frameController.setLevel(level);
                frameController.loadGame(LEVELS_PATH + level + ".txt");
            }
        });

        // 将下拉菜单添加到界面
        this.add(levelDropdown);
        this.add(levelDropdown1);
        this.add(levelDropdown2);

        // 设置窗口属性
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static FrameController getFrameController() {
        return frameController;
    }
}