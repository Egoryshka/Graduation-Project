package com.romanovich.nn.network;

import com.romanovich.nn.layer.Layer;
import com.romanovich.nn.layer.SimpleLayer;
import com.romanovich.nn.layer.neuron.Neuron;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of feedforward neural network.
 *
 * @author romanovich
 * @since 16.05.2017
 */
public class NeuralNetwork {

    private int inputVectorSize;
    private int outputVectorSize;
    private List<Layer> network;

    /**
     * Creates new feedforward neural network and initializes it by default.
     *
     * @param inputVectorSize  input signals count.
     * @param outputVectorSize output signals count.
     *                         Also this value defines count of neurons at last output layer.
     * @param hiddenLayers     array of integer values that defines neuron count at corresponding layer.
     *                         E.g. [2, 5, 3], this mean that neural network would be created
     *                         with 3 hidden layers. The first hidden layer will have 2 neurons.
     *                         The second hidden layer will have 5 neurons. And the third hidden
     *                         layer will have 3 neurons.
     *                         If empty array would be got, the neural network would be created without
     *                         hidden layers.
     * @param logistic         a parameter of neuron activation function.
     */
    public NeuralNetwork(int inputVectorSize, int outputVectorSize, int[] hiddenLayers, double logistic) {
        this.inputVectorSize = inputVectorSize;
        this.outputVectorSize = outputVectorSize;
        initLayers(inputVectorSize, outputVectorSize, hiddenLayers, logistic);
    }

    public int getInputVectorSize() {
        return inputVectorSize;
    }

    public int getOutputVectorSize() {
        return outputVectorSize;
    }

    public Layer getLayer(int index) {
        return network.get(index);
    }

    public int getNetworkLayersSize() {
        return this.network.size();
    }

    public double[] proceed(double[] input) {
        double[] result = input.clone();
        for (Layer layer : this.network) {
            result = layer.calculateOutputVector(result);
        }
        return result;
    }

    private void initLayers(int inputVectorSize,int outputVectorSize, int[] hiddenLayers, double logistic) {
        int[] layersDefinition = getLayersDefinition(inputVectorSize, outputVectorSize, hiddenLayers);
        this.network = new ArrayList<>();
        for (int i = 1; i < layersDefinition.length; i++) {
            this.network.add(new SimpleLayer(layersDefinition[i - 1], layersDefinition[i], logistic));
        }

    }

    private int[] getLayersDefinition(int inputVectorSize, int outputVectorSize, int[] hiddenLayers) {
        int[] layersDefinition = new int[hiddenLayers.length + 2];
        layersDefinition[0] = inputVectorSize;
        System.arraycopy(hiddenLayers, 0, layersDefinition, 1, hiddenLayers.length);
        layersDefinition[layersDefinition.length - 1] = outputVectorSize;
        return layersDefinition;
    }

    public void setSynapses(double[] synapses) {
        int index = 0;
        for (int layerIdx = 0; layerIdx < network.size(); layerIdx++) {
            Layer layer = network.get(layerIdx);
            int synapseCount = layerIdx == 0 ? inputVectorSize : network.get(layerIdx - 1).getLayerSize();
            for (int neuronIdx = 0; neuronIdx < layer.getLayerSize(); neuronIdx++) {
                Neuron neuron = layer.getNeuron(neuronIdx);
                for (int synapseIdx = 0; synapseIdx < synapseCount; synapseIdx++) {
                    neuron.setSynapse(synapses[index++], synapseIdx);
                }
            }
        }
    }
}
