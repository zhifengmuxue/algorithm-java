package top.zfmx.utils;

public interface ImageUtils<T> {
    double[][][] img2data(T src);
    T data2img(double[][][] data);
    void showImage(T src);
}
