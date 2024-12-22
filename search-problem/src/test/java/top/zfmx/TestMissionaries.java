package top.zfmx;

import org.junit.Test;
import top.zfmx.missionaries.MCState;
import top.zfmx.search.Node;
import top.zfmx.search.SearchFactory;
import top.zfmx.search.strategy.AdvancedSearchStrategy;

import java.util.List;

public class TestMissionaries {

    @Test
    public void testSolve(){
        SearchFactory searchFactory = new SearchFactory();
        System.out.println("================= bfs =====================");
        AdvancedSearchStrategy<MCState> bfsSearch = searchFactory.getAdvancedSearchStrategy("bfs", null);
        MCState startState = new MCState(3, 3, true);
        Node<MCState> solution = bfsSearch.search(startState, MCState::goalTest, MCState::successors);
        if (solution == null){
            System.out.println("No solution found!");
        } else {
            List<MCState> path = SearchFactory.nodeToPath(solution);
            MCState.displaySolution(path);
        }
        System.out.println("================= dfs =====================");
        AdvancedSearchStrategy<MCState> dfsSearch = searchFactory.getAdvancedSearchStrategy("dfs", null);
        solution = dfsSearch.search(startState, MCState::goalTest, MCState::successors);
        if (solution == null){
            System.out.println("No solution found!");
        } else {
            List<MCState> path = SearchFactory.nodeToPath(solution);
            MCState.displaySolution(path);
        }

        System.out.println("================= astar =====================");
        AdvancedSearchStrategy<MCState> astarSearch = searchFactory.getAdvancedSearchStrategy("astar", MCState::heuristic);
        solution = astarSearch.search(startState, MCState::goalTest, MCState::successors);
        if (solution == null){
            System.out.println("No solution found!");
        } else {
            List<MCState> path = SearchFactory.nodeToPath(solution);
            MCState.displaySolution(path);
        }
    }

    @Test
    public void testDifferentCount(){
        SearchFactory searchFactory = new SearchFactory();
        MCState startState = new MCState(5, 2, true);
        AdvancedSearchStrategy<MCState> bfsSearch = searchFactory.getAdvancedSearchStrategy("bfs", null);
        Node<MCState> solution = bfsSearch.search(startState, MCState::goalTest, MCState::successors);

        if (solution == null){
            System.out.println("No solution found!");
        } else {
            List<MCState> path = SearchFactory.nodeToPath(solution);
            MCState.displaySolution(path);
            System.out.println("BFS: " + bfsSearch.getCount());
        }
    }

    @Test
    public void testFindAllSolve(){
        // 找到从(3, 3, true)到(10, 10, false)的所有解
        SearchFactory searchFactory = new SearchFactory();
        AdvancedSearchStrategy<MCState> bfsSearch = searchFactory.getAdvancedSearchStrategy("bfs", null);

        for (int i = 3; i <= 10; i++){
            for (int j = 3; j <= 10; j++){
                MCState startState = new MCState(i, j, true);
                Node<MCState> solution = bfsSearch.search(startState, MCState::goalTest, MCState::successors);
                if (solution!=null){
                    System.out.println(i + " " + j);
                }
            }
        }

    }
}
