package top.zfmx.dijkstra;

import top.zfmx.framework.WeightedGraph.WeightedEdge;

import java.util.Map;

public class Dijkstra<V> {
    public static final class DijkstraNode
            implements Comparable<DijkstraNode>{
        public final int vertex;
        public final double distance;

        public DijkstraNode(int vertex, double distance) {
                this.vertex = vertex;
                this.distance = distance;
            }

            @Override
            public int compareTo(DijkstraNode o) {
                Double mine = distance;
                Double other = o.distance;
                return mine.compareTo(other);
            }
    }

    public static final class DijkstraResult{
        public final double[] distances;
        public final Map<Integer, WeightedEdge> path;
        public DijkstraResult(double[] distances, Map<Integer, WeightedEdge> path) {
            this.distances = distances;
            this.path = path;
        }

    }


}
