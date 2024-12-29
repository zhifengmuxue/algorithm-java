package top.zfmx;

import top.zfmx.framework.WeightedGraph;
import top.zfmx.framework.WeightedGraph.WeightedEdge;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public final class DijkstraFactory<V> {

    // 优先队列节点
    public record DijkstraNode(int vertex, double distance)
                implements Comparable<DijkstraNode> {

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

    public DijkstraResult dijkstra(V root, WeightedGraph<V> graph){
        int first = graph.indexOf(root);
        double[] distances = new double[graph.getVertexCount()];
        distances[first] = 0;
        boolean[] visited = new boolean[graph.getVertexCount()];
        visited[first] = true;

        HashMap<Integer, WeightedEdge> path = new HashMap<>();
        PriorityQueue<DijkstraNode> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new DijkstraFactory.DijkstraNode(first, 0));
        while(!priorityQueue.isEmpty()){
            int u = priorityQueue.poll().vertex;
            double distanceU = distances[u];
            for (WeightedEdge edge : graph.edgesOf(u)){
                double distanceV = distances[edge.v];
                double pathWeight = distanceU + edge.weight;
                if (!visited[edge.v] || pathWeight < distanceV){
                    visited[edge.v] = true;
                    distances[edge.v] = pathWeight;
                    path.put(edge.v, edge);
                    priorityQueue.offer(new DijkstraFactory.DijkstraNode(edge.v, pathWeight));
                }
            }
        }
        return new DijkstraFactory.DijkstraResult(distances, path);
    }

}
