import controller.FrameController;
import util.MP3Player;
import util.SoundEffect;
import view.level.LevelFrame;
import view.login.LoginFrame;
import view.login.LoginSelectionFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //创建FrameController实例
            FrameController frameController=new FrameController();

            LoginSelectionFrame loginSelectionFrame=new LoginSelectionFrame(330,250,frameController);
            LoginFrame loginFrame = new LoginFrame(280,280,frameController);
            LevelFrame levelFrame = new LevelFrame(300,600,frameController);

            //将Frame和FrameController互相绑定
            frameController.setLoginSelectionFrame(loginSelectionFrame);
            frameController.setLoginFrame(loginFrame);
            frameController.setLevelFrame(levelFrame);

            frameController.showLoginSelectionFrame();

            MP3Player backgroundMusic=new MP3Player("resource/music/background_music.mp3");
            backgroundMusic.play();
            backgroundMusic.setVolume(0.1f);

        });
    }
}
