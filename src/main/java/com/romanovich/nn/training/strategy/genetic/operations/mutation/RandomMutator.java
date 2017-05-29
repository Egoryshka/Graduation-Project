package com.romanovich.nn.training.strategy.genetic.operations.mutation;

import com.romanovich.nn.training.strategy.genetic.Chromosome;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author romanovich
 * @since 24.05.2017
 */
public class RandomMutator extends AbstractMutator {

    public RandomMutator(double mutationProbability, int mutationCount) {
        super(mutationProbability, mutationCount);
    }

    @Override
    public void apply(List<Chromosome> population, Random random) {
        for (Chromosome chromosome : population) {
            if (random.nextDouble() <= getMutationProbability()) {
                mutateChromosome(chromosome, random);
            }
        }
    }

    private void mutateChromosome(Chromosome chromosome, Random random) {
        Iterator iter = random.doubles(getMutationCount(), -1.1, 1.1).iterator();
        double[] genes = chromosome.getGenes();
        for (int i = 0; i < getMutationCount(); i++) {
            int mutationIndex = random.nextInt(chromosome.getGenes().length);
            genes[mutationIndex] = (Double) iter.next();
        }
        chromosome.setGenes(genes);
    }
}
