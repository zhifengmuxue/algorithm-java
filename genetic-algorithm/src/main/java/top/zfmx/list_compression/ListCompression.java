package top.zfmx.list_compression;

import top.zfmx.framwork.Chromosome;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

public class ListCompression extends Chromosome<ListCompression> {
    public static final List<String> ORIGINAL_LIST = List.of(
            "Michael","Sarah","Joshua","Narine","David","Sajid",
            "Melanie","Daniel", "Wei","Dean","Brian","Murat","Lisa"
    );

    private List<String> list;
    private Random random;

    public ListCompression(List<String> list){
        this.list = list;
        this.random = new Random();
    }

    /**
     * 随机生成一个实例
     * @return 随机生成的实例
     */
    public static ListCompression randomInstance(){
        ArrayList<String> tempList = new ArrayList<>(ORIGINAL_LIST);
        Collections.shuffle(tempList);  // 打乱顺序
        return new ListCompression(tempList);
    }

    /**
     * 计算压缩后的字节数
     * @return 压缩后的字节数
     */
    private int bytesCompressed(){
        try{
            ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayInputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
            return byteArrayInputStream.size();
        }catch (IOException e){
            System.out.println("Error: " + e);
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取压缩后的字节数
     * @return 压缩后的字节数
     */
    @Override
    public double fitness() {
        return 1.0 / bytesCompressed();
    }

    /**
     * 交叉操作
     * @param other 另一个染色体
     * @return 交叉后的两个染色体
     */
    @Override
    public List<ListCompression> crossover(ListCompression other) {
        ListCompression child1 = new ListCompression(new ArrayList<>(list));
        ListCompression child2 = new ListCompression(new ArrayList<>(list));
        int pos1 = random.nextInt(list.size());
        int pos2 = random.nextInt(other.list.size());
        String s1 = list.get(pos1);
        String s2 = other.list.get(pos2);
        int pos3 = list.indexOf(s2);
        int pos4 = other.list.indexOf(s1);
        Collections.swap(child1.list, pos1, pos3);
        Collections.swap(child2.list, pos2, pos4);
        return List.of(child1, child2);
    }

    @Override
    public void mutate() {
        int pos1 = random.nextInt(list.size());
        int pos2 = random.nextInt(list.size());
        Collections.swap(list, pos1, pos2);
    }

    @Override
    public ListCompression copy() {
        return new ListCompression(new ArrayList<>(list));
    }

    @Override
    public String toString(){
        return "Order: " + list + " Bytes: " + bytesCompressed();
    }
}
