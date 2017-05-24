package com.romanovich.nn.layer.neuron.activation;

/**
 * Activation function based on logistic function (sigmoid).
 *
 * @author romanovich
 * @since 17.05.2017
 */
public class SigmoidFunction implements ActivationFunction {

    private double alpha;

    public SigmoidFunction(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public double calc(double signal) {
        return 1 / (1 + Math.exp(-1 * this.alpha * signal));
    }

    @Override
    public double calcFirstDerivative(double signal) {
        return alpha * this.calc(signal) * (1 - this.calc(signal));
    }
}
