package com.romanovich.nn.business;

import com.romanovich.nn.network.NetworkContext;
import com.romanovich.nn.network.NeuralNetwork;
import com.romanovich.nn.training.TrainerFactory;
import com.romanovich.nn.training.scenario.ScenarioService;
import com.romanovich.nn.training.scenario.TrainingSet;
import com.romanovich.nn.training.strategy.TrainingStrategy;
import com.romanovich.nn.utils.converter.ImageDataConverter;
import com.romanovich.nn.utils.converter.NumberConverter;

/**
 * @author romanovich
 * @since 26.05.2017
 */
public class ImageResolver {

    NeuralNetwork neuralNetwork;
    NetworkContext context;
    boolean isReady = false;

    public ImageResolver(NetworkContext context) {
        this.context = context;
        neuralNetwork = new NeuralNetwork(context.getResolution().getImageSize(),
                context.getOutputCount(), context.getHiddenLayers(), context.getActivationParam());
    }

    public String trainNetwork() {
        TrainingStrategy trainer = TrainerFactory.getTrainer(context.getTrainerType());
        TrainingSet trainingSet = ScenarioService.getInstance().loadTrainingScenario(context.getResolution());
        String result = trainer.train(neuralNetwork, context, trainingSet);
        isReady = true;
        return result;
    }

    public int proceed(String filePath) {
        if (isReady) {
            double[] input = (new ImageDataConverter(context.getResolution())).convert(filePath);
            double[] output = neuralNetwork.proceed(input);
            return NumberConverter.convertBack(output);
        }
        return -1;
    }


}
