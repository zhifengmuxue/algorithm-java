package top.zfmx.WordGrid;

import top.zfmx.Constraint;

import java.util.*;
import java.util.stream.Collectors;

public class WordSearchConstraint extends Constraint<String, List<WordGrid.GridLocation>> {
    public WordSearchConstraint(List<String> words){
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
