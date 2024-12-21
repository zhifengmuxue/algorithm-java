package top.zfmx.gene;

import java.util.*;

/**
 * 问题：基因搜索
 * Gene类，用于存储基因序列
 * 测试类：GeneTest
 */
public class Gene {
    public ArrayList<Codon> codons = new ArrayList<>();
    public enum Nucleotide{
        A, C, G, T
    }

    // 构造方法：从字符串中创建Gene对象
    public Gene(String geneStr){
        for (int i = 0; i < geneStr.length() - 3; i += 3){
            codons.add(new Codon(geneStr.substring(i, i + 3)));
        }
    }

    public static class Codon implements Comparable<Codon>{
        public Nucleotide first, second, third;
        private final Comparator<Codon> comparator =
                Comparator.comparing((Codon c) -> c.first)
                .thenComparing(c -> c.second)
                .thenComparing(c -> c.third);

        // 从字符串中创建Codon对象
        public Codon (String codonStr){
            this.first = Nucleotide.valueOf(codonStr.substring(0, 1));
            this.second = Nucleotide.valueOf(codonStr.substring(1, 2));
            this.third = Nucleotide.valueOf(codonStr.substring(2, 3));
        }

        @Override
        public int compareTo(Codon other){
            return comparator.compare(this, other);
        }
    }

    /**
     * 线性搜索，复杂度O(n)
     * @param key 要搜索的Codon
     * @return 是否找到
     * @since 1.0 建议采用策略模式而非直接调用方法
     */
    public boolean linearContains(Codon key){
        for (Codon codon : codons){
            if (codon.compareTo(key) == 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 二分搜索，复杂度O(log n)
     * @param key 要搜索的Codon
     * @return 是否找到
     * @since 1.0 建议采用策略模式而非直接调用方法
     */
    public boolean binaryContains(Codon key){
        ArrayList<Codon> sortedCodons = new ArrayList<>(codons);
        Collections.sort(sortedCodons);
        int low = 0;
        int high = sortedCodons.size() - 1;
        // 基础不偏移二分
        while (low <= high){
            int middle = (low + high) / 2;
            int comparison = sortedCodons.get(middle).compareTo(key);
            if (comparison < 0){
                low = middle + 1;
            } else if (comparison > 0){
                high = middle - 1;
            } else {
                return true;
            }
        }
        return false;
    }

}
