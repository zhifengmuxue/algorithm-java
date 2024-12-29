package top.zfmx.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 图
 * @param <V> 顶点
 * @param <E> 边
 */
public class Graph<V, E extends Graph.Edge>{
    private final ArrayList<V> vertices = new ArrayList<>();
    protected ArrayList<ArrayList<Edge>> edges = new ArrayList<>();

    /**
     * 边
     * 采用静态内部类的方式，可以直接访问外部类的泛型参数
     */
    public static class Edge {
        public final int u;
        public final int v;

        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }

        public Edge reverse() {
            return new Edge(v, u);
        }

        @Override
        public String toString() {
            return u + " -> " + v;
        }
    }

    // 构造函数
    public Graph() {
    }

    // 构造函数
    public Graph(List<V> vertices) {
        this.vertices.addAll(vertices);
        for (int i = 0; i < vertices.size(); i++) {
            edges.add(new ArrayList<>());
        }
    }

    // 获取顶点数量
    public int getVertexCount() {
        return vertices.size();
    }

    // 获取边数量
    public int getEdgeCount(){
        return edges.stream().mapToInt(List::size).sum();
    }

    // 添加顶点
    public int addVertex(V vertex) {
        vertices.add(vertex);
        edges.add(new ArrayList<>());
        return vertices.size() - 1;
    }

    // 获取顶点
    public V vertexAt(int index) {
        return vertices.get(index);
    }

    // 获取顶点索引
    public int indexOf(V vertex) {
        return vertices.indexOf(vertex);
    }

    // 获取邻居
    public List<V> neighborsOf(int index) {
        return edges.get(index).stream()
                .map(e -> vertexAt(e.v))
                .collect(Collectors.toList());
    }

    // 获取邻居
    public List<V> neighborsOf(V vertex) {
        return neighborsOf(indexOf(vertex));
    }

    // 通过索引获取边
    public List<E> edgesOf(int index) {
        return edges.get(index).stream()
                .map(e -> (E)e)
                .collect(Collectors.toList());
    }

    // 通过顶点获取边
    public List<E> edgesOf(V vertex) {
        return edgesOf(indexOf(vertex));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertices.size(); i++) {
            sb.append(vertexAt(i))
                    .append(" -> ")
                    .append(Arrays.toString(neighborsOf(i).toArray()))
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }

}
