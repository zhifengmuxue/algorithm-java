package top.zfmx.WordGrid;

import top.zfmx.Constraint;

import java.util.*;

/**
 * 单词搜索约束
 * 约束：单词不能重叠
 */
public class WordSearchConstraintNoOverlap extends Constraint<String, List<WordGrid.GridLocation>> {
    public WordSearchConstraintNoOverlap(List<String> words){
        super(words);
    }

    @Override
    public boolean satisfied(Map<String, List<WordGrid.GridLocation>> assignment) {
        List<WordGrid.GridLocation> allLocations = assignment.values()
                .stream().flatMap(Collection::stream).toList();
        Set<WordGrid.GridLocation> allLocationsSet = new HashSet<>(allLocations);
        return allLocations.size() == allLocationsSet.size();
    }


}
