package top.zfmx.send_more_money;

import top.zfmx.CSP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SEND + MORE = MONEY 字谜问题
 * 找到字母背后的数字使等式成立
 */
public class SendMoreMoneySolve {
    public static void main(String[] args) {
        List<Character> letters = List.of('S', 'E', 'N', 'D', 'M', 'O', 'R','Y');
        Map<Character, List<Integer>> possibleDigits = new HashMap<>();
        for(Character letter : letters){
            possibleDigits.put(letter, List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        }
        possibleDigits.replace('M', List.of(1)); // M 不为 0 开头
        CSP<Character, Integer> csp = new CSP<>(letters, possibleDigits);
        csp.addConstraint(new SendMoreMoneyConstraint(letters));
        Map<Character, Integer> solution = csp.backtrackingSearch();
        if (solution == null){
            System.out.println("No solution found!");
        } else {
            System.out.println(solution);
        }
    }
}
