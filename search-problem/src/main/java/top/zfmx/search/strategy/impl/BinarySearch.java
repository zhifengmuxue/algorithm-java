package top.zfmx.search.strategy.impl;

import top.zfmx.search.strategy.SearchStrategy;

import java.util.List;

/**
 * Binary search strategy.
 * @param <T> the type of elements in the list
 */
public class BinarySearch<T extends Comparable<T>> implements SearchStrategy<T> {

    @Override
    public boolean contains(List<T> list, T key) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            int comparison = list.get(middle).compareTo(key);
            if (comparison < 0) {
                low = middle + 1;
            } else if (comparison > 0) {
                high = middle - 1;
            } else {
                return true;
            }
        }
        return false;
    }
}

