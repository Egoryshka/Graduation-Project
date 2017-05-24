package com.romanovich.nn.layer;

import com.romanovich.nn.layer.neuron.Neuron;

/**
 * @author romanovich
 * @since 16.05.2017
 */
public interface Layer {

    /**
     * Gets neuron by index.
     *
     * @return list of neurons.
     */
    Neuron getNeuron(int index);

    /**
     * Gets the size of an input vector.
     * This value equals the count of synapses in every neuron of this layer.
     *
     * @return size of an input vector.
     */
    int getInputSignalsCount();

    /**
     * Gets neurons count.
     * @return layer size.
     */
    int getLayerSize();

    /**
     * Gets layer output result.
     * Used for training.
     *
     * @return array of {@code double}.
     */
    double[] getLayerOutputVector();

    /**
     * Calculates layer output signal.
     * @param input input vector.
     * @return output vector.
     */
    double[] calculateOutputVector(double[] input);

}
