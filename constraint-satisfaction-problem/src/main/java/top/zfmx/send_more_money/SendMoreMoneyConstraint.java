package top.zfmx.send_more_money;

import top.zfmx.Constraint;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class SendMoreMoneyConstraint extends Constraint<Character, Integer> {
    private List<Character> letters;
    public SendMoreMoneyConstraint(List<Character> letters){
        super(letters);
        this.letters = letters;
    }

    @Override
    public boolean satisfied(Map<Character, Integer> assignment) {
        if ((new HashSet<>(assignment.values())).size() < assignment.size())
            return false;
        if (assignment.size() == letters.size()){
            int s = assignment.get('S');
            int e = assignment.get('E');
            int n = assignment.get('N');
            int d = assignment.get('D');
            int m = assignment.get('M');
            int o = assignment.get('O');
            int r = assignment.get('R');
            int y = assignment.get('Y');
            int send = s * 1000 + e * 100 + n * 10 + d;
            int more = m * 1000 + o * 100 + r * 10 + e;
            int money = m * 10000 + o * 1000 + n * 100 + e * 10 + y;
            return send + more == money;
        }
        return true;
    }
}
