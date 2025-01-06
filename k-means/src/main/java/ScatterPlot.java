import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScatterPlot<Point extends DataPoint> extends JPanel {
    private final List<KMeans<Point>.Cluster> clusters;

    public ScatterPlot(List<KMeans<Point>.Cluster> clusters) {
        this.clusters = clusters;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 设置绘图区域大小
        int width = getWidth();
        int height = getHeight();

        // 定义颜色数组，用于不同簇的着色
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN};

        // 遍历每个簇
        for (int i = 0; i < clusters.size(); i++) {
            KMeans<Point>.Cluster cluster = clusters.get(i);
            Color color = colors[i % colors.length]; // 循环使用颜色

            // 绘制簇中的数据点
            for (DataPoint point : cluster.points) {
                int x = (int) (point.dimensions.get(0) * width / 10); // 假设数据范围在 0-10
                int y = (int) (point.dimensions.get(1) * height / 10); // 假设数据范围在 0-10
                g2d.setColor(color);
                g2d.fillOval(x, y, 8, 8); // 绘制点
            }

            // 绘制簇的质心
            DataPoint centroid = cluster.centroid;
            int centroidX = (int) (centroid.dimensions.get(0) * width / 10);
            int centroidY = (int) (centroid.dimensions.get(1) * height / 10);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(centroidX - 5, centroidY - 5, 10, 10); // 绘制质心
        }
    }

    public void showScatterPlot() {
        JFrame frame = new JFrame("KMeans Clustering Scatter Plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(new ScatterPlot<>(clusters));
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        List<Governor> governors = List.of(
                new Governor(-86.79113, 72, "Alabama"),
                new Governor(-152.404419, 66, "Alaska"),
                new Governor(-111.431221, 53, "Arizona"),
                new Governor(-92.373123, 66, "Arkansas"),
                new Governor(-119.681564, 79, "California"),
                new Governor(-105.311104, 65, "Colorado"),
                new Governor(-72.755371, 61, "Connecticut"),
                new Governor(-75.507141, 61, "Delaware"),
                new Governor(-81.686783, 64, "Florida"),
                new Governor(-83.643074, 74, "Georgia"),
                new Governor(-157.498337, 60, "Hawaii"),
                new Governor(-114.478828, 75, "Idaho"),
                new Governor(-88.986137, 60, "Illinois"),
                new Governor(-86.258278, 49, "Indiana"),
                new Governor(-93.210526, 57, "Iowa"),
                new Governor(-96.726486, 60, "Kansas"),
                new Governor(-84.670067, 50, "Kentucky")
        );
        KMeans<Governor> kMeans = new KMeans<>(4, governors);
        List<KMeans<Governor>.Cluster> run = kMeans.run(100);

        System.out.println("聚类结果：");
        for (KMeans<Governor>.Cluster cluster : run) {
            System.out.println(cluster.points);
        }
        ScatterPlot<Governor> scatterPlot = new ScatterPlot<>(run);
        scatterPlot.showScatterPlot();
    }
}