package top.zfmx;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import top.zfmx.compression.Compression;
import top.zfmx.compression.impl.HuffmanCompression;
import top.zfmx.utils.ImageUtils;

import java.util.Objects;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String imagePath = Objects.requireNonNull(Main.class.getClassLoader().getResource("test-img/2.jpg")).getPath();
        imagePath = imagePath.substring(1);
        Mat image = imread(imagePath);
        // 获取图像的每个像素点的值
        double[][][] data = ImageUtils.mat2data(image);

        // 压缩图像
        Compression compression = new HuffmanCompression();
        var compressImage = compression.encodeImage(data);
        var compressBytes = compression.encodeToBytes(compressImage);

        // 解压图像
        double[][][] decompressImage = compression.decodeFromBytes(compressBytes);
        Mat decompressMat = ImageUtils.data2mat(decompressImage);
        HighGui.imshow("解压后的图像", decompressMat);
        HighGui.waitKey(0);

        // 计算大小、压缩率
        System.out.println("原始图像大小：" + image.total() * image.elemSize() + " 字节");
        System.out.println("压缩后的图像大小：" + compressBytes.length + " 字节");
        System.out.println("压缩率：" + (1 - (double) compressBytes.length / (image.total() * image.elemSize())) * 100 + "%");

        // 释放资源
        image.release();
    }
}