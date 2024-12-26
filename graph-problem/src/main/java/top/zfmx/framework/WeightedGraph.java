package top.zfmx.framework;

import java.util.Arrays;
import java.util.List;

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
}
