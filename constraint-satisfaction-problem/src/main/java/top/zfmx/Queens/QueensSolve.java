package top.zfmx.Queens;

import top.zfmx.CSP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 八皇后问题
 * 约束：任意两个皇后不能在同一行、同一列、同一对角线上
 * 变量：每个皇后的列
 * 值：每个皇后的行
 */
public class QueensSolve {
    public static void main(String[] args) {
        List<Integer> columns = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        Map<Integer, List<Integer>> rows = new HashMap<>();
        for(int column : columns){
            rows.put(column, List.of(1, 2, 3, 4, 5, 6, 7, 8));
        }
        CSP<Integer, Integer> csp = new CSP<>(columns, rows);
        csp.addConstraint(new QueensConstraint(columns));
        Map<Integer, Integer> solution = csp.backtrackingSearch();
        if (solution == null){
            System.out.println("No solution found!");
        } else {
            System.out.println(solution);
        }
    }
}
