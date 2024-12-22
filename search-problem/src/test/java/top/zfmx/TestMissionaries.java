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
}
