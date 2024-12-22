package top.zfmx.search.strategy.impl;

import top.zfmx.search.Node;
import top.zfmx.search.strategy.AdvancedSearchStrategy;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 深度优先搜索
 * @param <T> 状态类型
 */
public class BfsSearch<T> implements AdvancedSearchStrategy<T> {
    @Override
    public Node<T> search(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        Queue<Node<T>> frontier = new LinkedList<>();
        frontier.offer(new Node<>(initial, null));
        Set<T> explored = new HashSet<>();
        explored.add(initial);

        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.poll();
            T currentState = currentNode.getState();

            if (goalTest.test(currentState)) {
                return currentNode;
            }

            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue;
                }
                explored.add(child);
                frontier.offer(new Node<>(child, currentNode));
            }
        }
        return null;
    }
}
