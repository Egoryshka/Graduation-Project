package com.romanovich.nn.training.strategy.genetic;

import com.romanovich.nn.network.NetworkContext;
import com.romanovich.nn.network.NeuralNetwork;
import com.romanovich.nn.training.error.ErrorFunction;
import com.romanovich.nn.training.scenario.TrainingEntry;
import com.romanovich.nn.training.scenario.TrainingSet;
import com.romanovich.nn.training.strategy.TrainingStrategy;
import com.romanovich.nn.training.strategy.genetic.operations.crossover.Crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public class GeneticAlgorithm implements TrainingStrategy {

    @Override
    public String train(NeuralNetwork network, NetworkContext context, TrainingSet trainData) {
        Random random = new Random(System.currentTimeMillis());
        int trainingDataSize = trainData.getExamples().size();
        int chromosomeSize = getChromosomeSize(network);
        List<Chromosome> population = initPopulation(context.getPopulationSize(), chromosomeSize, random);
        StringBuilder result = new StringBuilder();
        for (int index = 0; index < context.getIterations(); index++) {
            for (TrainingEntry entry : trainData.getExamples()) {
                population = trainPattern(network, context, entry, population, random);
            }
            TrainingEntry entry = trainData.getExamples().get(random.nextInt(trainingDataSize));
            calculateFitness(population, network, context, entry);
            population.sort(new Chromosome.ChromosomeComparator());
            System.out.println("Iteration = " + (index + 1) + "; Network error = " + population.get(0).getError());
            result.append("Generation = ").append(index + 1).append("; Network error = ").append(population.get(0).getError()).append("\n");
            if (context.getTerminationError() > population.get(0).getError()) {
                break;
            }
        }
        network.setSynapses(population.get(0).getGenes());
        return result.toString();
    }

    private List<Chromosome> trainPattern(NeuralNetwork network, NetworkContext context, TrainingEntry entry,
                                          List<Chromosome> population, Random random) {
        calculateFitness(population, network, context, entry);
        int parentsCount = population.size() / 2;
        List<Chromosome> survived = context.getSelector().select(population, parentsCount, random);
        List<Chromosome> newGeneration = Crossover.apply(survived, context.getCrossoverPointCount(), random);
        context.getMutator().apply(newGeneration, random);
        newGeneration.addAll(survived);
        return newGeneration;
    }

    private int getChromosomeSize(NeuralNetwork network) {
        int chromosomeSize = network.getInputVectorSize() * network.getLayer(0).getLayerSize();
        for (int i = 1; i < network.getNetworkLayersSize(); i++) {
            chromosomeSize += network.getLayer(i - 1).getLayerSize()
                    * network.getLayer(i).getLayerSize();
        }
        return chromosomeSize;
    }

    private List<Chromosome> initPopulation(int populationSize, int chromosomeSize, Random random) {
        int index = 0;
        List<Chromosome> population = new ArrayList<>();
        while (populationSize > index++) {
            double[] genes = new double[chromosomeSize];
            for (int i = 0; i < chromosomeSize; i++) {
                genes[i] = random.nextGaussian();
            }
            population.add(new Chromosome(genes));
        }
        return population;
    }

    private void calculateFitness(List<Chromosome> population, NeuralNetwork network,
                                  NetworkContext context, TrainingEntry entry) {
        ErrorFunction errorFunction = context.getErrorFunction();
        for (Chromosome chromosome : population) {
            if (chromosome.getError() == Double.POSITIVE_INFINITY) {
                network.setSynapses(chromosome.getGenes());
                double[] actual = network.proceed(entry.getInput());
                chromosome.setError(errorFunction.calcError(entry.getExpected(), actual));
            }
        }
    }
}
