import java.util.List;

import org.junit.Test;

public class TestCSV {
    @Test
    public void testCSV() {
        String filePath = "src/main/resources/data.csv";
        List<DataPoint> dataPoints = CSVUtils.importFromCSV(filePath);
        assert !dataPoints.isEmpty();

        for (DataPoint point : dataPoints) {
            System.out.println(point);
        }

        KMeans<DataPoint> kMeans = new KMeans<>(2, dataPoints);
        List<KMeans<DataPoint>.Cluster> run = kMeans.run(100);

        System.out.println("聚类结果：");
        for (KMeans<DataPoint>.Cluster cluster : run) {
            System.out.println(cluster.points);
        }

        // 这里测试会一闪而过，可以去ScatterPlot中看他的入口函数，那里可以看到图形化界面
        ScatterPlot<DataPoint> scatterPlot = new ScatterPlot<>(run);
        scatterPlot.showScatterPlot();

    }
}