
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工具类
 */
public final class Util {

    /**
     * 计算点积
     * @param xs 矩阵1
     * @param ys 矩阵2
     * @return 点积
     */
    public static double dotProduct(double[] xs, double[] ys){
        double result = 0.0;
        for(int i = 0; i < xs.length; i++){
            result += xs[i] * ys[i];
        }
        return result;
    }

    /**
     * 计算sigmoid函数
     * @param x 输入
     * @return sigmoid函数值
     */
    public static double sigmoid(double x){
        return 1.0 / (1.0 + Math.exp(-x));
    }

    /**
     * 计算sigmoid函数的导数
     * @param x 输入
     * @return sigmoid函数的导数值
     */
    public static double derivativeSigmoid(double x){
        return sigmoid(x) * (1 - sigmoid(x));
    }

    /**
     * 数据归一化
     * @param dataset 数据集
     */
    public static void normalizeByFeatureScaling(List<double[]> dataset){
        for(int colNum = 0; colNum < dataset.getFirst().length; colNum++){
            List<Double> column = new ArrayList<>();
            for(double[] row : dataset){
                column.add(row[colNum]);
            }
            double maximum = Collections.max(column);
            double minimum = Collections.min(column);
            double difference = maximum - minimum;
            for(double[] row : dataset){
                row[colNum] = (row[colNum] - minimum) / difference;
            }
        }
    }

    public static List<String[]> loadCSV(String filename)  {
        try(InputStream inputStream = Util.class.getResourceAsStream(filename)){
            InputStreamReader inputStreamReader = null;
            if (inputStream != null) {
                inputStreamReader = new InputStreamReader(inputStream);
            } else {
                throw new RuntimeException("File not found");
            }
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.lines().map(line -> line.split(",")).collect(Collectors.toList());
        }catch (IOException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static double max(double[] numbers){
        return Arrays.stream(numbers).max().orElse(Double.MIN_VALUE);
    }
}
