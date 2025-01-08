import java.util.function.DoubleUnaryOperator;

/**
 * 神经元类
 * 用于表示一个神经元
 */
public class Neuron {
    public double[] weights;
    public final double learningRate;
    public double outputCache;
    public double delta;
    public final DoubleUnaryOperator activationFunction;
    public final DoubleUnaryOperator derivativeActivationFunction;

    public Neuron(double[] weights, double learningRate,
                  DoubleUnaryOperator activationFunction,
                  DoubleUnaryOperator derivativeActivationFunction){
        this.weights = weights;
        this.learningRate = learningRate;
        this.outputCache = 0.0;
        this.delta = 0.0;
        this.activationFunction = activationFunction;
        this.derivativeActivationFunction = derivativeActivationFunction;
    }

    // 计算输出
    public double output(double[] inputs){
        outputCache = Util.dotProduct(inputs,weights);
        return activationFunction.applyAsDouble(outputCache);
    }
}
