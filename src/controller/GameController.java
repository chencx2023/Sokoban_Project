package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import model.Direction;
import model.MapMatrix;
import util.FileUtil;
import view.game.GamePanel;
import view.game.GridComponent;
import view.game.Hero;
import view.game.Box;
import view.game.GameFrame;
import view.level.LevelFrame;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static view.level.LevelFrame.levels;


/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;//gamepanel是final的
    private final MapMatrix model;
    private final GameFrame frame;
//    private final LevelFrame levelFrame = new LevelFrame(650,200);
    private final List<int[][]> maps = new ArrayList<>();
    private ReplayWindow replayWindow;
    FileUtil fileUtil=new FileUtil();


    public GamePanel getView() {
        return view;
    }
    public List<int[][]> getMaps() {
        return maps;
    }

    private boolean haswon = false;
    private boolean haslosed = false;


    public GameController(GameFrame frame, GamePanel view, MapMatrix model) {
        this.frame = frame;
        this.view = view;
        this.model = model;
        maps.add(MapMatrix.copyArray(model.getMatrix()));
        view.setController(this);
    }

    public void restartGame() {
        //ToDo: reset model & view
        System.out.println("Do restart game here");
        haswon = false;
        haslosed = false;
        this.model.resetMapMatrix();
        this.view.restartGame();
        maps.clear();
        maps.add(model.getInitialMatrix());
        frame.setSeconds(0);
        frame.getTimer().stop();
        frame.getTimer().restart();
    }

    public void startGameFromCurrentModel(MapMatrix mapMatrix, int step){
        haswon = false;
        haslosed = false;
        model.setMatrix(mapMatrix.getMatrix());
        view.afterstartGameFromCurrentModel(step);
    }

    public boolean doMove(int row, int col, Direction direction) {
        GridComponent currentGrid = view.getGridComponent(row, col);
        //target row can column.
        int tRow = row + direction.getRow();
        int tCol = col + direction.getCol();
        int ttRow = tRow + direction.getRow();
        int ttCol = tCol + direction.getCol();
        GridComponent targetGrid = view.getGridComponent(tRow, tCol);
        int[][] map = model.getMatrix();
        if (map[tRow][tCol] == 0 || map[tRow][tCol] == 2) {
            //update hero in MapMatrix
            model.getMatrix()[row][col] -= 20;
            model.getMatrix()[tRow][tCol] += 20;
            //Update hero in GamePanel
            Hero h = currentGrid.removeHeroFromGrid();
            targetGrid.setHeroInGrid(h);
            //Update the row and column attribute in hero
            h.setRow(tRow);
            h.setCol(tCol);
            return true;
        }
        if ((map[tRow][tCol] == 10 || map[tRow][tCol] == 12) && (map[ttRow][ttCol] == 0 || map[ttRow][ttCol] == 2)){
            GridComponent ttargetGrid = view.getGridComponent(ttRow, ttCol);
            model.getMatrix()[row][col] -= 20;
            model.getMatrix()[tRow][tCol] += 10;
            model.getMatrix()[ttRow][ttCol] += 10;
            Hero h = currentGrid.removeHeroFromGrid();
            targetGrid.setHeroInGrid(h);
            Box b = targetGrid.removeBoxFromGrid();
            ttargetGrid.setBoxInGrid(b);
            h.setRow(tRow);
            h.setCol(tCol);
            b.setRow(ttRow);
            b.setCol(ttCol);
            return true;
        }
        return false;
    }

    public void undo(){
        if(maps.size()>1){
            maps.subList(maps.size() - 1, maps.size()).clear();
            int[][] previous = MapMatrix.copyArray(maps.get(maps.size()-1));
            model.setMatrix(previous);
            view.ResetGamePanel();
        }
        else {
            Toast.displayToast("This has been the first step\nCan't undo anymore!",300);
            maps.clear();
            maps.add(MapMatrix.copyArray(model.getInitialMatrix()));
        }
    }



    public void startReplay() {
        // 创建一个新的回放窗口并传递当前的游戏模型和控制器
        replayWindow = new ReplayWindow(model, this);
        // 显示回放窗口
        replayWindow.setVisible(true);
    }

    public void checkwin() {
        boolean iswin = true;
        int[][] a = model.getMatrix();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                if (model.copyArray(a)[i][j] % 10 == 2 && a[i][j] != 12) {
                    iswin = false;
                }
            }
        }
        if (iswin && !haswon) {
            haswon = true;
            try {
                // 摘取对图片进行缩放操作
                // 加载原始图像文件
                File file = new File("resource/pictures/smile.png");
                BufferedImage originalImage = ImageIO.read(file);

                // 创建一个新的BufferedImage对象，具有所需的尺寸
                int width = 64;  // 新的宽度
                int height = 64; // 新的高度
                BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getTransparency());

                // 绘制原始图像到新的BufferedImage对象，实现缩放
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(originalImage, 0, 0, width, height, null);
                g2d.dispose();

                // 创建一个新的ImageIcon对象，使用调整大小后的图像
                ImageIcon smileyIcon = new ImageIcon(resizedImage);

                // 显示消息对话框，并使用自定义的笑脸图标
                String message = "Congratulation, you win!!!\nDo you want to enter the next level?";
                String title = "Victory";

                // 自定义按钮选项
                String[] options = {"No", "Yes"};

                //修改消息的字体
                Font customFont = new Font("Comic Sans MS", Font.BOLD, 16);
                JTextArea textArea = new JTextArea(message);
                textArea.setFont(customFont); // 设置字体为 Serif，粗体，大小为 16
                textArea.setEditable(false);  // 设置文本不可编辑
                textArea.setBackground(frame.getBackground()); // 可选：设置背景色与 JFrame 一致

                // 显示选项对话框，返回用户选择的按钮索引
                int choice = JOptionPane.showOptionDialog(
                        frame,
                        new JScrollPane(textArea),
                        title,
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        smileyIcon,
                        options,
                        options[0]
                );

                // 根据用户选择执行不同操作
                if (choice == 1) {
                   nextlevel();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void checklose() {
        boolean islose = false;
        int[][] a = model.getMatrix();
        int w = a.length;
        int h = a[0].length;

        // Case 1: Check boxes stuck in corners
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (a[i][j] == 10 || a[i][j] == 12) { // Box or box on target
                    // Check for corner traps
                    if ((a[i - 1][j] == 1 && a[i][j - 1] == 1) ||
                            (a[i + 1][j] == 1 && a[i][j + 1] == 1) ||
                            (a[i - 1][j] == 1 && a[i][j + 1] == 1) ||
                            (a[i][j - 1] == 1 && a[i + 1][j] == 1)) {
                        islose = true;
                    }

                }
            }
        }
        if (islose && !haslosed && !haswon) {
            handleLoseCondition();
        }
    }
    private void handleLoseCondition() {
        haslosed = true;
        try {
            // Load and resize the expression image
            File file = new File("resource/pictures/expression.png");
            BufferedImage originalImage = ImageIO.read(file);
            BufferedImage resizedImage = new BufferedImage(49, 49, originalImage.getTransparency());
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, 49, 49, null);
            g2d.dispose();

            ImageIcon expressionIcon = new ImageIcon(resizedImage);
            String message = "Box is in an unsolvable position!\nYou lost!";
            String title = "Game Over";

            Font customFont = new Font("Comic Sans MS", Font.BOLD, 16);
            JTextArea textArea = new JTextArea(message);
            textArea.setFont(customFont);
            textArea.setEditable(false);
            textArea.setBackground(frame.getBackground());

            String[] options = {"Continue", "Restart"};

            int choice = JOptionPane.showOptionDialog(
                    frame,
                    new JScrollPane(textArea),
                    title,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    expressionIcon,
                    options,
                    options[0]
            );

            if (choice == 1) {
                restartGame();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nextlevel() {
        int j = 1;
        for (int i = 0; i < levels.size(); i++) {
            if (Arrays.deepEquals(model.getInitialMatrix(), levels.get(i))) {
                j = i + 2;
                break;
            }
        }
        //关掉这个level:
        if(j<=levels.size()){
            restartGame();
            frame.dispose();
            //进行下一个level:
            String path = "resource/level/level" + j + ".txt";
            FrameController frameController = new FrameController();
            frameController.loadGame(path);
        }
        else {
                String message = "This has been the last level";
                String title = "The End";

                // 自定义按钮选项
                String[] options = {"Continue", "Return"};

                // 显示选项对话框，返回用户选择的按钮索引
                int choice = JOptionPane.showOptionDialog(
                        frame,
                        message,
                        title,
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                // 根据用户选择执行不同操作
                if (choice == 1) {
                    frame.dispose();
                }
        }
    }

    public void saveGame() {
        // 获取用户名和关卡名，用于生成文件名
        String username = frame.getFrameController().getUser();
        int level= frame.getFrameController().getLevel();

        // 检查 username 是否为 null 或空字符串，即是否为游客
        if (username == null || username.trim().isEmpty()) { //.trim().isEmpty()比.equals()更适合用户输入场景，能去掉两端空格后再判断
            System.out.println("Username is null or empty, cannot save game.");
            JOptionPane.showMessageDialog(frame, "Guest Mode user cannot save game.");
            return;
        }

        // 生成文件路径
        String path = String.format("resource/%s/level%d.txt", username,level);

        // 确保目录存在
        File directory = new File(path).getParentFile();
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); //创建父目录
            if (!created) {
                System.out.println("Failed to create directory: " + directory.getPath());
                JOptionPane.showMessageDialog(frame, "Failed to create directory for saving game.");
                return;
            }
        }
        //获取游戏数据
        int[][] map = model.getMatrix();
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < map[i].length; j++) {
                line.append(map[i][j]);
                if (j < map[i].length - 1) {
                    line.append(","); //用逗号隔开
                }
            }
            lines.add(line.toString());
        }
        //保存Steps
        Integer steps=view.getSteps();
        lines.add(steps.toString());

        // 计算文件的哈希值
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

            // 将哈希值添加到文件末尾
            lines.add(hashString.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to calculate hash for save file.");
            return;
        }

        // 写入文件
        try {
            fileUtil.writeFileFromList(path, lines);
            System.out.println("Game saved to file: " + path);
            JOptionPane.showMessageDialog(frame, "Game saved successfully!");
        } catch (Exception e) {
            System.out.println("Failed to save game: " + e.getMessage());
            JOptionPane.showMessageDialog(frame, "Failed to save game: " + e.getMessage());
        }
    }

    //todo: add other methods such as loadGame, saveGame...
}
