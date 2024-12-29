package top.zfmx.framwork;

import java.util.List;

/**
 * 染色体 (Chromosome)
 * 一个染色体是一个解决方案的表示。在遗传算法中，一个染色体是一个解决方案的表示。
 * 一个染色体包含五个基本特性：
 * 1. 确定自己的适应度
 * 2. 实现交换操作
 * 3. 实现变异操作
 * 4. 实现复制操作
 * 5. 实现比较操作
 * 填入T类型变量的对象必须是染色体的子类
 */
public abstract class Chromosome<T extends Chromosome<T>>
        implements Comparable<T> {
    public abstract double fitness();  // 适应度
    public abstract List<T> crossover(T other);  // 交换操作
    public abstract void mutate();  // 变异操作
    public abstract T copy();  // 复制操作

    /**
     * 比较操作, 比较两个染色体的适应度
     * @param other 另一个染色体
     * @return 适应度的比较结果
     */
    @Override
    public int compareTo(T other){
        Double mine = this.fitness();
        Double another = other.fitness();
        return mine.compareTo(another);
    }
}
