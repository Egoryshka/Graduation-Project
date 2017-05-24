package com.romanovich.nn.training.strategy.genetic.operations.mutation;

import com.romanovich.nn.training.strategy.genetic.Chromosome;

import java.util.List;
import java.util.Random;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public class ExchangeMutator extends AbstractMutator {

    public ExchangeMutator(double mutationProbability, int mutationCount) {
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
        int point1 = random.nextInt(chromosome.getGenes().length);
        int point2 = random.nextInt(chromosome.getGenes().length);
        double[] genes = chromosome.getGenes();
        double tmp = genes[point1];
        genes[point1] = genes[point2];
        genes[point2] = tmp;
        chromosome.setGenes(genes);
    }
}
