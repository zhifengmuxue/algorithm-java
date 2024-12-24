package top.zfmx;

import java.util.*;

/**
 * 约束满足问题（CSP）类定义
 * @param <V> 变量类型
 * @param <D> 值类型
 */
public class CSP<V, D> {
    // 存储所有变量的列表
    private final List<V> variables;

    // 存储每个变量对应的取值域，Map 中的键为变量，值为该变量的取值列表
    private final Map<V, List<D>> domains;

    // 存储每个变量对应的约束列表，Map 中的键为变量，值为与该变量相关的所有约束
    private final Map<V, List<Constraint<V, D>>> constraints = new HashMap<>();

    /**
     * 构造函数，用于初始化 CSP 问题
     * @param variables 变量列表
     * @param domains 每个变量对应的取值域
     */
    public CSP(List<V> variables, Map<V, List<D>> domains) {
        this.variables = variables;
        this.domains = domains;

        // 对于每个变量，初始化该变量的约束列表为空
        for (V variable : variables) {
            constraints.put(variable, new ArrayList<>());

            // 检查是否每个变量都有对应的取值域
            if (!domains.containsKey(variable)) {
                throw new IllegalArgumentException("Every variable should have a domain assigned to it.");
            }
        }
    }

    /**
     * 添加约束到 CSP 中
     * @param constraint 需要添加的约束
     */
    public void addConstraint(Constraint<V, D> constraint) {
        // 对于约束涉及的每一个变量，检查其是否存在于 CSP 中
        for (V variable : constraint.variables) {
            if (!variables.contains(variable)) {
                // 如果约束中包含 CSP 中不存在的变量，抛出异常
                throw new IllegalArgumentException("Variable in constraint not in CSP");
            } else {
                // 如果变量存在，将该约束添加到该变量的约束列表中
                constraints.get(variable).add(constraint);
            }
        }
    }

    /**
     * 检查当前赋值是否满足指定变量的所有约束
     * @param variable 当前检查的变量
     * @param assignment 当前已赋值的映射
     * @return 是否满足所有约束
     */
    public boolean consistent(V variable, Map<V, D> assignment) {
        // 遍历该变量的所有约束
        for (Constraint<V, D> constraint : constraints.get(variable)) {
            // 如果约束不满足，返回 false
            if (!constraint.satisfied(assignment)) {
                return false;
            }
        }
        // 如果所有约束都满足，返回 true
        return true;
    }

    /**
     * 回溯搜索方法，用于找到满足所有约束的赋值
     * @param assignment 当前赋值的映射
     * @return 满足约束的赋值
     */
    public Map<V, D> backtrackingSearch(Map<V, D> assignment) {
        // 如果当前赋值的大小已经等于所有变量的数量，说明已经为所有变量赋值，返回当前赋值
        if (assignment.size() == variables.size())
            return assignment;

        // 选择一个未赋值的变量
        Optional<V> first = variables.stream().filter(v ->
                !assignment.containsKey(v)).findFirst();
        if (first.isEmpty())
            throw new IllegalArgumentException("No solution");
        V unassigned = first.get();

        // 遍历该变量的所有可能值
        for (D value : domains.get(unassigned)) {
            Map<V, D> local = new HashMap<>(assignment);
            local.put(unassigned, value); // 为该未赋值变量赋值

            // 检查当前赋值是否满足该变量的所有约束
            if (consistent(unassigned, local)) {
                // 如果满足约束，则递归调用回溯搜索
                Map<V, D> result = backtrackingSearch(local);
                if (result != null) {
                    // 如果找到有效解，返回该解
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 无参数的回溯搜索方法，初始化赋值为空
     * @return 满足约束的赋值
     */
    public Map<V, D> backtrackingSearch() {
        return backtrackingSearch(new HashMap<>());
    }
}
