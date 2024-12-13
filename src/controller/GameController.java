package controller;

import model.Direction;
import model.MapMatrix;
import view.game.GamePanel;
import view.game.GridComponent;
import view.game.Hero;
import view.game.Box;
import view.game.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;//gamepanel是final的
    private final MapMatrix model;
    private final GameFrame frame;
    private boolean haswon = false;
    private boolean haslosed = false;


    public GameController(GameFrame frame, GamePanel view, MapMatrix model) {
        this.frame = frame;
        this.view = view;
        this.model = model;
        view.setController(this);
    }

    public void restartGame() {
        //ToDo: reset model & view
        System.out.println("Do restart game here");
        haswon = true;
        haslosed = false;
        this.model.resetMapMatrix();
        this.view.restartGame();
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
                File file = new File("resource/smiling-face-with-smiling-eyes-emoji-clipart-sm.png");
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
                JOptionPane.showMessageDialog(frame, "You Win!", "Victory", JOptionPane.INFORMATION_MESSAGE, smileyIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void checklose(){    //只考虑箱子是否无法移动的情况
        boolean islose = false;
        int[][] a = model.getMatrix();
        int w = a.length;
        int h = a[0].length;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (a[i][j] == 10){ //此处是箱子
                    //如果箱子临近有两个边界
                    if((a[i-1][j] ==1 && a[i][j-1]==1) || (a[i+1][j] == 1 && a[i][j+1] == 1) || (a[i-1][j] == 1 && a[i][j+1] == 1) || (a[i][j-1] == 1 && a[i+1][j] == 1)){
                        islose = true;
                    }
                }
            }
        }

        if (islose && !haslosed && !haswon){
            haslosed = true;
            try {
                // 摘取对图片进行缩放操作
                // 加载原始图像文件
                File file = new File("resource/pngtree-cry-face-emoji-emoticon-expression-png-image_5246498.png");
                BufferedImage originalImage = ImageIO.read(file);

                // 创建一个新的BufferedImage对象，具有所需的尺寸
                int width = 49;  // 新的宽度
                int height = 49; // 新的高度
                BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getTransparency());

                // 绘制原始图像到新的BufferedImage对象，实现缩放
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(originalImage, 0, 0, width, height, null);
                g2d.dispose();

                // 创建一个新的ImageIcon对象，使用调整大小后的图像
                ImageIcon expressionIcon = new ImageIcon(resizedImage);

                // 显示消息对话框，并使用自定义的笑脸图标

                String message = "Box can't be moved,You losed!";
                String title = "Game Over";

                // 自定义按钮选项
                String[] options = {"Continue", "Restart"};

                // 显示选项对话框，返回用户选择的按钮索引
                int choice = JOptionPane.showOptionDialog(
                        frame,
                        message,
                        title,
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        expressionIcon,
                        options,
                        options[0] // 默认选中 "Continue"
                );

                // 根据用户选择执行不同操作
                if (choice == 1) {
                    restartGame(); // 调用重新开始的方法
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveGame(){
        String username=frame.getFrameController().getUser();
        
    }
    //todo: add other methods such as loadGame, saveGame...
}
