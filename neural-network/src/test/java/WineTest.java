import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WineTest {
    private List<double[]> wineParameters = new ArrayList<>();
    private List<double[]> wineClassifications = new ArrayList<>();
    private List<Integer> wineSpecies = new ArrayList<>();

    public WineTest(){
        List<String[]> wineDataset = Util.loadCSV("wine.csv");
        Collections.shuffle(wineDataset);
        for(String[] wine : wineDataset){
            double[] parameters = Arrays.stream(wine).skip(1)
                    .mapToDouble(Double::parseDouble).toArray();
            wineParameters.add(parameters);
            int species = Integer.parseInt(wine[0]);
            switch (species){
                case 1:
                    wineClassifications.add(new double[]{1.0,0.0,0.0});
                    break;
                case 2:
                    wineClassifications.add(new double[]{0.0,1.0,0.0});
                    break;
                default:
                    wineClassifications.add(new double[]{0.0,0.0,1.0});
                    break;
            }
            wineSpecies.add(species);
        }
        Util.normalizeByFeatureScaling(wineParameters);
    }

    public Integer wineInterpretOutput(double[] output){
        double max = Util.max(output);
        if (max == output[0]) {
            return 1;
        }else if(max == output[1]){
            return 2;
        }else{
            return 3;
        }
    }

    public Network<Integer>.Results classify(){
        Network<Integer> wineNetwork = new Network<>(new int[]{13,7,3},0.9,
                Util::sigmoid,Util::derivativeSigmoid);
        List<double[]> wineTrainers = wineParameters.subList(0, 150);
        List<double[]> wineTrainersCorrects = wineClassifications.subList(0, 150);
        int trainingIterations = 50;
        for(int i = 0; i < trainingIterations; i++){
            wineNetwork.train(wineTrainers,wineTrainersCorrects);
        }
        List<double[]> wineTesters = wineParameters.subList(150, 178);
        List<Integer> wineTestersCorrects = wineSpecies.subList(150, 178);
        return wineNetwork.validate(wineTesters,wineTestersCorrects,this::wineInterpretOutput);
    }

    public static void main(String[] args){
        WineTest wineTest = new WineTest();
        Network<Integer>.Results results = wineTest.classify();
        System.out.println(results.correct + " correct of " + results.trials + " = " +
                results.percentage * 100 + "%");
    }
}
