import java.util.List;

/**
 * 专辑数据点类
 * 按时间长度和歌曲数量对专辑进行分类
 */
public class Album extends DataPoint{
    private final String name;
    private final int year;

    public Album(String name, int year,double length, double tracks){
        super(List.of(length, tracks));
        this.name = name;
        this.year = year;
    }

    @Override
    public String toString(){
        return name + " (" + year + ")";
    }
}
