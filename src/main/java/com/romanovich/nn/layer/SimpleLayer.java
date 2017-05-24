package com.romanovich.nn.layer;

import com.romanovich.nn.layer.neuron.Neuron;
import com.romanovich.nn.layer.neuron.NeuronImpl;
import com.romanovich.nn.layer.neuron.activation.ActivationFunction;
import com.romanovich.nn.layer.neuron.activation.SigmoidFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author romanovich
 * @since 16.05.2017
 */
public class SimpleLayer implements Layer {

    private int inputVectorCount;
    private List<Neuron> neurons;
    private double[] outputVector;

    public SimpleLayer(int inputVectorCount, int layerSize, double activationParam) {
        this.inputVectorCount = inputVectorCount;
        ActivationFunction function = new SigmoidFunction(activationParam);
        this.neurons = new ArrayList<>();
        for (int i = 0; i < layerSize; i++) {
            neurons.add(new NeuronImpl(function, inputVectorCount));
        }
    }

    @Override
    public Neuron getNeuron(int index) {
        return this.neurons.get(index);
    }

    public int getInputSignalsCount() {
        return this.inputVectorCount;
    }

    public int getLayerSize() {
        return this.neurons.size();
    }

    public double[] getLayerOutputVector() {
        return this.outputVector;
    }

    public double[] calculateOutputVector(double[] input) {
        outputVector = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            outputVector[i] = neurons.get(i).proceed(input);
        }
        return outputVector;
    }
}
