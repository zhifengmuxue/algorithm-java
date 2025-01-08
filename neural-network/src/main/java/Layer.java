import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;

public class Layer {
    public Optional<Layer> previousLayer;
    public List<Neuron> neurons = new ArrayList<>();
    public double[] outputCache;

    public Layer(Optional<Layer> previousLayer, int numNeurons,
                 double learningRate, DoubleUnaryOperator activationFunction,
                 DoubleUnaryOperator derivativeActivationFunction){
        this.previousLayer = previousLayer;
        Random random = new Random();
        for(int i = 0; i < numNeurons; i++){
            double[] randomWeights = null;
            if (previousLayer.isPresent()){
                randomWeights = random.doubles(previousLayer.get().neurons.size()).toArray();
            }
            neurons.add(new Neuron(randomWeights,learningRate,activationFunction,derivativeActivationFunction));
        }
        outputCache = new double[numNeurons];
    }

    /**
     * 计算输出
     * 如果有上一层，则使用上一层的输出作为输入
     * 否则，使用输入作为输入
     * @param inputs 输入
     * @return 输出
     */
    public double[] output(double[] inputs){
        if (previousLayer.isPresent()) {
            outputCache = neurons.stream().mapToDouble(n -> n.output(inputs)).toArray();
        }else{
            outputCache = inputs;
        }
        return outputCache;
    }

    public void calculateDeltasForOutputLayer(double[] expected){
        for(int i = 0; i < neurons.size(); i++){
            neurons.get(i).delta = neurons.get(i).derivativeActivationFunction
                    .applyAsDouble(neurons.get(i).outputCache) * (expected[i] - outputCache[i]);
        }
    }

    public void calculateDeltasForHiddenLayer(Layer nextLayer){
        for(int i = 0; i < neurons.size(); i++){
            int index = i;
            double[] nextWeights = nextLayer.neurons.stream().mapToDouble(n -> n.weights[index]).toArray();
            double[] nextDeltas = nextLayer.neurons.stream().mapToDouble(n -> n.delta).toArray();
            double sumWeightAndDelta = Util.dotProduct(nextWeights,nextDeltas);
            neurons.get(i).delta = neurons.get(i).derivativeActivationFunction
                    .applyAsDouble(neurons.get(i).outputCache) * sumWeightAndDelta;
        }
    }
}
