package top.zfmx.compression;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import top.zfmx.compression.impl.HuffmanCompression;
import top.zfmx.utils.ImageUtilsTest;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class HuffmanCompressionTest {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    private final Mat testImage = imread("D:\\project\\algorithm-java\\Image-compression-encryption\\src\\main\\resources\\test-img\\2.jpg");


    @Test
    public void encodeImage() {
        Compression compression = new HuffmanCompression();
        double[][][] data = ImageUtilsTest.mat2data(testImage);
        var compressImage = compression.encodeImage(data);
        assert compressImage != null;
        System.out.println("压缩后的图像：" + compressImage);
    }

}
