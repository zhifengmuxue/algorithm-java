package top.zfmx.framework;

import java.util.List;

/**
 * 无权图
 * @param <V> 顶点
 */
public class UnweightedGraph<V> extends Graph<V, Graph.Edge>{

    // 构造函数
    public UnweightedGraph(List<V> vertices) {
        super(vertices);
    }

    // 添加边
    public void addEdge(Edge edge) {
        edges.get(edge.u).add(edge);
        edges.get(edge.v).add(edge.reverse());
    }

    // 添加边
    public void addEdge(int u, int v) {
        addEdge(new Edge(u, v));
    }

    // 添加边
    public void addEdge(V first, V second) {
        addEdge(indexOf(first), indexOf(second));
    }
}
