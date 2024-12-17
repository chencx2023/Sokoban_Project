package controller;

import javax.swing.*;

public class Toast {

    public static void displayToast(String message, int duration) {
        // 创建一个JOptionPane对象，设置消息和图标
        JOptionPane.showMessageDialog(null, message, "Notice", JOptionPane.INFORMATION_MESSAGE);

        // 使JOptionPane显示指定的毫秒数
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
