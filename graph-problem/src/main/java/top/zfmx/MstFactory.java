package top.zfmx;

import top.zfmx.framework.WeightedGraph;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.IntConsumer;

public final class MstFactory {
    public static List<WeightedGraph.WeightedEdge> mst(int start, WeightedGraph<?> graph){
        LinkedList<WeightedGraph.WeightedEdge> result = new LinkedList<>();
        if (start < 0 || start >= graph.getVertexCount()){
            return result;
        }
        PriorityQueue<WeightedGraph.WeightedEdge> priorityQueue = new PriorityQueue<>();
        boolean[] visited = new boolean[graph.getVertexCount()];
        IntConsumer visit = v -> {
            visited[v] = true;
            for (WeightedGraph.WeightedEdge edge : graph.edgesOf(v)){
                if (!visited[edge.v]){
                    priorityQueue.add(edge);
                }
            }
        };

        visit.accept(start);
        while (!priorityQueue.isEmpty()){
            WeightedGraph.WeightedEdge edge = priorityQueue.remove();
            if (visited[edge.v]){
                continue;
            }
            result.add(edge);
            visit.accept(edge.v);
        }

        return result;
    }
}
