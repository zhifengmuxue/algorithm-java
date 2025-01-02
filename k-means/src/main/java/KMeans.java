import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * KMeans类
 * 用于实现KMeans算法
 * @param <Point> 数据点类型
 */
public class KMeans<Point extends DataPoint> {

    /**
     * 蔟类
     */
    public class Cluster {
        public List<Point> points;
        public DataPoint centroid;
        public Cluster(List<Point> points, DataPoint randPoint) {
            this.points = points;
            this.centroid = randPoint;
        }
    }

    private List<Point> points;
    private List<Cluster> clusters;

    public KMeans(int k, List<Point> points){
        if(k < 1){
            throw new IllegalArgumentException("K must be >= 1");
        }
        this.points = points;
        zScoreNormalization();
        clusters = new ArrayList<>();
        for(int i = 0; i < k; i++){
            DataPoint randPoint = randomPoint();
            Cluster cluster = new Cluster(new ArrayList<>(), randPoint);
            clusters.add(cluster);
        }
    }

    private List<DataPoint> centroids(){
        return clusters.stream().map(cluster -> cluster.centroid)
                .collect(Collectors.toList());
    }

    private List<Double> dimensionSlice(int dimension){
        return points.stream().map(point -> point.dimensions.get(dimension))
                .collect(Collectors.toList());
    }

    /**
     * 计算z-score标准化
     */
    private void zScoreNormalization(){
        List<List<Double>> zScored = new ArrayList<>();
        for(Point ignored : points){
            zScored.add(new ArrayList<>());
        }
        for(int dimension = 0; dimension < points.get(0).numDimensions; dimension++){
            List<Double> dimensionSlice = dimensionSlice(dimension);
            Statistics statistics = new Statistics(dimensionSlice);
            List<Double> zScores = statistics.zScored();
            for(int i = 0; i < points.size(); i++){
                zScored.get(i).add(zScores.get(i));
            }
        }
        for (int i = 0; i < points.size(); i++){
            points.get(i).dimensions = zScored.get(i);
        }
    }

    /**
     * 初始化簇中心
     * @return 点
     */
    private DataPoint randomPoint(){
        List<Double> randDimensions = new ArrayList<>();
        Random random = new Random();
        for(int dimension = 0; dimension < points.getFirst().numDimensions; dimension++){
            List<Double> dimensionSlice = dimensionSlice(dimension);
            Statistics statistics = new Statistics(dimensionSlice);
            double randValue = random.nextDouble() * (statistics.max() - statistics.min()) + statistics.min();
            randDimensions.add(randValue);
        }
        return new DataPoint(randDimensions);
    }

    private void assignClusters(){
        for(Point point : points){
            double lowestDistance = Double.MAX_VALUE;
            Cluster closestCluster = clusters.getFirst();
            for(Cluster cluster : clusters){
                double distance = point.distance(cluster.centroid);
                if(distance < lowestDistance){
                    lowestDistance = distance;
                    closestCluster = cluster;
                }
            }
            closestCluster.points.add(point);
        }
    }

    private void generateCentroids(){
        for(Cluster cluster : clusters){
            if (cluster.points.isEmpty()){
                continue;
            }
            List<Double> means = new ArrayList<>();
            for(int i = 0; i < cluster.points.getFirst().numDimensions; i++){
                int dimension = i;
                Double dimensionMean = cluster.points.stream()
                        .mapToDouble(point -> point.dimensions.get(dimension))
                        .average().orElse(0.0);
                means.add(dimensionMean);
            }
            cluster.centroid = new DataPoint(means);
        }
    }

    private boolean listsEqual(List<DataPoint> oldCentroids, List<DataPoint> newCentroids){
        if(oldCentroids.size() != newCentroids.size()){
            return false;
        }
        for(int i = 0; i < oldCentroids.size(); i++){
            if(oldCentroids.get(i).distance(newCentroids.get(i)) != 0){
                return false;
            }
        }
        return true;
    }

    public List<Cluster> run(int maxIterations){
        for(int it = 0; it < maxIterations; it++){
            for(Cluster cluster : clusters){
                cluster.points.clear();
            }
            assignClusters();
            List<DataPoint> oldCentroids = new ArrayList<>(centroids());
            generateCentroids();
            if(listsEqual(oldCentroids, centroids())){
                System.out.println("Converged after " + it + " iterations");
                return clusters;
            }
        }

        return clusters;
    }
}
