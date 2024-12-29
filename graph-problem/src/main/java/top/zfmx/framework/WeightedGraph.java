package top.zfmx.framework;

import top.zfmx.dijkstra.Dijkstra;

import java.util.*;
import java.util.function.IntConsumer;

/**
 * 加权图
 * @param <V> 顶点
 */
public class WeightedGraph<V> extends Graph<V, WeightedEdge> {
    public WeightedGraph(List<V> vertices) {
        super(vertices);
    }

    public void addEdge(WeightedEdge edge) {
        edges.get(edge.u).add(edge);
        edges.get(edge.v).add(edge.reverse());
    }

    public void addEdge(int u, int v, double weight) {
        addEdge(new WeightedEdge(u, v, weight));
    }

    public void addEdge(V first, V second, double weight) {
        addEdge(indexOf(first), indexOf(second), weight);
    }

    /**
     * 计算路径的总权重
     * @param path 路径
     * @return 总权重
     */
    public static double totalWeight(List<WeightedEdge> path){
        return path.stream().mapToDouble(e -> e.weight).sum();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getVertexCount(); i++){
            sb.append(vertexAt(i));
            sb.append(" -> ");
            sb.append(Arrays.toString(edgesOf(i)
                    .stream().map(we -> "(" + vertexAt(we.v) + ", " + we.weight + ")").toArray()));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    /********************** 最小生成树  prim算法 **************************************/
    public List<WeightedEdge> mst(int start){
        LinkedList<WeightedEdge> result = new LinkedList<>();
        if (start < 0 || start >= getVertexCount()){
            return result;
        }
        PriorityQueue<WeightedEdge> priorityQueue = new PriorityQueue<>();
        boolean[] visited = new boolean[getVertexCount()];
        IntConsumer visit = v -> {
            visited[v] = true;
            for (WeightedEdge edge : edgesOf(v)){
                if (!visited[edge.v]){
                    priorityQueue.add(edge);
                }
            }
        };

        visit.accept(start);
        while (!priorityQueue.isEmpty()){
            WeightedEdge edge = priorityQueue.remove();
            if (visited[edge.v]){
                continue;
            }
            result.add(edge);
            visit.accept(edge.v);
        }

        return result;
    }

    public void printWeightedPath(List<WeightedEdge> wp){
        for (WeightedEdge edge : wp){
            System.out.println(vertexAt(edge.u) + " " + edge.weight + " > " + vertexAt(edge.v));
        }
        System.out.println("Total weight: " + totalWeight(wp));
    }

    // ********************** 最短路径  dijkstra算法 **************************************
    /**
     * Dijkstra算法
     * @param root 起始顶点
     * @return Dijkstra算法结果
     */
    public Dijkstra.DijkstraResult dijkstra(V root){
        int first = indexOf(root);
        double[] distances = new double[getVertexCount()];
        distances[first] = 0;
        boolean[] visited = new boolean[getVertexCount()];
        visited[first] = true;

        HashMap<Integer, WeightedEdge> path = new HashMap<>();
        PriorityQueue<Dijkstra.DijkstraNode> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new Dijkstra.DijkstraNode(first, 0));
        while(!priorityQueue.isEmpty()){
            int u = priorityQueue.poll().vertex;
            double distanceU = distances[u];
            for (WeightedEdge edge : edgesOf(u)){
                double distanceV = distances[edge.v];
                double pathWeight = distanceU + edge.weight;
                if (!visited[edge.v] || pathWeight < distanceV){
                    visited[edge.v] = true;
                    distances[edge.v] = pathWeight;
                    path.put(edge.v, edge);
                    priorityQueue.offer(new Dijkstra.DijkstraNode(edge.v, pathWeight));
                }
            }
        }
        return new Dijkstra.DijkstraResult(distances, path);
    }

    /**
     * array of distances to map of distances
     * @param distances 距离数组
     * @return 距离映射
     */
    public Map<V, Double> distanceArrayToDistanceMap(double[] distances){
        HashMap<V, Double> result = new HashMap<>();
        for (int i = 0; i < distances.length; i++){
            result.put(vertexAt(i), distances[i]);
        }
        return result;
    }

    public static List<WeightedEdge> pathMapToPath(int start, int end,
                                                   Map<Integer, WeightedEdge> pathMap){
        if (pathMap.isEmpty()){
            return List.of();
        }
        LinkedList<WeightedEdge> result = new LinkedList<>();
        WeightedEdge edge = pathMap.get(end);
        result.add(edge);
        while (edge.u != start){
            edge = pathMap.get(edge.u);
            result.addFirst(edge);
        }
        Collections.reverse(result);
        return result;
    }
}
