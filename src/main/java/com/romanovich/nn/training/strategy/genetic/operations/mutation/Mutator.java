package com.romanovich.nn.training.strategy.genetic.operations.mutation;

import com.romanovich.nn.training.strategy.genetic.Chromosome;

import java.util.List;
import java.util.Random;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public interface Mutator {

    void apply(List<Chromosome> population, Random random);
}
