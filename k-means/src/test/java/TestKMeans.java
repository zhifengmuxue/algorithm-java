import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestKMeans {
    @Test
    public void testKMeans(){
        DataPoint point1 = new DataPoint(List.of(2.0, 1.0, 1.0));
        DataPoint point2 = new DataPoint(List.of(2.0, 2.0, 5.0));
        DataPoint point3 = new DataPoint(List.of(3.0, 1.5, 2.5));
        KMeans<DataPoint> kMeans = new KMeans<>(2, List.of(point1, point2, point3));
        List<KMeans<DataPoint>.Cluster> run = kMeans.run(100);
        for(KMeans<DataPoint>.Cluster cluster : run){
            System.out.println(cluster.points);
        }

    }

    @Test
    public void testGovernor(){
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
        for(KMeans<Governor>.Cluster cluster : run){
            System.out.println(cluster.points);
        }
    }

    @Test
    public void testAlbum(){
        List<Album> albums = new ArrayList<>();
        albums.add(new Album("Thriller", 1982, 35.45,10));
        albums.add(new Album("Back in Black", 1980, 42.11, 22));
        albums.add(new Album("The Dark Side of the Moon", 1973, 42.49, 45));
        albums.add(new Album("The Bodyguard", 1992, 44.27, 38));
        albums.add(new Album("Bat Out of Hell", 1977, 46.33, 43));
        albums.add(new Album("Their Greatest Hits", 1976, 43.8, 26));
        albums.add(new Album("Saturday Night Fever", 1977, 44.0, 40));
        albums.add(new Album("Rumours", 1977, 40.01, 40));
        albums.add(new Album("Come On Over", 1997, 45.4, 20));
        albums.add(new Album("Led Zeppelin IV", 1971, 42.4, 37));
        KMeans<Album> kMeans = new KMeans<>(3, albums);
        List<KMeans<Album>.Cluster> run = kMeans.run(100);
        for(KMeans<Album>.Cluster cluster : run){
            System.out.println(cluster.points);
        }

    }
}
