package com.romanovich.nn.training.strategy.genetic.operations.selection;

import com.romanovich.nn.training.strategy.genetic.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public class TournamentSelection implements SelectionStrategy {
    @Override
    public List<Chromosome> select(List<Chromosome> population, int selectionSize, Random random) {
        List<Chromosome> selection = new ArrayList<>(selectionSize);
        for (int i = 0; i < selectionSize; i++) {
            Chromosome candidate1 = population.get(random.nextInt(population.size()));
            Chromosome candidate2 = population.get(random.nextInt(population.size()));

            selection.add(candidate1.getError() < candidate2.getError()
                    ? candidate1 : candidate2);
        }
        return selection;
    }
}
