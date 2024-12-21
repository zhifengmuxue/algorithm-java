package top.zfmx;

import org.junit.Test;
import top.zfmx.maze.Maze;
import top.zfmx.search.SearchFactory;

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

        SearchFactory.Node<Maze.MazeLocation> solution = SearchFactory.dfs(
                maze.getStart(),
                maze::isGoal,
                maze::successors
        );

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
        SearchFactory.Node<Maze.MazeLocation> solution = SearchFactory.bfs(
                maze.getStart(),
                maze::isGoal,
                maze::successors
        );

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

        SearchFactory.Node<Maze.MazeLocation> solution = SearchFactory.aStar(
                maze.getStart(),
                maze::isGoal,
                maze::successors,
                maze::manhattanDistance
        );

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
        System.out.println(maze);
        System.out.println("====================================");

        SearchFactory.Node<Maze.MazeLocation> dfsSolution = SearchFactory.dfs(
                maze.getStart(),
                maze::isGoal,
                maze::successors
        );

        if (dfsSolution == null){
            System.out.println("No solution found!");
        } else {
            List<Maze.MazeLocation> dfsPath = SearchFactory.nodeToPath(dfsSolution);
            maze.mark(dfsPath);
            System.out.println(maze);
            maze.clear(dfsPath);
        }
        System.out.println("====================================");
        SearchFactory.Node<Maze.MazeLocation> bfsSolution = SearchFactory.bfs(
                maze.getStart(),
                maze::isGoal,
                maze::successors
        );

        if (bfsSolution == null){
            System.out.println("No solution found!");
        } else {
            List<Maze.MazeLocation> bfsPath = SearchFactory.nodeToPath(bfsSolution);
            maze.mark(bfsPath);
            System.out.println(maze);
            maze.clear(bfsPath);
        }
        System.out.println("====================================");

        SearchFactory.Node<Maze.MazeLocation> solution = SearchFactory.aStar(
                maze.getStart(),
                maze::isGoal,
                maze::successors,
                maze::manhattanDistance
        );

        if (solution == null){
            System.out.println("No solution found!");
        } else {
            List<Maze.MazeLocation> path = SearchFactory.nodeToPath(solution);
            maze.mark(path);
            System.out.println(maze);
            maze.clear(path);
        }
    }
}
