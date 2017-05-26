package com.romanovich.nn.training;

import com.romanovich.nn.training.strategy.TrainerType;
import com.romanovich.nn.training.strategy.TrainingStrategy;
import com.romanovich.nn.training.strategy.genetic.GeneticAlgorithm;
import com.romanovich.nn.training.strategy.propagation.BackPropagation;

/**
 * @author romanovich
 * @since 26.05.2017
 */
public final class TrainerFactory {

    private TrainerFactory() {

    }

    public static TrainingStrategy getTrainer(TrainerType type) {
        switch (type) {
            case GENETIC:
                return new GeneticAlgorithm();
            case BACK_PROPAGATION:
            default:
                return new BackPropagation();
        }
    }
}
