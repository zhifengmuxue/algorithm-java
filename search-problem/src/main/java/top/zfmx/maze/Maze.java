package top.zfmx.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 迷宫类
 */
public class Maze {
    private final int rows, columns;
    private final MazeLocation start, goal;
    private Cell[][] grid;


    /**
     * 迷宫的参数
     */
    public enum Cell{
        EMPTY(" "),
        BLOCK("X"),
        START("S"),
        GOAL("G"),
        PATH("*");
        private final String code;
        Cell(String code){
            this.code = code;
        }
        @Override
        public String toString(){
            return code;
        }
    }

    /**
     * 标识迷宫各个位置的状态
     */
    public static class MazeLocation implements Comparable<MazeLocation>{
        public final int row, column;
        public MazeLocation(int row, int column){
            this.row = row;
            this.column = column;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + column;
            result = prime * result + row;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            MazeLocation other = (MazeLocation) obj;
            if (column != other.column)
                return false;
            return row == other.row;
        }

        @Override
        public int compareTo(MazeLocation o) {
            if (this.row != o.row) {
                return Integer.compare(this.row, o.row);
            }
            return Integer.compare(this.column, o.column);
        }

    }

    /**
     * 构造方法 创建一个迷宫
     * @param rows 行数
     * @param columns 列数
     * @param start 起点
     * @param goal 终点
     * @param sparseness 稀疏度
     */
    public Maze(int rows, int columns, MazeLocation start, MazeLocation goal, double sparseness){
        this.rows = rows;
        this.columns = columns;
        this.start = start;
        this.goal = goal;

        // 创建一个空的迷宫
        grid = new Cell[rows][columns];
        for (Cell[] row : grid){
            Arrays.fill(row, Cell.EMPTY);
        }

        // 随机填充迷宫
        randomlyFill(sparseness);
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    public Maze(){
        this(10, 10, new MazeLocation(0, 0), new MazeLocation(9, 9), 0.2);
    }


    // 随机填充迷宫
    private void randomlyFill(double sparseness){
        for (int row = 0; row<rows; row++){
            for (int column = 0; column<columns; column++){
                if (Math.random() < sparseness){
                    grid[row][column] = Cell.BLOCK;
                }
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Cell[] row : grid){
            for(Cell cell : row){
                sb.append(cell.toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    // 判断是否是迷宫的终点
    public boolean isGoal(MazeLocation ml){
        return ml.equals(goal);
    }

    /**
     * 获取一个位置的后继位置（迷宫内的移动方式）
     * @param ml 当前位置
     * @return  后继位置
     */
    public List<MazeLocation> successors(MazeLocation ml){
        List<MazeLocation> locations = new ArrayList<>();
        if (ml.row + 1 < rows && grid[ml.row + 1][ml.column] != Cell.BLOCK){
            locations.add(new MazeLocation(ml.row + 1, ml.column));
        }
        if (ml.row - 1 >= 0 && grid[ml.row - 1][ml.column] != Cell.BLOCK){
            locations.add(new MazeLocation(ml.row - 1, ml.column));
        }
        if (ml.column + 1 < columns && grid[ml.row][ml.column + 1] != Cell.BLOCK){
            locations.add(new MazeLocation(ml.row, ml.column + 1));
        }
        if (ml.column - 1 >= 0 && grid[ml.row][ml.column - 1] != Cell.BLOCK){
            locations.add(new MazeLocation(ml.row, ml.column - 1));
        }
        return locations;
    }

    /**
     * 标记路径
     * @param path 路径
     */
    public void mark(List<MazeLocation> path){
        for (MazeLocation ml : path){
            grid[ml.row][ml.column] = Cell.PATH;
        }
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    /**
     * 清除路径
     * @param path 路径
     */
    public void clear(List<MazeLocation> path){
        for (MazeLocation ml : path){
            grid[ml.row][ml.column] = Cell.EMPTY;
        }
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    public MazeLocation getStart() {
        return start;
    }

    public double euclideanDistance(MazeLocation ml){
        int xdist = ml.column - goal.column;
        int ydist = ml.row - goal.row;
        return Math.sqrt(xdist * xdist + ydist * ydist);
    }

    public double manhattanDistance(MazeLocation ml){
        return Math.abs(ml.column - goal.column) + Math.abs(ml.row - goal.row);
    }
}
