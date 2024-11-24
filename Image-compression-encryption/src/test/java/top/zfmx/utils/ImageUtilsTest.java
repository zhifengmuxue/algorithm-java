package top.zfmx.utils;

import org.junit.Test;
import org.junit.BeforeClass;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import static org.junit.Assert.*;

public class ImageUtilsTest {

    @BeforeClass
    public static void setup() {
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    public static double[][][] mat2data(Mat testImage) {
        return ImageUtils.mat2data(testImage);
    }

    @Test
    public void testMat2DataAndData2Mat() {
        Mat src = new Mat(3, 3, CvType.CV_8UC3);
        byte[] pixelData = {
                (byte) 10, (byte) 20, (byte) 30,
                (byte) 40, (byte) 50, (byte) 60,
                (byte) 70, (byte) 80, (byte) 90
        };
        src.put(0, 0, pixelData);

        double[][][] data = ImageUtils.mat2data(src);

        assertEquals(3, data.length);
        assertEquals(3, data[0].length);
        assertEquals(3, data[0][0].length);
        assertEquals(266.0, data[0][0][0], 0.01);
        assertEquals(276.0, data[0][0][1], 0.01);
        assertEquals(286.0, data[0][0][2], 0.01);

        Mat result = ImageUtils.data2mat(data);

        assertEquals(src.rows(), result.rows());
        assertEquals(src.cols(), result.cols());
        assertEquals(src.channels(), result.channels());


    }

    @Test(expected = IllegalArgumentException.class)
    public void testMat2DataEmptyMat() {
        Mat emptyMat = new Mat();
        ImageUtils.mat2data(emptyMat);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testData2MatEmptyArray() {
        double[][][] emptyData = new double[0][0][0];
        ImageUtils.data2mat(emptyData);
    }
}