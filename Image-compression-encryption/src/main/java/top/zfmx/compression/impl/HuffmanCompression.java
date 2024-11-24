package top.zfmx.compression.impl;

import top.zfmx.compression.Compression;

import java.util.*;

public class HuffmanCompression implements Compression {
    // 存储哈夫曼编码表
    private final Map<Integer, String> huffmanCodes = new HashMap<>();

    /**
     * 哈夫曼树节点
     * 静态内部类
     */
    private static class HuffmanNode {
        int value;
        int frequency;
        HuffmanNode left;
        HuffmanNode right;

        HuffmanNode(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }
    }

    /**
     * 构建哈夫曼树
     * @param frequencies 频率数组
     */
    private void buildHuffmanTree(int[] frequencies) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.frequency));

        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                queue.offer(new HuffmanNode(i, frequencies[i]));
            }
        }

        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            HuffmanNode parent = null;
            if (right != null) {
                parent = new HuffmanNode(-1, left.frequency + right.frequency);
            }
            if (parent != null) {
                parent.left = left;
            }
            if (parent != null) {
                parent.right = right;
            }
            queue.offer(parent);
        }

        generateCodes(Objects.requireNonNull(queue.poll()), "");
    }

    /**
     * 生成哈夫曼编码
     * @param node 哈夫曼树节点
     * @param code 编码
     */
    private void generateCodes(HuffmanNode node, String code) {
        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.value, code);
            return;
        }
        if (node.left != null) generateCodes(node.left, code + "0");
        if (node.right != null) generateCodes(node.right, code + "1");
    }

    /**
     * 获取哈夫曼编码表
     * @param src 图像数据
     */
    private void getHuffmanTable(double[][][] src) {
        int[] frequencies = new int[512];
        int cols = src[0].length;
        int channels = src[0][0].length;

        for (double[][] doubles : src) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < channels; k++) {
                    int value = (int) doubles[j][k];
                    frequencies[value]++; // 处理偏移量
                }
            }
        }

        this.buildHuffmanTree(frequencies);
    }

    /**
     * 从哈夫曼编码表构建哈夫曼树
     * @return 哈夫曼树根节点
     */
    private HuffmanNode buildHuffmanTreeFromCodes() {
        HuffmanNode root = new HuffmanNode(-1, 0);

        for (Map.Entry<Integer, String> entry : huffmanCodes.entrySet()) {
            String code = entry.getValue();
            HuffmanNode currentNode = root;

            for (char bit : code.toCharArray()) {
                if (bit == '0') {
                    if (currentNode.left == null) {
                        currentNode.left = new HuffmanNode(-1, 0);
                    }
                    currentNode = currentNode.left;
                } else {
                    if (currentNode.right == null) {
                        currentNode.right = new HuffmanNode(-1, 0);
                    }
                    currentNode = currentNode.right;
                }
            }
            currentNode.value = entry.getKey(); // 设置叶子节点的值
        }

        return root;
    }

    /**
     * 将解码后的值转换为三维数组
     * @param decodedValues 解码后的值
     * @return 三维数组
     */
    private double[][][] convertTo3DArray(List<Double> decodedValues) {
        int channels = 3; // 假设3个通道
        int cols = (int) Math.sqrt((double) decodedValues.size() / channels); // 示例计算列数
        int rows = decodedValues.size() / (cols * channels);

        double[][][] result = new double[rows][cols][channels];
        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < channels; k++) {
                    result[i][j][k] = decodedValues.get(index++);
                }
            }
        }
        return result;
    }

    /**
     * 编码图像数据
     * @param src  图像数据
     * @return 编码后的二进制字符串
     */
    public String encodeImage(double[][][] src) {
        StringBuilder encodedData = new StringBuilder();
        int cols = src[0].length;
        int channels = src[0][0].length;

        getHuffmanTable(src);
        for (double[][] doubles : src) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < channels; k++) {
                    int value = (int) doubles[j][k];
                    String code = huffmanCodes.get(value); // 处理偏移量
                    if (code != null) {
                        encodedData.append(code);
                    }
                }
            }
        }
        return encodedData.toString();
    }

    /**
     * 编码图像数据
     * @param encodedData 编码后的二进制字符串
     * @return 编码后的字节数组
     */
    public byte[] encodeToBytes(String encodedData) {
        int byteLength = (encodedData.length() + 7) / 8; // 计算字节数组长度
        byte[] byteArray = new byte[byteLength];

        for (int i = 0; i < encodedData.length(); i++) {
            if (encodedData.charAt(i) == '1') {
                byteArray[i / 8] |= (byte) (1 << (7 - (i % 8))); // 设置位
            }
        }

        return byteArray;
    }

    /**
     * 解码图像数据
     * @param encodedData 编码后的二进制字符串
     * @return 解码后的图像数据
     */
    public double[][][] decodeImage(String encodedData) {
        List<Double> decodedValues = new ArrayList<>();
        HuffmanNode currentNode = buildHuffmanTreeFromCodes();

        for (char bit : encodedData.toCharArray()) {
            if (currentNode != null) {
                currentNode = (bit == '0') ? currentNode.left : currentNode.right;
            }

            if (currentNode != null && currentNode.left == null && currentNode.right == null) {
                decodedValues.add((double) currentNode.value);
                currentNode = buildHuffmanTreeFromCodes(); // 重置当前节点
            }
        }

        return convertTo3DArray(decodedValues);
    }

    /**
     * 将字节数组解码为图像数据
     * @param byteArray 字节数组
     * @return 解码后的图像数据
     */
    public double[][][] decodeFromBytes(byte[] byteArray) {
        StringBuilder encodedData = new StringBuilder();

        for (byte b : byteArray) {
            for (int i = 7; i >= 0; i--) {
                encodedData.append((b >> i) & 1); // 提取每一位
            }
        }

        return decodeImage(encodedData.toString());
    }
}