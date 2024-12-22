package top.zfmx.search;

import top.zfmx.search.strategy.AdvancedSearchStrategy;
import top.zfmx.search.strategy.SearchStrategy;
import top.zfmx.search.strategy.impl.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;


/**
 * 搜索方法工厂
 */
public class SearchFactory {


    // 从节点到路径
    public static <T> List<T> nodeToPath(Node<T> node){
        List<T> path = new ArrayList<>();
        path.add(node.getState());
        while(node.getParent() != null){
            node = node.getParent();
            path.addFirst(node.getState());
        }
        return path;
    }

    public <T extends Comparable<T>> SearchStrategy<T> getSearchStrategy(String algorithmType) {
        if ("linear".equalsIgnoreCase(algorithmType)) {
            return new LinearSearch<>();
        } else if ("binary".equalsIgnoreCase(algorithmType)) {
            return new BinarySearch<>();
        } else {
            throw new IllegalArgumentException("Unknown search algorithm type");
        }
    }

    public <T extends Comparable<T>> AdvancedSearchStrategy<T> getAdvancedSearchStrategy(String algorithmType, ToDoubleFunction<T> heuristic) {
        return switch (algorithmType.toLowerCase()) {
            case "dfs" -> new DfsSearch<>();
            case "bfs" -> new BfsSearch<>();
            case "astar" -> new AStarSearch<>(heuristic);
            default -> throw new IllegalArgumentException("Unknown search algorithm type: " + algorithmType);
        };
    }
}
