package com.romanovich.nn.training.strategy.genetic.operations.selection;

import com.romanovich.nn.training.strategy.genetic.Chromosome;

import java.util.ArrayList;
import java.util.Collections;
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
        Collections.shuffle(population, random);
        for (int i = 0; i < selectionSize; i += 2) {
            Chromosome candidate1 = population.get(i);
            Chromosome candidate2 = population.get(i + 1);

            selection.add(candidate1.getError() < candidate2.getError() ? candidate1 : candidate2);
        }
        return selection;
    }
}
