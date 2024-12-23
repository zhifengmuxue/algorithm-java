package top.zfmx.Queens;

import top.zfmx.Constraint;

import java.util.List;
import java.util.Map;

public final class QueensConstraint extends Constraint<Integer, Integer> {
    private final List<Integer> columns;

    public QueensConstraint(List<Integer> columns) {
        super(columns);
        this.columns = columns;
    }

    /**
     * 检查约束是否满足，皇后不能在同一行或同一对角线
     * @param assignment 赋值
     * @return 是否满足
     */
    @Override
    public boolean satisfied(Map<Integer, Integer> assignment) {
        for(Map.Entry<Integer, Integer> item : assignment.entrySet()){
            int queen1Col = item.getKey();
            int queen1Row = item.getValue();
            for(int queen2Col = queen1Col + 1; queen2Col < columns.size(); queen2Col++){
                if (assignment.containsKey(queen2Col)){
                    int queen2Row = assignment.get(queen2Col);
                    if (queen1Row == queen2Row)
                        return false;
                    if (Math.abs(queen1Row - queen2Row) == Math.abs(queen1Col - queen2Col))
                        return false;
                }
            }
        }
        return true;
    }
}

