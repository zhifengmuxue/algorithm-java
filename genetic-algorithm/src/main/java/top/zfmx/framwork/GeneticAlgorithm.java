package top.zfmx.framwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm<C extends Chromosome<C>> {

    // 指定选择方法： 轮盘赌选择（适应度比例选择） 或 锦标赛选择
    public enum SelectionType{
        ROULETTE, TOURNAMENT;
    }

    private ArrayList<C> population;            // 种群
    private double mutationRate;                // 变异概率
    private double crossoverChance;             // 交叉概率
    private SelectionType selectionType;        // 选择方法
    private Random random;                      // 随机数生成器

    /**
     * 构造函数
     * @param initialPopulation 初始种群
     * @param mutationChance 变异概率
     * @param crossoverChance 交叉概率
     * @param selectionType 选择方法
     */
    public GeneticAlgorithm(List<C> initialPopulation, double mutationChance,
                            double crossoverChance, SelectionType selectionType){
        this.population = new ArrayList<>(initialPopulation);
        this.mutationRate = mutationChance;
        this.crossoverChance = crossoverChance;
        this.selectionType = selectionType;
        this.random = new Random();
    }

    /**
     * 轮盘式选择
     * @param wheel 适应度比例数组
     * @param numPicks 选择的个体数量
     * @return 选择的个体
     */
    private List<C> pickRoulette(double[] wheel, int numPicks){
        List<C> picks = new ArrayList<>();
        for (int i = 0; i < numPicks; i++){
            double pick = random.nextDouble();
            for (int j = 0; j < wheel.length; j++){
                pick -= wheel[j];
                if (pick <= 0){
                    picks.add(population.get(j));
                    break;
                }
            }
        }
        return picks;
    }

    /**
     * 锦标赛选择
     * @param numParticipants 参与比赛的个体数量
     * @param numPicks 选择的个体数量
     * @return 选择的个体
     */
    private List<C> pickTournament(int numParticipants, int numPicks){
        Collections.shuffle(population);        // 打乱种群
        List<C> tournament = population.subList(0, numParticipants);    // 选取numParticipants个个体
        tournament.sort(Collections.reverseOrder());            // 适应度从大到小排序
        return tournament.subList(0, numPicks);               // 选取适应度最大的numPicks个个体
    }

    /**
     * 进化
     * @param numPicks 选择的个体数量
     */
    private void reproduceAndReplace(int numPicks){
        ArrayList<C> nextPopulation = new ArrayList<>();
        while(nextPopulation.size() < population.size()){
            List<C> parents;
            if (selectionType == SelectionType.ROULETTE){   // 轮盘赌选择
                double totalFitness = population.stream().mapToDouble(C::fitness).sum();
                double[] wheel = population.stream().mapToDouble(c->c.fitness()/totalFitness).toArray();
                parents = pickRoulette(wheel, numPicks);
            }else {                                         // 锦标赛选择
                parents = pickTournament(population.size()/2, numPicks);
            }

            // 交换操作
            if (random.nextDouble() < crossoverChance) {
                C parent1 = parents.get(0);
                C parent2 = parents.get(1);
                nextPopulation.addAll(parent1.crossover(parent2));
            }else{
                nextPopulation.addAll(parents);
            }
        }

        // 如果下一代个体数量大于种群数量，则删除适应度较低的个体
        if (nextPopulation.size() > population.size()){
            nextPopulation.removeFirst();
        }

        // 替换种群
        population = nextPopulation;
    }


}
