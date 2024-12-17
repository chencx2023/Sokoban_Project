package view.game;

import javax.swing.*;
import java.awt.*;

public class Hero extends JComponent {
    private int row;
    private int col;
    private Image heroImage;

    private final int value = 20;
    private static Color color = new Color(87, 171, 220);

    public Hero(int width, int height, int row, int col) {
        this.row = row;
        this.col = col;
        this.setSize(width, height);
        this.setLocation(8, 8);
        heroImage=new ImageIcon("resource/pictures/hero.png").getImage();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(heroImage,0,0,getWidth(),getHeight(),null);
    }

    public int getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
