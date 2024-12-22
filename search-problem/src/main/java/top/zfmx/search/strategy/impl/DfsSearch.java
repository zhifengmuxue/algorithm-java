package top.zfmx.search.strategy.impl;

import top.zfmx.search.Node;
import top.zfmx.search.strategy.AdvancedSearchStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 深度优先搜索
 * @param <T> 状态类型
 */
public class DfsSearch<T> implements AdvancedSearchStrategy<T> {
    @Override
    public Node<T> search(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        Stack<Node<T>> frontier = new Stack<>();
        frontier.push(new Node<>(initial, null));
        Set<T> explored = new HashSet<>();
        explored.add(initial);

        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.pop();
            T currentState = currentNode.getState();

            if (goalTest.test(currentState)) {
                return currentNode;
            }

            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue;
                }
                explored.add(child);
                frontier.push(new Node<>(child, currentNode));
            }
        }
        return null;
    }
}