package controller;

import controller.GameController;
import model.MapMatrix;
import util.SoundEffect;
import view.game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ReplayWindow extends JFrame {
    private GamePanel gamePanel;
    private final GameController gameController;
    private Timer replayTimer;
    private int currentPosition;
    private JSlider replaySlider;
    private boolean isPlaying;
    private int replaySpeed = 800;
    private JLabel positionLabel;
    private JLabel timeLabel;
    private JButton playPauseButton;
    private SoundEffect stepSound;
    private List<Integer> timeRecords;
    private boolean isTimermode=false;
    private int timelimit=-1;

    public ReplayWindow(MapMatrix model, GameController gameController) {
        this.gameController = gameController;
        this.currentPosition = 0;
        this.isPlaying = false;
        isTimermode=model.isTimerMode();
        timelimit=model.getTimeLimit();
        if(isTimermode){
            this.timeRecords = gameController.getTimeRecords();
        }
        stepSound=new SoundEffect("resource/music/move.wav");

        initializeWindow();
        MapMatrix m1 =new MapMatrix(gameController.getMaps().get(0),isTimermode,timelimit);
        initializeGamePanel(m1);
        initializeControls();
    }

    private void initializeWindow() {
        setTitle("Game Replay");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void initializeGamePanel(MapMatrix model) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gamePanel = new GamePanel(model);
        gamePanel.setSize(model.getWidth() * 50, model.getHeight() * 50);
        gamePanel.setLayout(null);
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        positionLabel = new JLabel("Position: 0");
        positionLabel.setFont(new Font("serif", Font.ITALIC, 22));
        positionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if(isTimermode){
            timeLabel = new JLabel("Time: 0s");
            timeLabel.setFont(new Font("serif", Font.ITALIC, 22));
            timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            infoPanel.add(timeLabel);
        }


        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(positionLabel);


        mainPanel.add(infoPanel, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void initializeControls() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        replaySlider = new JSlider(0, gameController.getMaps().size() - 1, 0);
        replaySlider.setMajorTickSpacing(1);
        replaySlider.setPaintTicks(true);
        replaySlider.setPaintLabels(true);
        replaySlider.addChangeListener(e -> {
            currentPosition = replaySlider.getValue();
            updateToPosition(currentPosition);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        playPauseButton = new JButton("Play");
        playPauseButton.addActionListener(e -> togglePlayPause());

        JButton fastForwardButton = new JButton("Fast Forward");
        fastForwardButton.addActionListener(e -> setReplaySpeed(100));

        JButton normalSpeedButton = new JButton("Normal Speed");
        normalSpeedButton.addActionListener(e -> setReplaySpeed(500));

        JButton returnButton = new JButton("Return to Game");
        returnButton.addActionListener(e -> returnToGame());

        buttonPanel.add(playPauseButton);
        buttonPanel.add(fastForwardButton);
        buttonPanel.add(normalSpeedButton);
        buttonPanel.add(returnButton);

        controlPanel.add(replaySlider);
        controlPanel.add(Box.createVerticalStrut(5));
        controlPanel.add(buttonPanel);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void togglePlayPause() {
        if (isPlaying) {
            stopReplay();
            playPauseButton.setText("Play");
        } else {
            if (currentPosition >= gameController.getMaps().size() - 1) {
                currentPosition = 0;
                replaySlider.setValue(0);
                updateToPosition(0);
            }
            startReplay();
            playPauseButton.setText("Pause");
        }
        isPlaying = !isPlaying;  // Move this line here to properly toggle the state
    }

    private void startReplay() {

        if (replayTimer != null) {
            replayTimer.stop();
        }
        replayTimer = new Timer(replaySpeed, e -> {
            if (currentPosition < gameController.getMaps().size() - 1) {
                stepSound.play();
                currentPosition++;
                updateToPosition(currentPosition);
                replaySlider.setValue(currentPosition);
            }
            else {
                stopReplay();
                playPauseButton.setText("Play");
                isPlaying = false;  // Add this line to ensure state is updated
            }
        });
        replayTimer.start();
    }

    private void stopReplay() {
        if (replayTimer != null) {
            replayTimer.stop();
        }
    }

    private void updateToPosition(int position) {
        if (position >= 0 && position < gameController.getMaps().size()) {
            MapMatrix currentState = new MapMatrix(gameController.getMaps().get(position),isTimermode,timelimit);
            gamePanel.setModel(currentState);
            gamePanel.updateview();
            positionLabel.setText(String.format("Position: %d", position));
            if(isTimermode){
                int timeAtPosition = timeRecords.get(position);
                timeLabel.setText(String.format("Time: %ds", timeAtPosition));
            }
        }
    }

    private void setReplaySpeed(int speed) {
        this.replaySpeed = speed;
        if (replayTimer != null && isPlaying) {
            replayTimer.setDelay(speed);
        }
    }

    private void returnToGame() {
        stopReplay();
        dispose();

        java.util.List<int[][]> newMaps = new ArrayList<>();
        List<Integer> newTimeRecords = new ArrayList<>();
        for (int i = 0; i <= currentPosition; i++) {
            newMaps.add(MapMatrix.copyArray(gameController.getMaps().get(i)));
            if(isTimermode){
                newTimeRecords.add(timeRecords.get(i));
            }

        }

        // 更新游戏控制器中的状态
        gameController.getMaps().clear();
        gameController.getMaps().addAll(newMaps);
        if(isTimermode){
            gameController.getTimeRecords().clear();
            gameController.getTimeRecords().addAll(newTimeRecords);
            // 恢复时间和游戏状态
            int timeAtPosition = timeRecords.get(currentPosition);
            gameController.getFrame().setSeconds(timeAtPosition);
            gameController.getFrame().updateTimeLabel();
            gameController.getFrame().resumeTimer();
        }
        // 更新游戏面板
        gameController.startGameFromCurrentModel(gamePanel.getModel(), currentPosition);
    }
}