package top.zfmx;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import top.zfmx.compression.Compression;
import top.zfmx.compression.impl.HuffmanCompression;
import top.zfmx.utils.ImageUtils;
import top.zfmx.utils.impl.ImageUtilsOpencv;

import java.util.Objects;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class StartByCV {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String imagePath = Objects.requireNonNull(StartByCV.class.getClassLoader().getResource("test-img/2.jpg")).getPath();
        imagePath = imagePath.substring(1);
        Mat image = imread(imagePath);
        ImageUtils<Mat> imageUtils = new ImageUtilsOpencv();
        // 获取图像的每个像素点的值
        double[][][] data = imageUtils.img2data(image);

        // 压缩图像
        Compression compression = new HuffmanCompression();
        var compressImage = compression.encodeImage(data);
        var compressBytes = compression.encodeToBytes(compressImage);

        // 计算大小、压缩率
        System.out.println("原始图像大小：" + image.total() * image.elemSize() + " 字节");
        System.out.println("压缩后的图像大小：" + compressBytes.length + " 字节");
        System.out.println("压缩率：" + (1 - (double) compressBytes.length / (image.total() * image.elemSize())) * 100 + "%");

        // 解压图像
        double[][][] decompressImage = compression.decodeFromBytes(compressBytes);

        Mat decompressMat = imageUtils.data2img(decompressImage);
        imageUtils.showImage(decompressMat);

    }
}