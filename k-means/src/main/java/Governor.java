import java.util.List;

/**
 * 州长数据点类
 * 按年龄和经度对州长进行分类
 */
public class Governor extends DataPoint {
    private final double longitude;
    private final double age;
    private final String state;

    public Governor(double longitude, double age, String state){
        super(List.of(longitude, age));
        this.longitude = longitude;
        this.age = age;
        this.state = state;
    }
    @Override
    public String toString(){
        return state + ": (longitude: " + longitude + ", age: " + age + ")";
    }

}
