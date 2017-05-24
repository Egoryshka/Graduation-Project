package com.romanovich.nn.layer.neuron;

import com.romanovich.nn.layer.neuron.activation.ActivationFunction;

/**
 * @author romanovich
 * @since 15.05.2017
 */
public class NeuronImpl implements Neuron {

    private ActivationFunction activationFunction;
    private int inputCount;
    private double[] synapses;
    private double summarizedSignal;

    public NeuronImpl(ActivationFunction function, int inputCount) {
        this.activationFunction = function;
        this.inputCount = inputCount;
        initNeuron(inputCount);
    }

    public double getSynapse(int index) {
        return this.synapses[index];
    }

    public void setSynapse(double value, int synapseIndex) {
        this.synapses[synapseIndex] = value;
    }

    public double proceed(double[] signals) {
        double signal = summarizeSignals(signals);
        return activateSignal(signal);
    }

    /**
     * Summarize input vector using synaptic weights.
     *
     * @param signals input vector.
     * @return summarized signal.
     */
    private double summarizeSignals(double[] signals) {
        double result = 0D;
        for (int i = 0; i < this.inputCount; i++) {
            result += signals[i] * this.synapses[i];
        }
        this.summarizedSignal = result;
        return this.summarizedSignal;
    }

    /**
     * Activates summarized signal.
     *
     * @param signal signal value.
     * @return neuron output value.
     */
    private Double activateSignal(Double signal) {
        return activationFunction.calc(signal);
    }

    /**
     * Initializes synaptic weights with random values in range [0, 1)
     * on neuron creation.
     *
     * @param inputSize input vector size.
     */
    private void initNeuron(int inputSize) {
        this.synapses = new double[inputSize];
        for (int i = 0; i < inputSize; i++) {
            this.synapses[i] = Math.random();
        }
    }

    @Override
    public double getNeuronInput() {
        return this.summarizedSignal;
    }

    @Override
    public double getFirstDerivativeBySummarized() {
        return activationFunction.calcFirstDerivative(summarizedSignal);
    }
}