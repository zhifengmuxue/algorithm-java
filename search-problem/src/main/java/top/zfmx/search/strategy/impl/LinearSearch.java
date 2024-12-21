package top.zfmx.search.strategy.impl;

import top.zfmx.search.strategy.SearchStrategy;

import java.util.List;

/**
 * Linear search strategy.
 * @param <T> the type of elements in the list
 */
public class LinearSearch<T extends Comparable<T>> implements SearchStrategy<T> {

    @Override
    public boolean contains(List<T> list, T key) {
        for (T item : list) {
            if (item.compareTo(key) == 0) {
                return true;
            }
        }
        return false;
    }
}
