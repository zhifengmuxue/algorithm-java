package top.zfmx.WordGrid;

import top.zfmx.Constraint;

import java.util.List;
import java.util.Map;

public class WordSearchConstraintOverlap extends Constraint<String, List<WordGrid.GridLocation>> {
    public WordSearchConstraintOverlap(List<String> words){
        super(words);
    }

    @Override
    public boolean satisfied(Map<String, List<WordGrid.GridLocation>> assignment) {
        List<WordGrid.GridLocation> allLocations = assignment.values()
                .stream().flatMap(List::stream).toList();

        for (int i = 0; i < allLocations.size(); i++){
            for (int j = i + 1; j < allLocations.size(); j++){
                WordGrid.GridLocation loc1 = allLocations.get(i);
                WordGrid.GridLocation loc2 = allLocations.get(j);
                if (loc1.row == loc2.row && loc1.column == loc2.column){
                    return false;
                }
            }
        }
        return true;
    }



}
