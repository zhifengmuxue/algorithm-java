package top.zfmx.framework;

import java.util.List;

/**
 * 无权图
 * @param <V> 顶点
 */
public class UnweightedGraph<V> extends Graph<V, Edge>{
    public UnweightedGraph(List<V> vertices) {
        super(vertices);
    }

    public void addEdge(Edge edge) {
        edges.get(edge.u).add(edge);
        edges.get(edge.v).add(edge.reverse());
    }

    public void addEdge(int u, int v) {
        addEdge(new Edge(u, v));
    }

    public void addEdge(V first, V second) {
        addEdge(indexOf(first), indexOf(second));
    }


}
