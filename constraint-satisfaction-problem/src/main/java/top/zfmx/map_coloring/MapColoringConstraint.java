package top.zfmx.map_coloring;

import top.zfmx.Constraint;

import java.util.List;
import java.util.Map;


/**
 * 地图着色约束
 */
public final class MapColoringConstraint extends Constraint<String, String> {
    private final String place1;
    private final String place2;

    public MapColoringConstraint(String place1, String place2){
        super(List.of(place1, place2));
        this.place1 = place1;
        this.place2 = place2;
    }


    /**
     * 检查约束是否满足，相邻的地区不能着相同的颜色
     * @param assignment 赋值
     * @return 是否满足
     */
    @Override
    public boolean satisfied(Map<String, String> assignment){
        if (!assignment.containsKey(place1) || !assignment.containsKey(place2)){
            return true;
        }
        return !assignment.get(place1).equals(assignment.get(place2));
    }
}
