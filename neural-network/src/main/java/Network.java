import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

public class Network<T> {
    private List<Layer> layers = new ArrayList<>();

    /**
     * 初始化网络
     * @param layerStructure 网络结构
     * @param learningRate 学习率
     * @param activationFunction 激活函数
     * @param derivativeActivationFunction 激活函数的导数
     */
    public Network(int[] layerStructure, double learningRate,
                   DoubleUnaryOperator activationFunction,
                   DoubleUnaryOperator derivativeActivationFunction){
        if(layerStructure.length < 3){
            throw new IllegalArgumentException("Layer structure must have at least 3 layers");
        }
        layers.add(new Layer(Optional.empty(),layerStructure[0],
                learningRate,activationFunction,derivativeActivationFunction));
        for(int i = 1; i < layerStructure.length; i++){
            layers.add(new Layer(Optional.of(layers.get(i-1)),layerStructure[i],
                    learningRate,activationFunction,derivativeActivationFunction));
        }
    }

    /**
     * 正向传播
     * @param inputs 输入
     * @return 输出
     */
    private double[] output(double[] inputs){
        double[] result = inputs;
        for (Layer layer : layers){
            result = layer.output(result);
        }
        return result;
    }

    /**
     * 反向传播
     * @param expected 期望输出
     */
    private void backPropagate(double[] expected){
        int lastLayer = layers.size() - 1;
        layers.get(lastLayer).calculateDeltasForOutputLayer(expected);
        for(int i = lastLayer - 1; i > 0; i--){
            layers.get(i).calculateDeltasForHiddenLayer(layers.get(i+1));
        }
    }

    /**
     * 更新权重
     */
    private void updateWeights(){
        for(Layer layer : layers.subList(1,layers.size())){
            for(Neuron neuron : layer.neurons){
                for(int i = 0; i < neuron.weights.length; i++){
                    neuron.weights[i] += neuron.learningRate * neuron.delta
                            * layer.previousLayer.get().outputCache[i];
                }
            }
        }
    }

    // 训练网络
    public void train(List<double[]> inputs, List<double[]> expected){
        for(int i = 0; i < inputs.size(); i++){
            double[] xs = inputs.get(i);
            double[] ys = expected.get(i);
            output(xs);
            backPropagate(ys);
            updateWeights();
        }
    }

    public class Results{
        public final int correct;
        public final int trials;
        public final double percentage;

        public Results(int correct, int trials, double percentage){
            this.correct = correct;
            this.trials = trials;
            this.percentage = percentage;
        }
    }

    public Results validate(List<double[]> inputs, List<T> expecteds, Function<double[], T> interpret){
        int correct = 0;
        for(int i = 0; i < inputs.size(); i++){
            double[] input = inputs.get(i);
            T expected = expecteds.get(i);
            T result = interpret.apply(output(input));
            if (expected.equals(result)){
                correct++;
            }
        }
        double percentage = (double) correct / (double) inputs.size();
        return new Results(correct, inputs.size(), percentage);
    }
}
