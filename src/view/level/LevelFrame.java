package view.level;

import controller.FrameController;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LevelFrame extends JFrame {
    private static FrameController frameController = new FrameController();
    public static final List<int[][]> levels = new ArrayList<>();
    private static final String LEVELS_PATH = "resource/level/level";
    private static final int NUM_LEVELS = 14;

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
        this.getContentPane().setBackground(new Color(255, 223, 186)); // Peach background

        // Add title label
        JLabel titleLabel = new JLabel("Level Choose", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        titleLabel.setForeground(new Color(139, 69, 19)); // Brown text
        titleLabel.setBounds(width/2 - 100, 50, 200, 40);
        this.add(titleLabel);

        // Create dropdowns
        DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>();
        model1.addElement("Low Level");
        for (int i = 1; i <= 5; i++) {
            model1.addElement("Level " + i);
        }
        JComboBox<String> levelDropdown = new JComboBox<>(model1);

        DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>();
        model2.addElement("High Level");
        for (int i = 6; i <= 10; i++) {
            model2.addElement("Level " + i);
        }
        JComboBox<String> levelDropdown1 = new JComboBox<>(model2);

        DefaultComboBoxModel<String> model3 = new DefaultComboBoxModel<>();
        model3.addElement("Special Level");
        for (int i = 11; i <= 14; i++) {
            model3.addElement("Level " + i);
        }
        JComboBox<String> levelDropdown2 = new JComboBox<>(model3);

        // Position dropdowns
        levelDropdown.setBounds(width/2 - 100, height/2 - 150, 200, 40);
        levelDropdown1.setBounds(width/2 - 100, height/2, 200, 40);
        levelDropdown2.setBounds(width/2 - 100, height/2 + 150, 200, 40);

        // Style dropdowns
        Font menuFont = new Font("Comic Sans MS", Font.PLAIN, 16);
        Color buttonColor = new Color(173, 216, 230); // Light blue
        Color textColor = new Color(139, 69, 19); // Brown

        for (JComboBox<String> dropdown : new JComboBox[]{levelDropdown, levelDropdown1, levelDropdown2}) {
            dropdown.setFont(menuFont);
            dropdown.setBackground(buttonColor);
            dropdown.setForeground(textColor);
            dropdown.setBorder(BorderFactory.createLineBorder(textColor, 1));
        }

        // Add listeners
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

        this.add(levelDropdown);
        this.add(levelDropdown1);
        this.add(levelDropdown2);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static FrameController getFrameController() {
        return frameController;
    }

    public List<int[][]> getLevels() {
        return levels;
    }
}