package top.zfmx.compression;

import java.util.Map;

public interface Compression {
    String encodeImage(double[][][] src);
    byte[] encodeToBytes(String encodedData);
    double[][][] decodeImage(String encodedData);
    double[][][] decodeFromBytes(byte[] byteArray);
}
