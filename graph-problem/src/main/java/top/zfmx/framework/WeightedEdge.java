package top.zfmx.framework;

/**
 * 加权边
 */
public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {
    public final double weight;

    public WeightedEdge(int u, int v, double weight) {
        super(u, v);
        this.weight = weight;
    }

    @Override
    public WeightedEdge reverse() {
        return new WeightedEdge(v, u, weight);
    }

    @Override
    public int compareTo(WeightedEdge o) {
        Double mine = weight;
        Double other = o.weight;
        return mine.compareTo(other);
    }

    @Override
    public String toString() {
        return u + " " + weight + " > " + v;
    }
}
