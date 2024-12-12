import view.level.LevelFrame;
import view.login.LoginFrame;
import view.login.LoginSelectionFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginSelectionFrame loginSelectionFrame=new LoginSelectionFrame(280,280);
            loginSelectionFrame.setVisible(true);
            LoginFrame loginFrame = new LoginFrame(280,280);
            loginFrame.setVisible(false);
            loginSelectionFrame.setLoginFrame(loginFrame);
            LevelFrame levelFrame = new LevelFrame(650,200);
            levelFrame.setVisible(false);
            loginSelectionFrame.setLevelFrame(levelFrame);
            loginFrame.setLevelFrame(levelFrame);
        });
    }
}
