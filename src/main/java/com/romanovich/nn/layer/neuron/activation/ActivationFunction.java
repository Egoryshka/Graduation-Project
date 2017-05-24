package com.romanovich.nn.layer.neuron.activation;

/**
 * @author romanovich
 * @since 17.05.2017
 */
public interface ActivationFunction {

    /**
     * Activates summarized signal.
     *
     * @param signal summarized signal
     * @return activated signal.
     */
    double calc(double signal);

    /**
     * Calculates first derivative for activation function
     * for summarized signal. Used for back propagation training.
     *
     * @param signal summarized input signal.
     * @return the first derivative result.
     */
    double calcFirstDerivative(double signal);
}
