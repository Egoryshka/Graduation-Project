package com.romanovich.nn.training.strategy.genetic.operations.selection;

import com.romanovich.nn.training.strategy.genetic.Chromosome;

import java.util.List;
import java.util.Random;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public interface SelectionStrategy {

    List<Chromosome> select(List<Chromosome> population, int selectionSize, Random random);
}
