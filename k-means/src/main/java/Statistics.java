import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;


/**
 * 统计类
 * 工具类，用于计算一组数据的统计信息
 * 包括：和、平均值、方差、标准差、z-score标准化、最大值、最小值
 */
public final class Statistics {
    private final List<Double> list;
    private final DoubleSummaryStatistics statistics;

    public Statistics(List<Double> list) {
        this.list = list;
        this.statistics = list.stream().collect(Collectors.summarizingDouble(Double::doubleValue));
    }

    // 计算和
    public double sum(){
        return statistics.getSum();
    }

    // 计算平均值
    public double mean(){
        return statistics.getAverage();
    }

    // 计算方差
    public double variance(){
        double mean = mean();
        OptionalDouble average = list.stream().mapToDouble(x -> Math.pow((x - mean), 2))
                .average();
        return average.isPresent() ? average.getAsDouble() : 0.0;
    }

    // 计算标准差
    public double std(){
        return Math.sqrt(variance());
    }

    // z-score 标准化
    public List<Double> zScored(){
        double mean = mean();
        double std = std();
        return list.stream().map(x -> std != 0 ? (x - mean) / std : 0.0)
                .collect(Collectors.toList());
    }

    // 最大值
    public double max(){
        return statistics.getMax();
    }

    // 最小值
    public double min(){
        return statistics.getMin();
    }
}
