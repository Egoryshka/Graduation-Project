package com.romanovich.nn;

import com.romanovich.nn.training.error.ErrorFunction;
import com.romanovich.nn.training.error.ErrorFunctionFactory;
import com.romanovich.nn.training.error.ErrorFunctionType;
import com.romanovich.nn.training.strategy.genetic.operations.MutationType;
import com.romanovich.nn.training.strategy.genetic.operations.OperatorFactory;
import com.romanovich.nn.training.strategy.genetic.operations.SelectionType;
import com.romanovich.nn.training.strategy.genetic.operations.mutation.Mutator;
import com.romanovich.nn.training.strategy.genetic.operations.selection.SelectionStrategy;
import com.romanovich.nn.utils.ImgResolution;
import com.romanovich.nn.utils.converter.DataConverter;
import com.romanovich.nn.utils.converter.ImageNumberConverter;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public class NetworkContext {

    //Network params
    private ImgResolution resolution;
    private int outputCount;
    private int[] hiddenLayers;
    private double activationParam;
    private DataConverter converter;

    //Training params
    private ErrorFunction errorFunction;

    //Back Propagation
    private int eras;
    private double trainingRate;
    private double regularization;

    //Genetic Engine
    private int populationSize;
    private int iterations;
    private double terminationError;
    private SelectionStrategy selector;
    private int crossoverPointCount;
    private Mutator mutator;

    private NetworkContext() {

    }

    public static NetworkContext buildBackPropagationContext(ImgResolution resolution, int outputCount, int[] hiddenLayers,
                                                      double activationParam, ErrorFunctionType errorFunctionType,
                                                      int eras, double trainingRate, double regularization) {
        NetworkContext context = setNetworkParams(resolution, outputCount, hiddenLayers, activationParam);
        context.errorFunction = ErrorFunctionFactory.getErrorFunctionFunction(errorFunctionType);
        context.eras = eras;
        context.trainingRate = trainingRate;
        context.regularization = regularization;
        return context;
    }

    public static NetworkContext buildGeneticContext(ImgResolution resolution, int outputCount, int[] hiddenLayers,
                                              double activationParam, ErrorFunctionType errorFunctionType,
                                              int populationSize, int iterations, double terminationError,
                                              SelectionType selectionType, int crossoverPointCount,
                                              double mutationProbability, int mutationsCount, MutationType mutationType) {
        NetworkContext context = setNetworkParams(resolution, outputCount, hiddenLayers, activationParam);
        context.errorFunction = ErrorFunctionFactory.getErrorFunctionFunction(errorFunctionType);
        context.populationSize = populationSize;
        context.iterations = iterations;
        context.terminationError = terminationError;
        context.selector = OperatorFactory.getSelector(selectionType);
        context.crossoverPointCount = crossoverPointCount;
        context.mutator = OperatorFactory.getMutator(mutationType, mutationProbability, mutationsCount);
        return context;
    }

    public ImgResolution getResolution() {
        return resolution;
    }

    public int getOutputCount() {
        return outputCount;
    }

    public int[] getHiddenLayers() {
        return hiddenLayers;
    }

    public double getActivationParam() {
        return activationParam;
    }

    public DataConverter getConverter() {
        return converter;
    }

    public ErrorFunction getErrorFunction() {
        return errorFunction;
    }

    public int getEras() {
        return eras;
    }

    public double getTrainingRate() {
        return trainingRate;
    }

    public double getRegularization() {
        return regularization;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getIterations() {
        return iterations;
    }

    public double getTerminationError() {
        return terminationError;
    }

    public SelectionStrategy getSelector() {
        return selector;
    }

    public int getCrossoverPointCount() {
        return crossoverPointCount;
    }

    public Mutator getMutator() {
        return mutator;
    }

    private static NetworkContext setNetworkParams(ImgResolution resolution, int outputCount, int[] hiddenLayers,
                                            double activationParam) {
        NetworkContext context = new NetworkContext();
        context.resolution = resolution;
        context.outputCount = outputCount;
        context.hiddenLayers = hiddenLayers;
        context.activationParam = activationParam;
        context.converter = new ImageNumberConverter(resolution);
        return context;
    }

    // min == -1.0134158815514511
    // max == 0.7837617798033267
}