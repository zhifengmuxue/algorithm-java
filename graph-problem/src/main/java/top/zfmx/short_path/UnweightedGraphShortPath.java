package top.zfmx.short_path;


import top.zfmx.framework.UnweightedGraph;
import top.zfmx.search.Node;
import top.zfmx.search.SearchFactory;
import top.zfmx.search.strategy.AdvancedSearchStrategy;

import java.util.List;

/**
 * 无权图的最短路径
 * 导入了search-problem模块，其中的bfs算法
 */
public class UnweightedGraphShortPath {
    private static UnweightedGraph<String> graph;
    public static void main(String[] args) {
        init();
        SearchFactory searchFactory = new SearchFactory();
        AdvancedSearchStrategy<String> searchStrategy = searchFactory.getAdvancedSearchStrategy("bfs", null);

        Node<String> bfsResult = searchStrategy.search("Boston", v->v.equals("Miami"), graph::neighborsOf);
        if (bfsResult == null){
            System.out.println("No solution found!");
        } else {
            List<String> path = SearchFactory.nodeToPath(bfsResult);
            System.out.println("path from Boston to Miami:");
            System.out.println(path);
        }
    }

    private static void init(){
        graph = new UnweightedGraph<>(
                List.of("Seattle", "San Francisco", "Los Angeles",
                        "Riverside", "Phoenix", "Chicago", "Boston",
                        "New York", "Atlanta", "Miami", "Dallas", "Houston",
                        "Detroit", "Philadelphia", "Washington"));

        graph.addEdge("Seattle", "Chicago");
        graph.addEdge("Seattle", "San Francisco");
        graph.addEdge("San Francisco", "Riverside");
        graph.addEdge("San Francisco", "Los Angeles");
        graph.addEdge("Los Angeles", "Riverside");
        graph.addEdge("Los Angeles", "Phoenix");
        graph.addEdge("Riverside", "Phoenix");
        graph.addEdge("Riverside", "Chicago");
        graph.addEdge("Phoenix", "Dallas");
        graph.addEdge("Phoenix", "Houston");
        graph.addEdge("Dallas", "Chicago");
        graph.addEdge("Dallas", "Atlanta");
        graph.addEdge("Dallas", "Houston");
        graph.addEdge("Houston", "Atlanta");
        graph.addEdge("Houston", "Miami");
        graph.addEdge("Atlanta", "Chicago");
        graph.addEdge("Atlanta", "Washington");
        graph.addEdge("Atlanta", "Miami");
        graph.addEdge("Miami", "Washington");
        graph.addEdge("Chicago", "Detroit");
        graph.addEdge("Detroit", "Boston");
        graph.addEdge("Detroit", "Washington");
        graph.addEdge("Detroit", "New York");
        graph.addEdge("Boston", "New York");
        graph.addEdge("New York", "Philadelphia");
        graph.addEdge("Philadelphia", "Washington");
        System.out.println(graph);
    }
}
