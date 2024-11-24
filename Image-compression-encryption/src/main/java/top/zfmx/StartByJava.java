package top.zfmx;

import top.zfmx.compression.Compression;
import top.zfmx.compression.impl.HuffmanCompression;
import top.zfmx.utils.ImageUtils;
import top.zfmx.utils.impl.ImageUtilsJava;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class StartByJava {
    private final static ImageUtils<BufferedImage> imageUtils = new ImageUtilsJava();
    public static void main(String[] args) {
        String imagePath = Objects.requireNonNull(StartByJava.class.getClassLoader().
                getResource("test-img/2.jpg")).getPath().substring(1);
        BufferedImage image;

        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException("读取图像失败");
        }
        double[][][] data = imageUtils.img2data(image);

        // 压缩图像
        Compression compression = new HuffmanCompression();
        var compressImage = compression.encodeImage(data);
        System.out.println("压缩后数据" + compressImage);
        var compressBytes = compression.encodeToBytes(compressImage);

        // 计算大小、压缩率
        System.out.println("原始图像大小：" + data.length * data[0].length * 3 + " 字节");
        System.out.println("压缩后的图像大小：" + compressBytes.length + " 字节");
        System.out.println("压缩率：" + (1 - (double) compressBytes.length / (data.length * data[0].length * 3)) * 100 + "%");

        // 解压图像
        double[][][] decompressImage = compression.decodeFromBytes(compressBytes);


        BufferedImage bufferedImage = imageUtils.data2img(decompressImage);
        imageUtils.showImage(bufferedImage);

    }


}