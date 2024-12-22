package top.zfmx.search.strategy;

import top.zfmx.search.Node;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface AdvancedSearchStrategy<T> {
    Node<T> search(T initial, Predicate<T> goalTest, Function<T, List<T>> successors);
    int getCount();
}
