package com.romanovich.nn.training.strategy.genetic.operations.mutation;

/**
 * @author romanovich
 * @since 24.05.2017
 */
public abstract class AbstractMutator implements Mutator {

    private double mutationProbability;
    private int mutationCount;

    protected AbstractMutator(double mutationProbability, int mutationCount) {
        this.mutationProbability = mutationProbability;
        this.mutationCount = mutationCount;
    }

    protected double getMutationProbability() {
        return mutationProbability;
    }

    protected int getMutationCount() {
        return mutationCount;
    }
}
