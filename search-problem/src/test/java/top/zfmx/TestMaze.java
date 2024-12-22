package top.zfmx;

import org.junit.Test;
import top.zfmx.maze.Maze;
import top.zfmx.search.Node;
import top.zfmx.search.SearchFactory;
import top.zfmx.search.strategy.AdvancedSearchStrategy;

import java.util.List;

public class TestMaze {

    @Test
    public void testCreateMaze(){
        Maze maze = new Maze();
        System.out.println(maze);
    }

    @Test
    public void testAll(){
        Maze maze = new Maze();
        SearchFactory searchFactory = new SearchFactory();
        System.out.println(maze);
        System.out.println("====================================");

        AdvancedSearchStrategy<Maze.MazeLocation> dfsSearch = searchFactory.getAdvancedSearchStrategy("dfs", null);
        Node<Maze.MazeLocation> dfsSolution = dfsSearch.search(maze.getStart(), maze::isGoal, maze::successors);

        if (dfsSolution == null){
            System.out.println("No solution found!");
        } else {
            List<Maze.MazeLocation> dfsPath = SearchFactory.nodeToPath(dfsSolution);
            maze.mark(dfsPath);
            System.out.println(maze);
            maze.clear(dfsPath);
        }
        System.out.println("====================================");

        AdvancedSearchStrategy<Maze.MazeLocation> bfsSearch = searchFactory.getAdvancedSearchStrategy("bfs", null);
        Node<Maze.MazeLocation> bfsSolution = bfsSearch.search(maze.getStart(), maze::isGoal, maze::successors);

        if (bfsSolution == null){
            System.out.println("No solution found!");
        } else {
            List<Maze.MazeLocation> bfsPath = SearchFactory.nodeToPath(bfsSolution);
            maze.mark(bfsPath);
            System.out.println(maze);
            maze.clear(bfsPath);
        }
        System.out.println("====================================");

        AdvancedSearchStrategy<Maze.MazeLocation> aStarSearch = searchFactory.getAdvancedSearchStrategy("astar", maze::manhattanDistance);
        Node<Maze.MazeLocation> aStarSolution = aStarSearch.search(maze.getStart(), maze::isGoal, maze::successors);

        if (aStarSolution == null){
            System.out.println("No solution found!");
        } else {
            List<Maze.MazeLocation> path = SearchFactory.nodeToPath(aStarSolution);
            maze.mark(path);
            System.out.println(maze);
            maze.clear(path);
        }

        // 返回各自的路径长度
        System.out.println("DFS: " + dfsSearch.getCount());
        System.out.println("BFS: " + bfsSearch.getCount());
        System.out.println("A*: " + aStarSearch.getCount());
    }

    /**
     * 测试100次迷宫, 比较三种搜索算法的性能
     */
    @Test
    public void testMaze100(){

        long dfsCount = 0L;
        long bfsCount = 0L;
        long aStarCount = 0L;

        for (int i = 0; i < 100; i++){
            Maze maze = new Maze();
            SearchFactory searchFactory = new SearchFactory();
            AdvancedSearchStrategy<Maze.MazeLocation> dfsSearch = searchFactory.getAdvancedSearchStrategy("dfs", null);
            Node<Maze.MazeLocation> dfsSolution = dfsSearch.search(maze.getStart(), maze::isGoal, maze::successors);
            dfsCount += dfsSearch.getCount();

            AdvancedSearchStrategy<Maze.MazeLocation> bfsSearch = searchFactory.getAdvancedSearchStrategy("bfs", null);
            Node<Maze.MazeLocation> bfsSolution = bfsSearch.search(maze.getStart(), maze::isGoal, maze::successors);
            bfsCount += bfsSearch.getCount();

            AdvancedSearchStrategy<Maze.MazeLocation> aStarSearch = searchFactory.getAdvancedSearchStrategy("astar", maze::manhattanDistance);
            Node<Maze.MazeLocation> aStarSolution = aStarSearch.search(maze.getStart(), maze::isGoal, maze::successors);
            aStarCount += aStarSearch.getCount();
        }
        System.out.println("DFS: " + dfsCount);
        System.out.println("BFS: " + bfsCount);
        System.out.println("A*: " + aStarCount);
    }
}
