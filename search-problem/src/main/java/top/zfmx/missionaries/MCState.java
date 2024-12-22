package top.zfmx.missionaries;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class MCState implements Comparable<MCState> {
    private static final int MAX_NUM = 3;   // 传教士/食人族的最大数量
    private final int wm; // west bank missionaries
    private final int wc; // west bank cannibals
    private final int em; // east bank missionaries
    private final int ec; // east bank cannibals
    private final boolean boat; // 船在西岸还是东岸
    public MCState(int wm, int wc, boolean boat){
        this.wm = wm;
        this.wc = wc;
        this.em = MAX_NUM - wm;
        this.ec = MAX_NUM - wc;
        this.boat = boat;
    }

    @Override
    public String toString(){
        return String.format(
                "On the west bank there are %d missionaries and %d cannibals.%n" +
                "On the east bank there are %d missionaries and %d cannibals.%n" +
                "The boat is on the %s bank.",
                wm, wc, em, ec, boat ? "west" : "east");
    }

    public boolean goalTest(){
        return isLegal() && em == MAX_NUM && ec == MAX_NUM;
    }

    public boolean isLegal(){
        if (wm < wc && wm > 0) return false;
        return em >= ec || em <= 0;
    }

    // 生成下一步的状态
    public static List<MCState> successors(MCState mcState){
        List<MCState> success = new ArrayList<>();
        if(mcState.boat){
            if (mcState.wm > 1) success.add(new MCState(mcState.wm - 2, mcState.wc, false));
            if (mcState.wm > 0) success.add(new MCState(mcState.wm - 1, mcState.wc, false));
            if (mcState.wc > 1) success.add(new MCState(mcState.wm, mcState.wc - 2, false));
            if (mcState.wc > 0) success.add(new MCState(mcState.wm, mcState.wc - 1, false));
            if (mcState.wc > 0 && mcState.wm > 0)
                success.add(new MCState(mcState.wm - 1, mcState.wc - 1, false));
        }else{
            if (mcState.em > 1) success.add(new MCState(mcState.wm + 2, mcState.wc, true));
            if (mcState.em > 0) success.add(new MCState(mcState.wm + 1, mcState.wc, true));
            if (mcState.ec > 1) success.add(new MCState(mcState.wm, mcState.wc + 2, true));
            if (mcState.ec > 0) success.add(new MCState(mcState.wm, mcState.wc + 1, true));
            if (mcState.ec > 0 && mcState.em > 0)
                success.add(new MCState(mcState.wm + 1, mcState.wc + 1, true));
        }
        success.removeIf(Predicate.not(MCState::isLegal));
        return success;
    }

    public static void displaySolution(List<MCState> path){
        if (path.isEmpty())
            return;
        MCState oldState = path.getFirst();
        System.out.println(oldState);
        for (MCState currentState : path.subList(1, path.size())){
            if (currentState.boat){
                System.out.printf(
                        "%d missionaries and %d cannibals moved from the east bank to the west bank.%n%n",
                        oldState.em - currentState.em, oldState.ec - currentState.ec);
            } else {
                System.out.printf(
                        "%d missionaries and %d cannibals moved from the west bank to the east bank.%n%n",
                        oldState.wm - currentState.wm, oldState.wc - currentState.wc);
            }
            System.out.println(currentState);
            oldState = currentState;
        }
    }
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((boat) ? 1231 : 1237);
        result = prime * result + ec;
        result = prime * result + em;
        result = prime * result + wc;
        result = prime * result + wm;
        return result;
    }


    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MCState other = (MCState) obj;
        if (boat != other.boat) return false;
        if (ec != other.ec) return false;
        if (em != other.em) return false;
        if (wc != other.wc) return false;
        return wm == other.wm;
    }

    @Override
    public int compareTo(MCState o) {
        if (this.wm != o.wm) return Integer.compare(this.wm, o.wm);
        if (this.wc != o.wc) return Integer.compare(this.wc, o.wc);
        if (this.em != o.em) return Integer.compare(this.em, o.em);
        if (this.ec != o.ec) return Integer.compare(this.ec, o.ec);
        return Boolean.compare(this.boat, o.boat);
    }

    public double heuristic() {
        return em + ec - 2 * (wm + wc);
    }
}

