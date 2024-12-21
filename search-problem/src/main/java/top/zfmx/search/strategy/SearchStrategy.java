package top.zfmx.search.strategy;

import java.util.List;

public interface SearchStrategy<T> {
    boolean contains(List<T> list, T key);
}
