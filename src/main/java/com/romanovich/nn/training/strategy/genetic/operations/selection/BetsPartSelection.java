package com.romanovich.nn.training.strategy.genetic.operations.selection;

import com.romanovich.nn.training.strategy.genetic.Chromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author romanovich
 * @since 24.05.2017
 */
public class BetsPartSelection implements SelectionStrategy {
    @Override
    public List<Chromosome> select(List<Chromosome> population, int selectionSize, Random random) {
        List<Chromosome> clonePopulation = new ArrayList<>(population);
        clonePopulation.sort(new Chromosome.ChromosomeComparator());
        return clonePopulation.subList(0, selectionSize);
    }
}
