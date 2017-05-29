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
        int chromosomeSize = getChromosomeSize(network);
        List<Chromosome> population = initPopulation(context.getPopulationSize(), chromosomeSize, random);
        int parentsCount = population.size() / 2;

        calculateFitness(population, network, context, trainData);
        StringBuilder result = new StringBuilder();
        for (int generation = 0; generation < context.getIterations(); generation++) {
            List<Chromosome> parent = context.getSelector().select(population, parentsCount, random);
            population.addAll(Crossover.apply(parent, context.getCrossoverPointCount(), random));
            context.getMutator().apply(population, random);

            calculateFitness(population, network, context, trainData);
            population.sort(new Chromosome.ChromosomeComparator());
            population = population.subList(0, context.getPopulationSize() - 1);

            System.out.println("Generation = " + (generation + 1) + "; Network error = " + population.get(0).getError());
            result.append("Generation = ").append(generation + 1).append("; Network error = ").append(population.get(0).getError()).append("\n");
            if (context.getTerminationError() > population.get(0).getError()) {
                break;
            }
        }
        network.setSynapses(population.get(0).getGenes());
        return result.toString();
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
            double[] genes = random.doubles(chromosomeSize, -1.1, 1.1).toArray();
            population.add(new Chromosome(genes));
        }
        return population;
    }

    private void calculateFitness(List<Chromosome> population, NeuralNetwork network,
                                            NetworkContext context, TrainingSet set) {
        ErrorFunction errorFunction = context.getErrorFunction();
        int trainingSetSize = set.getExamples().size();
        for (Chromosome chromosome : population) {
            if (chromosome.getError() == Double.POSITIVE_INFINITY) {
                double errorSum = 0;
                network.setSynapses(chromosome.getGenes());
                for (TrainingEntry entry : set.getExamples()) {
                    double[] actual = network.proceed(entry.getInput());
                    errorSum += errorFunction.calcError(entry.getExpected(), actual);
                }
                chromosome.setError(errorSum / trainingSetSize);
            }
        }
    }
}
