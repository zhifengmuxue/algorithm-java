package top.zfmx.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图
 * @param <V> 顶点
 * @param <E> 边
 */
public class Graph<V, E extends Edge>{
    private ArrayList<V> vertices = new ArrayList<>();
    protected ArrayList<ArrayList<Edge>> edges = new ArrayList<>();

    public Graph() {
    }

    public Graph(List<V> vertices) {
        this.vertices.addAll(vertices);
        for (int i = 0; i < vertices.size(); i++) {
            edges.add(new ArrayList<>());
        }
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount(){
        return edges.stream().mapToInt(List::size).sum();
    }

    public int addVertex(V vertex) {
        vertices.add(vertex);
        edges.add(new ArrayList<>());
        return vertices.size() - 1;
    }

    public V vertexAt(int index) {
        return vertices.get(index);
    }

    public int indexOf(V vertex) {
        return vertices.indexOf(vertex);
    }

    public List<V> neighborsOf(int index) {
        return edges.get(index).stream()
                .map(e -> vertexAt(e.v))
                .collect(Collectors.toList());
    }

    public List<V> neighborsOf(V vertex) {
        return neighborsOf(indexOf(vertex));
    }

    public List<E> edgesOf(int index) {
        return edges.get(index).stream()
                .map(e -> (E)e)
                .collect(Collectors.toList());
    }

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
