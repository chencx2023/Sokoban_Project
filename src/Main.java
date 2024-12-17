import controller.FrameController;
import view.level.LevelFrame;
import view.login.LoginFrame;
import view.login.LoginSelectionFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //创建FrameController实例
            FrameController frameController=new FrameController();

            LoginSelectionFrame loginSelectionFrame=new LoginSelectionFrame(350,280,frameController);
            LoginFrame loginFrame = new LoginFrame(280,280,frameController);
            LevelFrame levelFrame = new LevelFrame(650,200,frameController);

            //将Frame和FrameController互相绑定
            frameController.setLoginSelectionFrame(loginSelectionFrame);
            frameController.setLoginFrame(loginFrame);
            frameController.setLevelFrame(levelFrame);

            frameController.showLoginSelectionFrame();
        });
    }
}
