package com.romanovich.nn.training.strategy.genetic.operations.crossover;

import com.romanovich.nn.training.strategy.genetic.Chromosome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public class Crossover {

    private Crossover() {

    }

    public static List<Chromosome> apply(List<Chromosome> candidates, int crossoverPointCount, Random random) {
        List<Chromosome> selectionClone = new ArrayList<>(candidates);
        Collections.shuffle(selectionClone, random);

        List<Chromosome> result = new ArrayList<>(candidates.size());
        Iterator<Chromosome> iterator = selectionClone.iterator();

        while (iterator.hasNext()) {
            Chromosome parent1 = iterator.next();
            if (iterator.hasNext()) {
                Chromosome parent2 = iterator.next();
                if (crossoverPointCount > 0) {
                    result.addAll(cross(parent1, parent2, crossoverPointCount, random));
                } else {
                    result.add(parent1);
                    result.add(parent2);
                }
            } else {
                result.add(parent1);
            }
        }
        return result;
    }

    private static List<Chromosome> cross(Chromosome parent1, Chromosome parent2,
                                         int crossoverPointCount, Random random) {
        final int chromosomeLength = parent1.getGenes().length;

        double[] offspring1 = new double[chromosomeLength];
        System.arraycopy(parent1.getGenes(), 0, offspring1, 0, chromosomeLength);
        double[] offspring2 = new double[chromosomeLength];
        System.arraycopy(parent2.getGenes(), 0, offspring2, 0, chromosomeLength);

        double[] temp = new double[chromosomeLength];
        for (int i = 0; i < crossoverPointCount; i++) {
            int crossoverIndex = (1 + random.nextInt(chromosomeLength - 1));
            System.arraycopy(offspring1, 0, temp, 0, crossoverIndex);
            System.arraycopy(offspring2, 0, offspring1, 0, crossoverIndex);
            System.arraycopy(temp, 0, offspring2, 0, crossoverIndex);
        }
        List<Chromosome> result = new ArrayList<>();
        result.add(new Chromosome(offspring1));
        result.add(new Chromosome(offspring1));

        return result;
    }
}
