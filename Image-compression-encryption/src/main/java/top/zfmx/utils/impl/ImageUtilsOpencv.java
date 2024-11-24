package top.zfmx.utils.impl;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import top.zfmx.utils.ImageUtils;

public class ImageUtilsOpencv implements ImageUtils<Mat> {
    /**
     * 获取图像的每个像素点的值，并将其转换为三维数组
     * 偏移量256
     * @param src Mat对象
     * @return data[][][] 三维数组，第一维是行，第二维是列，第三维通道
     */
    @Override
    public double[][][] img2data(Mat src){
        if (src == null || src.empty()) {
            throw new IllegalArgumentException("输入的 Mat 不能为 null 或空");
        }
        int rows = src.rows();
        int cols = src.cols();
        double[][][] data = new double[rows][cols][];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double[] temp = src.get(i, j);
                data[i][j] = new double[temp.length];
                for (int k = 0; k < temp.length; k++) {
                    data[i][j][k] = temp[k]+256;
                }
            }
        }
        return data;
    }

    /**
     * 将三维数组转换为 Mat 对象
     * @param data 三维数组，第一维是行，第二维是列，第三维通道
     * @return Mat对象
     */
    @Override
    public Mat data2img(double[][][] data) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("输入的三维数组不能为 null 或空");
        }

        int rows = data.length;
        int cols = data[0].length;
        int channels = data[0][0].length;

        // 创建一个新的 Mat 对象，类型根据通道数设置
        Mat result = new Mat(rows, cols, channels == 1 ? CvType.CV_8UC1 : CvType.CV_8UC3);

        // 遍历三维数组，填充 Mat 对象
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double[] pixel = new double[channels];
                for (int k = 0; k < channels; k++) {
                    pixel[k] = data[i][j][k] - 256; // 恢复原始像素值
                }
                result.put(i, j, pixel); // 将恢复的像素值放入 Mat 对象
            }
        }
        return result;
    }

    @Override
    public void showImage(Mat src){
        HighGui.imshow("解压后的图像", src);
        HighGui.waitKey(0);
    }
}
