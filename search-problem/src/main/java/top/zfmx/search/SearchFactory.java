package top.zfmx.search;

import top.zfmx.search.strategy.SearchStrategy;
import top.zfmx.search.strategy.impl.BinarySearch;
import top.zfmx.search.strategy.impl.LinearSearch;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;


/**
 * 搜索方法工厂
 */
public class SearchFactory {
    public static class Node<T> implements Comparable<Node<T>> {
        final T state;
        Node<T> parent;
        double cost;    // 从初始状态到当前状态的代价
        double heuristic;  // 启发式值
        Node(T state, Node<T> parent){
            this.state = state;
            this.parent = parent;
        }
        Node(T state, Node<T> parent, double cost, double heuristic){
            this.state = state;
            this.parent = parent;
            this.cost = cost;
            this.heuristic = heuristic;
        }
        @Override
        public int compareTo(Node<T> other){
            Double mine = cost + heuristic;
            Double theirs = other.cost + other.heuristic;
            return mine.compareTo(theirs);
        }
    }

    // 从节点到路径
    public static <T> List<T> nodeToPath(Node<T> node){
        List<T> path = new ArrayList<>();
        path.add(node.state);
        while(node.parent != null){
            node = node.parent;
            path.addFirst(node.state);
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


    /**
     * 深度优先搜索
     * @param initial 初始状态
     * @param goalTest 目标测试
     * @param successors 后继状态
     * @return 结果节点
     * @param <T> 状态类型
     */
    public static <T> Node<T> dfs(T initial,
                                  Predicate<T> goalTest,
                                  Function<T, List<T>> successors){
        Stack<Node<T>> frontier = new Stack<>();
        frontier.push(new Node<>(initial, null));
        Set<T> explored = new HashSet<>();  // 用于存储已经探索过的状态
        explored.add(initial);

        while(!frontier.isEmpty()){
            Node<T> currentNode = frontier.pop();
            T currentState = currentNode.state;

            // 判断是否是目标状态
            if (goalTest.test(currentState)){
                return currentNode;
            }

            // 找到我们可以到达的下一个状态
            for (T child : successors.apply(currentState)){
                if (explored.contains(child)){
                    continue;
                }
                explored.add(child);
                frontier.push(new Node<>(child, currentNode));
            }
        }
        return null;
    }

    /**
     * 广度优先搜索
     * @param initial 初始状态
     * @param goalTest 目标测试
     * @param successors 后继状态
     * @return 结果节点
     * @param <T> 状态类型
     */
    public static <T> Node<T> bfs(T initial,
                                  Predicate<T> goalTest,
                                  Function<T, List<T>> successors){
        Queue<Node<T>> frontier = new LinkedList<>();
        frontier.offer(new Node<>(initial, null));
        Set<T> explored = new HashSet<>();  // 用于存储已经探索过的状态
        explored.add(initial);

        while(!frontier.isEmpty()){
            Node<T> currentNode = frontier.poll();
            T currentState = currentNode.state;
            if (goalTest.test(currentState)){
                return currentNode;
            }
            for (T child : successors.apply(currentState)){
                if (explored.contains(child)){
                    continue;
                }
                explored.add(child);
                frontier.offer(new Node<>(child, currentNode));
            }
        }
        return null;
    }

    /**
     * A*搜索
     * @param initial 初始状态
     * @param goalTest 目标测试
     * @param successors 后继状态
     * @param heuristic 启发式函数
     * @return 结果节点
     * @param <T> 状态类型
     */
    public static <T> Node<T> aStar(T initial,
                                    Predicate<T> goalTest,
                                    Function<T, List<T>> successors,
                                    ToDoubleFunction<T> heuristic){
        PriorityQueue<Node<T>> frontier = new PriorityQueue<>();
        frontier.offer(new Node<>(initial, null, 0.0, heuristic.applyAsDouble(initial)));
        Map<T, Double> explored = new HashMap<>();
        explored.put(initial, 0.0);

        // 从frontier中取出节点，直到frontier为空
        while(!frontier.isEmpty()){
            Node<T> currentNode = frontier.poll();
            T currentState = currentNode.state;
            if (goalTest.test(currentState)){
                return currentNode;
            }

            // 找到我们可以到达的下一个状态
            for (T child : successors.apply(currentState)){
                double newCost = currentNode.cost + 1;  // 代价增加

                // 如果这个状态没有被探索过，或者新的代价更小
                if (!explored.containsKey(child) || explored.get(child) > newCost){
                    explored.put(child, newCost);
                    frontier.offer(new Node<>(child, currentNode, newCost, heuristic.applyAsDouble(child)));
                }
            }
        }
        return null;
    }
}
