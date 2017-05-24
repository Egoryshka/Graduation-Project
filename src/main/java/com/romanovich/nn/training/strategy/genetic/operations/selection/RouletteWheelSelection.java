package com.romanovich.nn.training.strategy.genetic.operations.selection;

import com.romanovich.nn.training.strategy.genetic.Chromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public class RouletteWheelSelection implements SelectionStrategy {

    @Override
    public List<Chromosome> select(List<Chromosome> population, int selectionSize, Random random) {
        double[] cumulativeFitness = new double[population.size()];
        cumulativeFitness[0] = 1 / population.get(0).getError();
        for (int i = 1; i < population.size(); i++)
        {
            cumulativeFitness[i] = cumulativeFitness[i - 1] + 1 / population.get(i).getError();
        }

        List<Chromosome> selection = new ArrayList<>(selectionSize);
        for (int i = 0; i < selectionSize; i++)
        {
            double randomFitness = random.nextDouble() * cumulativeFitness[cumulativeFitness.length - 1];
            int index = Arrays.binarySearch(cumulativeFitness, randomFitness);
            if (index < 0)
            {
                // Convert negative insertion point to array index.
                index = Math.abs(index + 1);
            }
            selection.add(population.get(index));
        }
        return selection;
    }
}
