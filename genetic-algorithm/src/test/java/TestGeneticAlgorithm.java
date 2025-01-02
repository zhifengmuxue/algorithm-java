import org.junit.Test;
import top.zfmx.framwork.GeneticAlgorithm;
import top.zfmx.list_compression.ListCompression;
import top.zfmx.pazzle.SendMoreMoney;
import top.zfmx.simple.SimpleEquation;

import java.util.ArrayList;
import java.util.List;

public class TestGeneticAlgorithm {
    @Test
    public void testWithSimpleEquation() {
        ArrayList<SimpleEquation> initialPopulation = new ArrayList<>();
        final int POPULATION_SIZE = 20;
        final int GENERATIONS = 100;
        final double THRESHOLD = 13.0;

        for (int i = 0; i < POPULATION_SIZE; i++) {
            initialPopulation.add(SimpleEquation.randomInstance());
        }
        GeneticAlgorithm<SimpleEquation> ga = new GeneticAlgorithm<>(
                initialPopulation, 0.1, 0.7,
                GeneticAlgorithm.SelectionType.TOURNAMENT
        );

        SimpleEquation result = ga.run(GENERATIONS, THRESHOLD);
        System.out.println(result);     // 输出结果 x=3, y=2
    }

    @Test
    public void testSendMoreMoney() {
        ArrayList<SendMoreMoney> initialPopulation = new ArrayList<>();
        final int POPULATION_SIZE = 1000;
        final int GENERATIONS = 1000;
        final double THRESHOLD = 1.0;

        for(int i = 0; i < POPULATION_SIZE; i++){
            initialPopulation.add(SendMoreMoney.randomInstance());
        }
        GeneticAlgorithm<SendMoreMoney> ga = new GeneticAlgorithm<>(
                initialPopulation, 0.2, 0.7,
                GeneticAlgorithm.SelectionType.TOURNAMENT
        );

        SendMoreMoney result = ga.run(GENERATIONS, THRESHOLD);
        System.out.println(result);     // 输出结果 SEND=9567, MORE=1085, MONEY=10652
    }

    @Test
    public void testListCompression(){
        ListCompression originalOrder = new ListCompression(ListCompression.ORIGINAL_LIST);
        System.out.println("Original Order: " + originalOrder);
        ArrayList<ListCompression> initialPopulation = new ArrayList<>();
        final int POPULATION_SIZE = 100;
        final int GENERATIONS = 100;
        final double THRESHOLD = 1.0;
        for(int i = 0; i < POPULATION_SIZE; i++){
            initialPopulation.add(ListCompression.randomInstance());
        }
        GeneticAlgorithm<ListCompression> ga = new GeneticAlgorithm<>(
                initialPopulation, 0.2, 0.7,
                GeneticAlgorithm.SelectionType.TOURNAMENT
        );
        ListCompression result = ga.run(GENERATIONS, THRESHOLD);
        System.out.println("Compressed Order: " + result);
    }
}
