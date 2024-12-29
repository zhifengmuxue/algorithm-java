import org.junit.Test;
import top.zfmx.dijkstra.Dijkstra;
import top.zfmx.framework.WeightedGraph.WeightedEdge;
import top.zfmx.framework.WeightedGraph;

import java.util.List;
import java.util.Map;

public class TestWeightedGraph {
    private WeightedGraph<String> graph;

    @Test
    public void testInit(){
        graph = new WeightedGraph<>(
                List.of("Seattle", "San Francisco", "Los Angeles",
                        "Riverside", "Phoenix", "Chicago", "Boston",
                        "New York", "Atlanta", "Miami", "Dallas", "Houston",
                        "Detroit", "Philadelphia", "Washington"));
        graph.addEdge("Seattle", "Chicago", 1737);
        graph.addEdge("Seattle", "San Francisco", 678);
        graph.addEdge("San Francisco", "Riverside", 386);
        graph.addEdge("San Francisco", "Los Angeles", 348);
        graph.addEdge("Los Angeles", "Riverside", 50);
        graph.addEdge("Los Angeles", "Phoenix", 357);
        graph.addEdge("Riverside", "Phoenix", 307);
        graph.addEdge("Riverside", "Chicago", 1704);
        graph.addEdge("Phoenix", "Dallas", 887);
        graph.addEdge("Phoenix", "Houston", 1015);
        graph.addEdge("Dallas", "Chicago", 805);
        graph.addEdge("Dallas", "Atlanta", 721);
        graph.addEdge("Dallas", "Houston", 225);
        graph.addEdge("Houston", "Atlanta", 702);
        graph.addEdge("Houston", "Miami", 968);
        graph.addEdge("Atlanta", "Chicago", 588);
        graph.addEdge("Atlanta", "Washington", 543);
        graph.addEdge("Atlanta", "Miami", 604);
        graph.addEdge("Miami", "Washington", 923);
        graph.addEdge("Chicago", "Detroit", 238);
        graph.addEdge("Detroit", "Boston", 613);
        graph.addEdge("Detroit", "Washington", 396);
        graph.addEdge("Detroit", "New York", 482);
        graph.addEdge("Boston", "New York", 190);
        graph.addEdge("New York", "Philadelphia", 81);
        graph.addEdge("Philadelphia", "Washington", 123);

        System.out.println(graph);
    }


    @Test
    public void testShortPath(){
        testInit();
        // 最短路径测试
        Dijkstra.DijkstraResult result = graph.dijkstra("Los Angeles");
        Map<String, Double> nameDistance = graph.distanceArrayToDistanceMap(result.distances);
        System.out.println("Distance from Los Angeles:");
        nameDistance.forEach((k, v) -> System.out.println(k + " " + v));
        System.out.println();
        System.out.println("Shortest path from Los Angeles to Boston:");
        List<WeightedEdge> path = WeightedGraph.pathMapToPath(graph.indexOf("Los Angeles"), graph.indexOf("Boston"), result.path);
        graph.printWeightedPath(path);
    }

    @Test
    public void testMst(){
        testInit();
        // 计算最小生成树
        List<WeightedEdge> mst = graph.mst(0);
        graph.printWeightedPath(mst);
    }
}
