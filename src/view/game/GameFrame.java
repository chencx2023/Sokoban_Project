package view.game;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.FrameController;
import controller.GameController;
import model.MapMatrix;
import view.FrameUtil;
import view.level.LevelFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {

    private GameController controller;
    private FrameController frameController;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton returnBtn;
    private JButton saveBtn;
    private JButton undoBtn;
    private JButton replayBtn;
    private JButton upBtn;
    private JButton downBtn;
    private JButton leftBtn;
    private JButton rightBtn;

    private JLabel stepLabel;
    private JLabel trailLabel;
    private JLabel levelLabel;
    private GamePanel gamePanel;
    private Clip clickSound;

    private Timer timer;
    private int seconds=0;
    private JLabel timeLabel;

    //限时模式
    private int timeLimit ;
    private boolean timerPaused = false;
    public GameFrame(int width, int height, MapMatrix mapMatrix, FrameController frameController) {
        this.frameController = frameController;
        this.setTitle("2024 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapMatrix);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2 - 100);
        this.add(gamePanel);
        this.controller = new GameController(this, gamePanel, mapMatrix);
        this.frameController = frameController;
        timeLimit =mapMatrix.getTimeLimit();

        System.out.println("GameFrame: Username = " + frameController.getUser());

        initializeAudio();

        Font buttonFont = new Font("Tahoma", Font.BOLD, 15);
        Font buttonFont1 = new Font("Tahoma", Font.BOLD, 25);

        // Initialize all buttons with consistent styling
        this.restartBtn = createStyledButton("Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50, buttonFont);
        this.loadBtn = createStyledButton("Load", new Point(gamePanel.getWidth() + 80, 190), 80, 50, buttonFont);
        this.returnBtn = createStyledButton("Return", new Point(gamePanel.getWidth() + 80, 260), 80, 50, buttonFont);
        this.undoBtn = createStyledButton("Undo", new Point(gamePanel.getWidth() + 80, 330), 80, 50, buttonFont);
        this.replayBtn = createStyledButton("Replay", new Point(gamePanel.getWidth() + 80, 400), 80, 50, buttonFont);
        this.saveBtn = createStyledButton("Save", new Point(gamePanel.getWidth() + 80, 470), 80, 50, buttonFont);

        int buttonSize = 70;
        int centerX =gamePanel.getX() + gamePanel.getWidth() / 2;
        int startY = gamePanel.getY() + gamePanel.getHeight() + 20;


        this.upBtn = createStyledButton("↑", new Point(centerX, startY), buttonSize, buttonSize, buttonFont1);
        this.downBtn = createStyledButton("↓", new Point(centerX, startY + buttonSize), buttonSize, buttonSize, buttonFont1);
        this.leftBtn = createStyledButton("←", new Point(centerX - buttonSize, startY + buttonSize), buttonSize, buttonSize, buttonFont1);
        this.rightBtn = createStyledButton("→", new Point(centerX + buttonSize, startY + buttonSize), buttonSize, buttonSize, buttonFont1);

        this.stepLabel = FrameUtil.createJLabel(this, "Step:0", new Font("serif", Font.ITALIC, 22),
                new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        this.trailLabel = FrameUtil.createJLabel(this, "Trail", new Font("serif", Font.ITALIC, 22),
                new Point(gamePanel.getWidth() + 80, 550), 180, 50);
        this.levelLabel=FrameUtil.createJLabel(this, "Level "+frameController.getLevel(), new Font("serif", Font.ITALIC, 22),
                new Point(gamePanel.getWidth() + 80, 20), 180, 50);

        gamePanel.setStepLabel(stepLabel);
        gamePanel.setTrailLabel(trailLabel);
        gamePanel.setLevelLabel(levelLabel);

        if (mapMatrix.isTimerMode()) {
            initializeTimerComponents(mapMatrix.getTimeLimit());
        }

        addButtonListeners();

        initGameLoop();

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void initializeTimerComponents(int timeLimit) {
        this.timeLabel=FrameUtil.createJLabel(this, "Time:0", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 600), 180, 50);
        timer=new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!timerPaused){
                    seconds++;
                    updateTimeLabel();

                    String username=frameController.getUser();
                    if (!(username == null || username.trim().isEmpty())){
                        if(seconds%10==0){
                            controller.saveGame(true);
                            System.out.println("Auto-save successful!");
                            gamePanel.requestFocusInWindow();
                        }
                    }

                    if (seconds >= timeLimit) {
                        timer.stop();
                        JOptionPane.showMessageDialog(null, "Time's up! Game Over.");
                        controller.restartGame();
                        seconds = 0;
                        updateTimeLabel();
                        timer.start();
                        gamePanel.requestFocusInWindow();//enable key listener
                    }
                }
            }
        });
        timer.start();
    }

    public void pauseTimer(){
        timerPaused = true;
    }
    public void resumeTimer(){
        timerPaused = false;
    }

    public void decrementTime(int amount) {
        seconds = Math.max(0, seconds - amount);
        updateTimeLabel();
    }

    private JButton createStyledButton(String text, Point location, int width, int height, Font font) {
        JButton button = new JButton(text);
        button.setBounds(location.x, location.y, width, height);
        button.setFont(font);
        styleButton(button);
        this.add(button);
        return button;
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(255, 223, 186));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 200, 150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 223, 186));
            }
        });
    }

    private void initializeAudio() {
        try {
            File soundFile = new File("resource/music/click_sound.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clickSound = AudioSystem.getClip();
            clickSound.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading audio: " + e.getMessage());
        }
    }

    private void playClickSound() {
        if (clickSound != null) {
            clickSound.setFramePosition(0);
            clickSound.start();
        }
    }

    private void addButtonListeners() {
        this.restartBtn.addActionListener(e -> {
            playClickSound();
            controller.restartGame();
            gamePanel.requestFocusInWindow();
        });

        this.loadBtn.addActionListener(e -> {
            playClickSound();
            String user = frameController.getUser();
            if (user == null || user.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Guest Mode user cannot load game.");
                return;
            }
            String path = String.format("resource/%s/level%d.txt", user, frameController.getLevel());
            frameController.loadGame1(path, this);
            System.out.println(path);
        });

        this.undoBtn.addActionListener(e -> {
            playClickSound();
            controller.undo();
            decrementTime(1);
            gamePanel.requestFocusInWindow();
        });

        this.replayBtn.addActionListener(e -> {
            playClickSound();
            pauseTimer();
            controller.startReplay();
            gamePanel.requestFocusInWindow();
        });

        this.returnBtn.addActionListener(e -> {
            playClickSound();
            LevelFrame levelFrame = frameController.getLevelFrame();
            LevelFrame.getFrameController().returnLevelFrame(this);
            gamePanel.requestFocusInWindow();
        });

        this.saveBtn.addActionListener(e -> {
            playClickSound();
            controller.saveGame(false);
            System.out.println("Save successfully!");
            gamePanel.requestFocusInWindow();
        });

        this.upBtn.addActionListener(e -> {
            gamePanel.doMoveUp();
            gamePanel.requestFocusInWindow();
        });

        this.downBtn.addActionListener(e -> {
            gamePanel.doMoveDown();
            gamePanel.requestFocusInWindow();
        });

        this.leftBtn.addActionListener(e -> {
            gamePanel.doMoveLeft();
            gamePanel.requestFocusInWindow();
        });

        this.rightBtn.addActionListener(e -> {
            gamePanel.doMoveRight();
            gamePanel.requestFocusInWindow();
        });
    }

    public void updateStepLabel() {
        int steps = gamePanel.getSteps();
        String stepText = String.format("Steps: %d", steps);
        stepLabel.setText(stepText);
    }

    private void initGameLoop() {
        new Timer(1000 / 60, e -> {
            controller.checkwin();
            controller.checklose();
        }).start();
    }

    public void dispose() {
        if(timer!=null){
            timer.stop();
        }
        if (clickSound != null) {
            clickSound.close();
        }
        super.dispose();
    }

    public void setTimeLabel(int time){
        timeLabel.setText("Time:0");
    }

    //更新计时器标签
    public void updateTimeLabel(){
        timeLabel.setText("Time:"+seconds+"/"+timeLimit);
    }
    //停止计时器
    public void stopTimer(){
        timer.stop();
    }
    public Timer getTimer() {
        return timer;
    }
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
    public int getSeconds() {
        return seconds;
    }


    public FrameController getFrameController() {
        return frameController;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

}