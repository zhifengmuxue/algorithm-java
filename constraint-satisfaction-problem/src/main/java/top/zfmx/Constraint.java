package top.zfmx;

import java.util.List;
import java.util.Map;

/**
 * 约束
 * @param <V> 变量类型
 * @param <D> 值类型
 */
public abstract class Constraint<V, D> {
    // 约束的变量
    protected List<V> variables;

    public Constraint(List<V> variables) {
        this.variables = variables;
    }

    // 检查约束是否满足
    public abstract boolean satisfied(Map<V, D> assignment);
}
