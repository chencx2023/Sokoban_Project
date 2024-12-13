import controller.FrameController;
import view.level.LevelFrame;
import view.login.LoginFrame;
import view.login.LoginSelectionFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrameController frameController=new FrameController();
            LoginSelectionFrame loginSelectionFrame=new LoginSelectionFrame(280,280,frameController);
            loginSelectionFrame.setVisible(true);
            LoginFrame loginFrame = new LoginFrame(280,280,frameController);
            loginFrame.setVisible(false);
            loginSelectionFrame.setLoginFrame(loginFrame);
            LevelFrame levelFrame = new LevelFrame(650,200,frameController);
            levelFrame.setVisible(false);
            loginSelectionFrame.setLevelFrame(levelFrame);
            loginFrame.setLevelFrame(levelFrame);
        });
    }
}
