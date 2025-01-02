import java.util.ArrayList;
import java.util.List;

/**
 * 数据点类
 * 用于表示一个数据点
 */
public class DataPoint {
    public final int numDimensions;
    private final List<Double> originals;
    public List<Double> dimensions;

    /**
     * 构造函数
     * @param initials 初始值
     */
    public DataPoint(List<Double> initials){
        originals = initials;
        dimensions = new ArrayList<>(initials);
        numDimensions = dimensions.size();
    }

    /**
     * 计算两个数据点之间的距离
     * @param other 另一个数据点
     * @return 两个数据点之间的距离
     */
    public double distance(DataPoint other){
        double difference = 0.0;
        for(int i = 0; i < numDimensions; i++){
            double differenceOfDimension = dimensions.get(i) - other.dimensions.get(i);
            difference += Math.pow(differenceOfDimension, 2);
        }
        return Math.sqrt(difference);
    }

    @Override
    public String toString(){
        return originals.toString();
    }
}
