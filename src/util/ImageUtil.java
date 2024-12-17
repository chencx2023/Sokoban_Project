package util;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageUtil {

    /**
     * 读取图片并缩放
     * @param inputPath 输入图片路径
     * @param outputPath 输出图片路径
     * @param width 缩放后的宽度
     * @param height 缩放后的高度
     */
    public static void scaleImage(String inputPath, String outputPath, int width, int height) {
        try {
            // 读取图片
            File inputFile = new File(inputPath);
            BufferedImage inputImage = ImageIO.read(inputFile);

            // 创建缩放后的图片
            BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());

            // 缩放图片
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, width, height, null);
            g2d.dispose(); // 释放资源

            // 写入到输出文件
            File outputFile = new File(outputPath);
            ImageIO.write(outputImage, "jpg", outputFile);

            System.out.println("Image scaled successfully: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // 输入文件路径（修改为实际路径）
        String inputPath = "resource/.png";

        // 输出文件路径
        String outputPath = "resource/.png";

        // 缩放后的宽度和高度
        int scaledWidth = 64; // 缩放到宽度300
        int scaledHeight = 64; // 缩放到高度200

        // 调用方法进行缩放
        scaleImage(inputPath, outputPath, scaledWidth, scaledHeight);
    }

}

