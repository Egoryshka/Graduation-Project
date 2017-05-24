package com.romanovich.nn.training.strategy.genetic.operations.mutation;

import com.romanovich.nn.training.strategy.genetic.Chromosome;

import java.util.List;
import java.util.Random;

/**
 * @author romanovich
 * @since 24.05.2017
 */
public class GaussMutator extends AbstractMutator {

    public GaussMutator(double mutationProbability, int mutationCount) {
        super(mutationProbability, mutationCount);
    }

    @Override
    public void apply(List<Chromosome> population, Random random) {
        for (Chromosome chromosome : population) {
            if (random.nextDouble() <= getMutationProbability()) {
                for (int i = 0; i < getMutationCount(); i++) {
                    mutateChromosome(chromosome, random);
                }
            }
        }
    }

    private void mutateChromosome(Chromosome chromosome, Random random) {
        int mutationIndex = random.nextInt(chromosome.getGenes().length);
        double[] genes = chromosome.getGenes();
        genes[mutationIndex] = random.nextDouble() * genes[mutationIndex] * 2;
        chromosome.setGenes(genes);
    }


}
