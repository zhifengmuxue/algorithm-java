import org.junit.Test;
import top.zfmx.framwork.GeneticAlgorithm;
import top.zfmx.simple.SimpleEquation;

import java.util.ArrayList;

public class TestGeneticAlgorithm {
    @Test
    public void testWithSimpleEquation(){
        ArrayList<SimpleEquation> initialPopulation = new ArrayList<>();
        final int POPULATION_SIZE = 20;
        final int GENERATIONS = 100;
        final double THRESHOLD = 13.0;

        for(int i = 0; i < POPULATION_SIZE; i++){
            initialPopulation.add(SimpleEquation.randomInstance());
        }
        GeneticAlgorithm<SimpleEquation> ga = new GeneticAlgorithm<>(
                initialPopulation, 0.1, 0.7,
                GeneticAlgorithm.SelectionType.TOURNAMENT
        );

        SimpleEquation result = ga.run(GENERATIONS, THRESHOLD);
        System.out.println(result);     // 输出结果 x=3, y=2
    }
}
