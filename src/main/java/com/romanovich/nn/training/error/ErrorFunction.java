package com.romanovich.nn.training.error;

/**
 * @author romanovich
 * @since 17.05.2017
 */
public interface ErrorFunction {

    /**
     * Calculate error of neuron network result during it training.
     *
     * @param expected result the neural network should return.
     * @param actual result returned by neuron network.
     * @return error of neural network.
     */
    double calcError(double[] expected, double[] actual);

    /**
     * Calculates error function derivative for certain output value.
     *
     * @param expected result the neural network should return.
     * @param actual result returned by neuron network.
     * @return error derivative.
     */
    double calcErrorDerivative(double expected, double actual);
}
