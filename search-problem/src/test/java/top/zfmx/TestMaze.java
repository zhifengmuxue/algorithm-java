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
    public void testSolveMazeByDfs(){
        Maze maze = new Maze();
        System.out.println(maze);
        System.out.println("====================================");

        SearchFactory searchFactory = new SearchFactory();
        AdvancedSearchStrategy<Maze.MazeLocation> dfsSearch = searchFactory.getAdvancedSearchStrategy("dfs", null);
        Node<Maze.MazeLocation> solution = dfsSearch.search(maze.getStart(), maze::isGoal, maze::successors);

        if (solution == null){
            System.out.println("No solution found!");
        } else {
            List<Maze.MazeLocation> path = SearchFactory.nodeToPath(solution);
            maze.mark(path);
            System.out.println(maze);
            maze.clear(path);
        }
    }

    @Test
    public void testSolveMazeByBfs(){
        Maze maze = new Maze();
        System.out.println(maze);
        System.out.println("====================================");

        SearchFactory searchFactory = new SearchFactory();
        AdvancedSearchStrategy<Maze.MazeLocation> bfsSearch = searchFactory.getAdvancedSearchStrategy("bfs", null);
        Node<Maze.MazeLocation> solution = bfsSearch.search(maze.getStart(), maze::isGoal, maze::successors);

        if (solution == null){
            System.out.println("No solution found!");
        } else {
            List<Maze.MazeLocation> path = SearchFactory.nodeToPath(solution);
            maze.mark(path);
            System.out.println(maze);
            maze.clear(path);
        }
    }

    @Test
    public void testSolveMazeByAStar(){
        Maze maze = new Maze();
        System.out.println(maze);
        System.out.println("====================================");

        SearchFactory searchFactory = new SearchFactory();
        AdvancedSearchStrategy<Maze.MazeLocation> aStarSearch = searchFactory.getAdvancedSearchStrategy("astar", maze::manhattanDistance);
        Node<Maze.MazeLocation> solution = aStarSearch.search(maze.getStart(), maze::isGoal, maze::successors);
        if (solution == null){
            System.out.println("No solution found!");
        } else {
            List<Maze.MazeLocation> path = SearchFactory.nodeToPath(solution);
            maze.mark(path);
            System.out.println(maze);
            maze.clear(path);
        }
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
    }
}
