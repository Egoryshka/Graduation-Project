package com.romanovich.nn.layer.neuron.activation;

/**
 * Activation function based on hyperbolic tangens.
 *
 * @author romanovich
 * @since 17.05.2017
 */
public class HyperbolicTangensFunction implements ActivationFunction {

    private double alpha;

    public HyperbolicTangensFunction(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public double calc(double signal) {
        return Math.tanh(alpha * signal);
    }

    @Override
    public double calcFirstDerivative(double signal) {
        return alpha * (1 - Math.pow(this.calc(signal), 2));
    }
}
