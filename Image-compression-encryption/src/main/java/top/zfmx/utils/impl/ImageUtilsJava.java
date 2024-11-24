package top.zfmx.utils.impl;

import top.zfmx.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtilsJava implements ImageUtils<BufferedImage> {
    @Override
    public double[][][] img2data(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();
        double[][][] data = new double[height][width][3]; // RGB

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = src.getRGB(x, y);
                data[y][x][0] = (rgb >> 16) & 0xff ;
                data[y][x][1] = (rgb >> 8) & 0xff ;
                data[y][x][2] = rgb & 0xff;
                data[y][x][0] += 256;
                data[y][x][1] += 256;
                data[y][x][2] += 256;
            }
        }
        return data;
    }

    @Override
    public BufferedImage data2img(double[][][] data) {
        int height = data.length;
        int width = data[0].length;
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = (int) data[y][x][0] - 256;
                int g = (int) data[y][x][1] - 256;
                int b = (int) data[y][x][2] - 256;
                int rgb = (r << 16) | (g << 8) | b;
                result.setRGB(x, y, rgb);
            }
        }
        return result;
    }

    @Override
    public void showImage(BufferedImage src) {
        // 创建 JFrame
        JFrame frame = new JFrame("显示图像");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(src.getWidth(), src.getHeight());

        // 将图像放入 JLabel
        JLabel label = new JLabel(new ImageIcon(src));
        frame.getContentPane().add(label, BorderLayout.CENTER);

        // 显示窗口
        frame.setVisible(true);
    }
}
