package com.romanovich.nn.layer.neuron;

/**
 * @author romanovich
 * @since 15.05.2017
 */
public interface Neuron {

    double getSynapse(int index);

    void setSynapse(double value, int synapseIndex);

    /**
     * Calculates neuron callback.
     *
     * @param signals input vector.
     * @return neuron callback.
     */
    double proceed(double[] signals);

    /**
     * Returns value that summarized from input signals.
     *
     * @return summarized value.
     */
    double getNeuronInput();

    /**
     * Gets neuron activation Function.
     *
     * @return activation function.
     */
    double getFirstDerivativeBySummarized();
}
