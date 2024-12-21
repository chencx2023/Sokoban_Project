package view.game;

import controller.GameController;
import model.Direction;
import model.MapMatrix;
import util.SoundEffect;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;

/**
 * It is the subclass of ListenerPanel, so that it should implement those four methods: do move left, up, down ,right.
 * The class contains a grids, which is the corresponding GUI view of the matrix variable in MapMatrix.
 */
public class GamePanel extends ListenerPanel {
    private GridComponent[][] grids;
    private MapMatrix model;
    private SoundEffect stepSound;

    public MapMatrix getModel() {
        return model;
    }

    public void setModel(MapMatrix model) {
        this.model = model;
    }

    private GameController controller;
    private JLabel stepLabel;
    private JLabel trailLabel;

    private int steps;
    private int trail = 0;
    private final int GRID_SIZE = 50;
    private Hero hero;

    public GamePanel(MapMatrix model) {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setSize(model.getWidth() * GRID_SIZE + 4, model.getHeight() * GRID_SIZE + 4);
        this.model = model;
        this.grids = new GridComponent[model.getHeight()][model.getWidth()];
        stepSound=new SoundEffect("resource/music/move.wav");
        initialGame();
    }
    public JLabel getTrailLabel() {
        return trailLabel;
    }

    public void setTrailLabel(JLabel trailLabel) {
        this.trailLabel = trailLabel;
    }

    public void initialGame() {
        this.steps = 0;
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                //Units digit maps to id attribute in GridComponent. (The no change value)
                grids[i][j] = new GridComponent(i, j, model.getId(i, j) % 10, this.GRID_SIZE);
                grids[i][j].setLocation(j * GRID_SIZE + 2, i * GRID_SIZE + 2);
                //Ten digit maps to Box or Hero in corresponding location in the GridComponent. (Changed value)
                switch (model.getId(i, j) / 10) {
                    case 1:
                        grids[i][j].setBoxInGrid(new Box(GRID_SIZE - 10, GRID_SIZE - 10,i,j));
                        break;
                    case 2:
                        this.hero = new Hero(GRID_SIZE - 16, GRID_SIZE - 16, i, j);
                        grids[i][j].setHeroInGrid(hero);
                        break;
                }
                this.add(grids[i][j]);
            }
        }
        this.repaint();
    }

    //继承了listenerpanel，能监视键盘的活动
    //把domove设置成boolean就是判定是否能执行这种操作，本质上还是在domove
    @Override
    public void doMoveRight() {
        System.out.println("Click VK_RIGHT");
        if (controller.doMove(hero.getRow(), hero.getCol(), Direction.RIGHT)) {
            stepSound.play();
            this.afterMove();
        }
    }

    @Override
    public void doMoveLeft() {
        System.out.println("Click VK_LEFT");
        if(controller.doMove(hero.getRow(), hero.getCol(), Direction.LEFT)){
            stepSound.play();
            this.afterMove();
        }
    }

    @Override
    public void doMoveUp() {
        System.out.println("Click VK_Up");
       if( controller.doMove(hero.getRow(), hero.getCol(), Direction.UP)){
           stepSound.play();
           this.afterMove();
       }
    }

    @Override
    public void doMoveDown() {
        System.out.println("Click VK_DOWN");
        if(controller.doMove(hero.getRow(), hero.getCol(), Direction.DOWN)){
            stepSound.play();
            this.afterMove();
        }
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void afterMove() {
        this.steps++;
        int[][] copy_map = MapMatrix.copyArray(model.getMatrix());
        controller.getMaps().add(copy_map);
        this.stepLabel.setText(String.format("Step: %d", this.steps));
    }


    public JLabel getStepLabel() {
        return stepLabel;
    }

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }


    public void setController(GameController controller) {
        this.controller = controller;
    }

    public GridComponent getGridComponent(int row, int col) {
        return grids[row][col];
    }

    public void restartGame(){
        //ToDo: reset step & GridComponents
        //reset steps
        this.setSteps(0);
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        //reset gridComponents
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                //remove box and hero
                if(grids[i][j].getHero()!=null){
                    grids[i][j].removeHeroFromGrid();
                }
                if(grids[i][j].getBox()!=null){
                    grids[i][j].removeBoxFromGrid();
                }
                //add box & hero to their initial grid
                switch (model.getId(i,j)/10){
                    case 1:
                        grids[i][j].setBoxInGrid(new Box(GRID_SIZE - 10, GRID_SIZE - 10,i,j));
                        break;
                    case 2:
                        this.hero = new Hero(GRID_SIZE - 16, GRID_SIZE - 16, i, j);
                        grids[i][j].setHeroInGrid(hero);
                        break;
                }
            }
        }
        trail++;
        this.afterRestart();
        this.repaint();
    }

    public void updateview(){
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                //remove box and hero
                if(grids[i][j].getHero()!=null){
                    grids[i][j].removeHeroFromGrid();
                }
                if(grids[i][j].getBox()!=null){
                    grids[i][j].removeBoxFromGrid();
                }
                //add box & hero to their initial grid
                switch (model.getId(i,j)/10){
                    case 1:
                        grids[i][j].setBoxInGrid(new Box(GRID_SIZE - 10, GRID_SIZE - 10,i,j));
                        break;
                    case 2:
                        this.hero = new Hero(GRID_SIZE - 16, GRID_SIZE - 16, i, j);
                        grids[i][j].setHeroInGrid(hero);
                        break;
                }
            }
        }
        this.repaint();
    }

    public void ResetGamePanel(){
        updateview();
        this.steps -= 1;
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        this.repaint();
    }

    public void afterRestart() {
        this.trailLabel.setText(String.format("Trail: %d", this.trail));
    }


}
