import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IrisTest {
    public static final String IRIS_SETOSA = "Iris-setosa";
    public static final String IRIS_VERSICOLOR = "Iris-versicolor";
    public static final String IRIS_VIRGINICA = "Iris-virginica";

    private List<double[]> irisParameters = new ArrayList<>();
    private List<double[]> irisClassifications = new ArrayList<>();
    private List<String> irisSpecies = new ArrayList<>();
    public IrisTest(){
        List<String[]> irisData = Util.loadCSV("iris.csv");
        Collections.shuffle(irisData);
        for (String[] iris : irisData){
            double[] parameters = Arrays.stream(iris).limit(4)
                    .mapToDouble(Double::parseDouble).toArray();
            irisParameters.add(parameters);
            String species = iris[4];
            switch (species){
                case IRIS_SETOSA:
                    irisClassifications.add(new double[]{1.0,0.0,0.0});
                    break;
                case IRIS_VERSICOLOR:
                    irisClassifications.add(new double[]{0.0,1.0,0.0});
                    break;
                case IRIS_VIRGINICA:
                    irisClassifications.add(new double[]{0.0,0.0,1.0});
                    break;
            }
            irisSpecies.add(species);
        }
        Util.normalizeByFeatureScaling(irisParameters);
    }

    public String irisInterpretOutput(double[] output){
        double max = Util.max(output);
        if (max == output[0]) {
            return IRIS_SETOSA;
        }else if(max == output[1]){
            return IRIS_VERSICOLOR;
        }else{
            return IRIS_VIRGINICA;
        }
    }

    public Network<String>.Results classify(){
        Network<String> irisNetwork = new Network<>(new int[]{4,6,3},0.3,
                Util::sigmoid,Util::derivativeSigmoid);
        List<double[]> irisTrainers = irisParameters.subList(0, 140);
        List<double[]> irisTrainersCorrects = irisClassifications.subList(0, 140);
        int trainingIterations = 50;
        for(int i = 0; i < trainingIterations; i++){
            irisNetwork.train(irisTrainers,irisTrainersCorrects);
        }
        List<double[]> irisTesters = irisParameters.subList(140, 150);
        List<String> irisTestersCorrects = irisSpecies.subList(140, 150);
        return irisNetwork.validate(irisTesters,irisTestersCorrects,this::irisInterpretOutput);
    }

    public static void main(String[] args){
        IrisTest irisTest = new IrisTest();
        Network<String>.Results results = irisTest.classify();
        System.out.println(results.correct + " correct of " + results.trials + " = " +
                results.percentage * 100 + "%");
    }
}
