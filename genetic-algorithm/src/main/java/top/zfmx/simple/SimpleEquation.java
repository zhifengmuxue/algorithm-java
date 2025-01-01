package top.zfmx.simple;

import top.zfmx.framwork.Chromosome;

import java.util.List;
import java.util.Random;

/**
 * 简单的方程式
 * 通过简单的方程式来演示遗传算法
 * 适应度函数为： 6x - x^2 + 4y - y^2
 * 我们找到方程式的最大值时，x y 等于什么
 */
public class SimpleEquation extends Chromosome<SimpleEquation> {
    private int x,y;
    private static final int MAX_START = 100;
    public SimpleEquation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static SimpleEquation randomInstance(){
        Random random = new Random();
        return new SimpleEquation(random.nextInt(MAX_START), random.nextInt(MAX_START));
    }

    @Override
    public double fitness(){
        return 6 * x - x * x + 4 * y - y * y;
    }

    @Override
    public List<SimpleEquation> crossover(SimpleEquation other){
        SimpleEquation child1 = new SimpleEquation(this.x, other.y);
        SimpleEquation child2 = new SimpleEquation(other.x, this.y);
        return List.of(child1, child2);
    }

    /**
     * 变异操作
     * 以50%的概率在x或y上增加或减少1
     */
    @Override
    public void mutate(){
        Random random = new Random();
        if (random.nextDouble() > 0.5){
            if ( random.nextDouble() > 0.5){
                x++;
            } else {
                x--;
            }
        } else {
            if (random.nextDouble() > 0.5){
                y++;
            } else {
                y--;
            }
        }
    }

    @Override
    public SimpleEquation copy(){
        return new SimpleEquation(x, y);
    }

    @Override
    public String toString(){
        return "X: " + x + " Y: " + y + " Fitness: " + fitness();
    }
}
