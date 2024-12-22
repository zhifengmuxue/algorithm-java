package top.zfmx.search.strategy.impl;

import top.zfmx.search.Node;
import top.zfmx.search.strategy.AdvancedSearchStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

/**
 * A*搜索
 * @param <T> 状态类型
 */

public class AStarSearch<T> implements AdvancedSearchStrategy<T> {

    // 启发式函数
    private final ToDoubleFunction<T> heuristic;
    private int count = 0;

    public AStarSearch(ToDoubleFunction<T> heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public Node<T> search(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        PriorityQueue<Node<T>> frontier = new PriorityQueue<>();
        frontier.offer(new Node<>(initial, null, 0.0, heuristic.applyAsDouble(initial)));
        Map<T, Double> explored = new HashMap<>();
        explored.put(initial, 0.0);

        while (!frontier.isEmpty()) {
            count++;
            Node<T> currentNode = frontier.poll();
            T currentState = currentNode.getState();

            if (goalTest.test(currentState)) {
                return currentNode;
            }

            for (T child : successors.apply(currentState)) {
                double newCost = currentNode.getCost() + 1;

                if (!explored.containsKey(child) || explored.get(child) > newCost) {
                    explored.put(child, newCost);
                    frontier.offer(new Node<>(child, currentNode, newCost, heuristic.applyAsDouble(child)));
                }
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }
}
