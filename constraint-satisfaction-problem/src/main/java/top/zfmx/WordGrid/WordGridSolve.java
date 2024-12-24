package top.zfmx.WordGrid;

import top.zfmx.CSP;

import java.util.*;

/**
 * 单词搜索问题
 * pizza游戏构建，将单词放入网格中，然后找到它们
 * 采用CSP将单词放入网格中
 * 约束：单词不能重叠
 * 变量：每个单词
 */
public class WordGridSolve {
    public static void main(String[] args) {
        WordGrid wordGrid = new WordGrid(9, 9);
        List<String> words = List.of("CONSUMER", "DAILY", "ANT", "ACCESS", "SALLY");

        Map<String, List<List<WordGrid.GridLocation>>> domains = new HashMap<>();
        for (String word : words){
            domains.put(word, wordGrid.generateDomain(word));
        }
        CSP<String, List<WordGrid.GridLocation>> csp = new CSP<>(words, domains);

        // 不重叠约束
//        csp.addConstraint(new WordSearchConstraintNoOverlap(words));

        // 重叠约束
        csp.addConstraint(new WordSearchConstraintOverlap(words));

        Map<String, List<WordGrid.GridLocation>> solution = csp.backtrackingSearch();
        if (solution == null){
            System.out.println("No solution found!");
        } else {
            Random random = new Random();
            for (Map.Entry<String, List<WordGrid.GridLocation>> item : solution.entrySet()){
                String word = item.getKey();
                List<WordGrid.GridLocation> locations = item.getValue();
                if (random.nextBoolean())
                    Collections.reverse(locations);
                wordGrid.mark(word, locations);
            }
            System.out.println(wordGrid);
        }
    }
}
