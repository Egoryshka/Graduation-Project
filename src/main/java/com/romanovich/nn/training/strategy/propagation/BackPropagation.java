package com.romanovich.nn.training.strategy.propagation;

import com.romanovich.nn.layer.Layer;
import com.romanovich.nn.layer.neuron.Neuron;
import com.romanovich.nn.network.NetworkContext;
import com.romanovich.nn.network.NeuralNetwork;
import com.romanovich.nn.training.scenario.TrainingEntry;
import com.romanovich.nn.training.scenario.TrainingSet;
import com.romanovich.nn.training.strategy.TrainingStrategy;

/**
 * @author romanovich
 * @since 21.05.2017
 */
public class BackPropagation implements TrainingStrategy {

    @Override
    public String train(NeuralNetwork network, NetworkContext context, TrainingSet trainData) {
        final int scenarioSize = trainData.getExamples().size();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < context.getEras(); i++) {
            double error = 0;
            for (TrainingEntry entry : trainData.getExamples()) {
                error += trainPattern(network, context, entry, scenarioSize);
            }
            System.out.println("Epoch = " + (i + 1) + "; Network error = " + error / scenarioSize);
            result.append("Epoch = ").append(i + 1).append("; Network error = ").append(error / scenarioSize).append("\\n");

        }
        return result.toString();
    }

    private double trainPattern(NeuralNetwork network, NetworkContext context,
                                TrainingEntry entry, int scenarioSize) {
        if (entry.getInput() == null || entry.getInput().length != network.getInputVectorSize()
                || entry.getExpected() == null || entry.getExpected().length != network.getOutputVectorSize()) {
            throw new IllegalArgumentException();
        }

        int networkSize = network.getNetworkLayersSize();
        double[] actual = network.proceed(entry.getInput());
        Layer outputLayer = network.getLayer(networkSize - 1);

        double[] dEdZ = new double[outputLayer.getLayerSize()];
        for (int neuronIndex = 0; neuronIndex < outputLayer.getLayerSize(); neuronIndex++) {
            dEdZ[neuronIndex] = context.getErrorFunction().calcErrorDerivative(entry.getExpected()[neuronIndex], actual[neuronIndex])
                    * outputLayer.getNeuron(neuronIndex).getFirstDerivativeBySummarized();
            double[] offset = new double[outputLayer.getInputSignalsCount()];
            for (int synapseIndex = 0; synapseIndex < outputLayer.getInputSignalsCount(); synapseIndex++) {
                offset[synapseIndex] = context.getTrainingRate() * ((dEdZ[neuronIndex]
                        * (networkSize > 1
                        ? network.getLayer(networkSize - 2).getLayerOutputVector()[synapseIndex]
                        : entry.getInput()[synapseIndex]))
                        + (context.getRegularization() * outputLayer.getNeuron(neuronIndex).getSynapse(synapseIndex) / scenarioSize));
            }
            adjustNeuronSynapses(outputLayer.getNeuron(neuronIndex), offset);
        }

        for (int hiddenIdx = networkSize - 2; hiddenIdx >= 0; hiddenIdx--) {
            Layer hidden = network.getLayer(hiddenIdx);
            double[] layerErrors = new double[hidden.getLayerSize()];
            for (int neuronIdx = 0; neuronIdx < hidden.getLayerSize(); neuronIdx++) {
                for (int synapseIdx = 0; synapseIdx < network.getLayer(hiddenIdx + 1).getLayerSize(); synapseIdx++) {
                    layerErrors[neuronIdx] += network.getLayer(hiddenIdx + 1).getNeuron(synapseIdx).getSynapse(neuronIdx)
                            * dEdZ[synapseIdx];
                }

                layerErrors[neuronIdx] *= hidden.getNeuron(neuronIdx).getFirstDerivativeBySummarized();
                double[] offset = new double[hidden.getInputSignalsCount()];
                for (int synapseIdx = 0; synapseIdx < network.getLayer(hiddenIdx + 1).getLayerSize(); synapseIdx++) {
                    offset[synapseIdx] = context.getTrainingRate() * ((layerErrors[neuronIdx]
                            * (hiddenIdx > 0
                            ? network.getLayer(hiddenIdx - 1).getLayerOutputVector()[synapseIdx]
                            : entry.getInput()[synapseIdx]))
                            + (context.getRegularization() * hidden.getNeuron(neuronIdx).getSynapse(synapseIdx) / scenarioSize));
                }
                adjustNeuronSynapses(hidden.getNeuron(neuronIdx), offset);
            }
            dEdZ = layerErrors;
        }
        return context.getErrorFunction().calcError(entry.getExpected(), network.proceed(entry.getInput()));
    }

    private void adjustNeuronSynapses(Neuron neuron, double[] offset) {
        for (int i = 0; i < offset.length; i++) {
            neuron.setSynapse(neuron.getSynapse(i) - offset[i], i);
        }
    }
}
