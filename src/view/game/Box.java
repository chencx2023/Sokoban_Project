package view.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class Box extends JComponent {
    private final int value = 10;
    private int row;
    private int col;
    private Image boxImage;

    public Box(int width, int height, int row, int col) {
        this.col = col;
        this.row = row;
        this.setSize(width, height);
        this.setLocation(5, 5);
        boxImage=new ImageIcon("resource/pictures/box.png").getImage();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(boxImage,0,0,getWidth(),getHeight(),null);
        Border border = BorderFactory.createLineBorder(Color.black, 1);
        this.setBorder(border);
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getValue() {
        return value;
    }
}
