package top.zfmx.WordGrid;

import java.util.ArrayList;
import java.util.List;

/**
 * 单词网格
 */
public class WordGrid {
    private final char ALPHABET_LENGTH = 26;
    private final char FIRST_LETTER = 'A';
    private final int rows, columns;
    private char[][] grid;
    /**
     * 网格位置
     */
    public static class GridLocation{
        public final int row;
        public final int column;
        public GridLocation(int row, int column){
            this.row = row;
            this.column = column;
        }

        @Override
        public int hashCode(){
            final int prime = 31;
            int result = 1;
            result = prime * result + column;
            result = prime * result + row;
            return result;
        }

        @Override
        public boolean equals(Object obj){
            if (this == obj){
                return true;
            }
            if (obj == null){
                return false;
            }
            if (getClass() != obj.getClass()){
                return false;
            }
            GridLocation other = (GridLocation) obj;
            if(column != other.column){
                return false;
            }
            return row == other.row;
        }
    }

    // 初始化网格
    public WordGrid(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        grid = new char[rows][columns];

        // 初始化随机字母
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                grid[i][j] = (char) (FIRST_LETTER + (int) (Math.random() * ALPHABET_LENGTH));
            }
        }
    }

    // 标记单词
    public void mark(String word, List<GridLocation> locations){
        for(int i = 0; i < word.length(); i++){
            GridLocation location = locations.get(i);
            grid[location.row][location.column] = word.charAt(i);
        }
    }


    /**
     * 生成单词的位置
     * @param word 单词
     * @return 单词位置
     */
    public List<List<GridLocation>> generateDomain(String word){
        List<List<GridLocation>> domain = new ArrayList<>();
        int length = word.length();
        for (int row = 0; row < rows; row++){
            for (int column = 0 ; column < columns; column++){
                if (column + length <= columns){
                    fillRight(domain, row, column, length);
                    if (row + length <= rows){
                        fillDiagonalRight(domain, row, column, length);
                    }
                }
                if (row + length <= rows){
                    fillDown(domain, row, column, length);
                    if (column - length >= 0){
                        fillDiagonalLeft(domain, row, column, length);
                    }
                }
            }
        }
        return domain;
    }

    /**
     * 填充右边
     * @param domain 单词位置
     * @param row 行
     * @param column 列
     * @param length 长度
     */
    private void fillRight(List<List<GridLocation>> domain, int row, int column, int length){
        List<GridLocation> locations = new ArrayList<>();
        for(int c = column; c < column + length; c++){
            locations.add(new GridLocation(row, c));
        }
        domain.add(locations);
    }

    /**
     * 填充右下角
     * @param domain 单词位置
     * @param row 行
     * @param column 列
     * @param length 长度
     */
    private void fillDiagonalRight(List<List<GridLocation>> domain, int row, int column, int length){
        List<GridLocation> locations = new ArrayList<>();
        int r = row;
        for(int c = column; c < column + length; c++){
            locations.add(new GridLocation(r++, c));
        }
        domain.add(locations);
    }

    /**
     * 填充下边
     * @param domain 单词位置
     * @param row 行
     * @param column 列
     * @param length 长度
     */
    private void fillDown(List<List<GridLocation>> domain, int row, int column, int length){
        List<GridLocation> locations = new ArrayList<>();
        for(int r = row; r < row + length; r++){
            locations.add(new GridLocation(r, column));
        }
        domain.add(locations);
    }


    /**
     * 填充左下角
     * @param domain 单词位置
     * @param row 行
     * @param column 列
     * @param length 长度
     */
    private void fillDiagonalLeft(List<List<GridLocation>> domain, int row, int column, int length){
        List<GridLocation> locations = new ArrayList<>();
        int r = row;
        for(int c = column; c > column - length; c--){
            locations.add(new GridLocation(r++, c));
        }
        domain.add(locations);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(char[] row : grid){
            sb.append(row);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
