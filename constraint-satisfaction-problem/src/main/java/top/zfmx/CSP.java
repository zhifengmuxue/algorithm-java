package top.zfmx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 约束满足问题
 * @param <V> 变量类型
 * @param <D> 值类型
 */
public class CSP<V, D> {
    private final List<V> variables;
    private final Map<V, List<D>> domains;
    private final Map<V, List<Constraint<V, D>>> constraints = new HashMap<>();

    public CSP(List<V> variables, Map<V, List<D>> domains){
        this.variables = variables;
        this.domains = domains;

        for (V variable : variables){
            constraints.put(variable, new ArrayList<>());
            if (!domains.containsKey(variable)){
                throw new IllegalArgumentException("Every variable should have a domain assigned to it.");
            }
        }
    }

    /**
     * 添加约束
     * @param constraint 约束
     */
    public void addConstraint(Constraint<V, D> constraint){
        for(V variable : constraint.variables){
            if (!variables.contains(variable)){
                throw new IllegalArgumentException("Variable in constraint not in CSP");
            }else{
                constraints.get(variable).add(constraint);
            }
        }
    }

    /**
     * 检查约束是否满足
     * @param variable 变量
     * @param assignment 赋值
     * @return 是否满足
     */
    public boolean consistent(V variable, Map<V, D> assignment){
        for(Constraint<V, D> constraint : constraints.get(variable)){
            if (!constraint.satisfied(assignment)){
                return false;
            }
        }
        return true;
    }

    /**
     * 回溯搜索
     * @param assignment 赋值
     * @return 赋值
     */
    public Map<V, D> backtrackingSearch(Map<V, D> assignment){

        // 回溯终结
        if (assignment.size() == variables.size()){
            return assignment;
        }

        // 选择一个未赋值的变量
        V unassigned = variables.stream().filter(v ->
                !assignment.containsKey(v)).findFirst().get();

        // 遍历未赋值的变量
        for(D value : domains.get(unassigned)){
            Map<V, D> local = new HashMap<>(assignment);
            local.put(unassigned, value);

            // 检查约束是否满足
            if (consistent(unassigned, local)){
                Map<V, D> result = backtrackingSearch(local);
                if (result != null){
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 回溯搜索
     * @return 赋值
     */
    public Map<V, D> backtrackingSearch(){
        return backtrackingSearch(new HashMap<>());
    }
}
