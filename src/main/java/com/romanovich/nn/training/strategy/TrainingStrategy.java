package com.romanovich.nn.training.strategy;

import com.romanovich.nn.NetworkContext;
import com.romanovich.nn.network.NeuralNetwork;
import com.romanovich.nn.training.scenario.TrainingSet;

/**
 * @author romanovich
 * @since 21.05.2017
 */
public interface TrainingStrategy {

    void train(NeuralNetwork network, NetworkContext context, TrainingSet trainData);
}
