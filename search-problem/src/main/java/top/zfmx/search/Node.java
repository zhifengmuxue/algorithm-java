package top.zfmx.search;

public class  Node<T> implements Comparable<Node<T>> {
    private final T state;
    private Node<T> parent;
    private double cost;    // 从初始状态到当前状态的代价
    private double heuristic;  // 启发式值
    public Node(T state, Node<T> parent){
        this.state = state;
        this.parent = parent;
    }
    public Node(T state, Node<T> parent, double cost, double heuristic){
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
    }
    @Override
    public int compareTo(Node<T> other){
        Double mine = cost + heuristic;
        Double theirs = other.cost + other.heuristic;
        return mine.compareTo(theirs);
    }

    public T getState() {
        return state;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }
}
