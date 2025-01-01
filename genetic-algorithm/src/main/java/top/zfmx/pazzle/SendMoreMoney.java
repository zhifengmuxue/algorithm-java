package top.zfmx.pazzle;

import top.zfmx.framwork.Chromosome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SendMoreMoney extends Chromosome<SendMoreMoney> {
    private List<Character> letters;
    private Random random;

    public SendMoreMoney(List<Character> letters){
        this.letters = letters;
        this.random = new Random();
    }

    public static SendMoreMoney randomInstance(){
        List<Character> letters = new ArrayList<>(
                List.of('S', 'E', 'N', 'D', 'M', 'O', 'R', 'Y',' ', ' '));
        Collections.shuffle(letters);
        return new SendMoreMoney(letters);
    }

    /**
     * 适应度函数
     * difference 是MONEY - (SEND + MORE)的绝对值
     * 标识了SEND + MORE 和 MONEY 之间的差距，我们需要
     * 最小化这个差距
     * @return 适应度
     */
    @Override
    public double fitness() {
        int s = letters.indexOf('S');
        int e = letters.indexOf('E');
        int n = letters.indexOf('N');
        int d = letters.indexOf('D');
        int m = letters.indexOf('M');
        int o = letters.indexOf('O');
        int r = letters.indexOf('R');
        int y = letters.indexOf('Y');
        int send = s * 1000 + e * 100 + n * 10 + d;
        int more = m * 1000 + o * 100 + r * 10 + e;
        int money = m * 10000 + o * 1000 + n * 100 + e * 10 + y;
        int difference = Math.abs(money - (send + more));
        return 1.0 / (difference + 1);
    }

    /**
     * 交换操作
     * @param other 另一个染色体
     * @return 交换后的两个染色体
     */
    @Override
    public List<SendMoreMoney> crossover(SendMoreMoney other) {
        SendMoreMoney child1 = new SendMoreMoney(new ArrayList<>(letters));
        SendMoreMoney child2 = new SendMoreMoney(new ArrayList<>(other.letters));
        int pos1 = random.nextInt(letters.size());
        int pos2 = random.nextInt(letters.size());
        Character l1 = child1.letters.get(pos1);
        Character l2 = child2.letters.get(pos2);
        int pos3 = child1.letters.indexOf(l2);
        int pos4 = child2.letters.indexOf(l1);
        Collections.swap(child1.letters, pos1, pos3);
        Collections.swap(child2.letters, pos2, pos4);
        return List.of(child1, child2);
    }

    @Override
    public void mutate() {
        int pos1 = random.nextInt(letters.size());
        int pos2 = random.nextInt(letters.size());
        Collections.swap(letters, pos1, pos2);
    }

    @Override
    public SendMoreMoney copy() {
        return new SendMoreMoney(new ArrayList<>(letters));
    }

    @Override
    public String toString(){
        int s = letters.indexOf('S');
        int e = letters.indexOf('E');
        int n = letters.indexOf('N');
        int d = letters.indexOf('D');
        int m = letters.indexOf('M');
        int o = letters.indexOf('O');
        int r = letters.indexOf('R');
        int y = letters.indexOf('Y');
        int send = s * 1000 + e * 100 + n * 10 + d;
        int more = m * 1000 + o * 100 + r * 10 + e;
        int money = m * 10000 + o * 1000 + n * 100 + e * 10 + y;
        return String.format(
                "SEND: %d, MORE: %d, MONEY: %d, Difference: %d",
                send, more, money, Math.abs(money - (send + more))
        );
    }
}
