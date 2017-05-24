package com.romanovich.nn.training.strategy.genetic.operations;

import com.romanovich.nn.training.strategy.genetic.operations.mutation.ExchangeMutator;
import com.romanovich.nn.training.strategy.genetic.operations.mutation.Mutator;
import com.romanovich.nn.training.strategy.genetic.operations.mutation.GaussMutator;
import com.romanovich.nn.training.strategy.genetic.operations.selection.BetsPartSelection;
import com.romanovich.nn.training.strategy.genetic.operations.selection.RouletteWheelSelection;
import com.romanovich.nn.training.strategy.genetic.operations.selection.SelectionStrategy;
import com.romanovich.nn.training.strategy.genetic.operations.selection.TournamentSelection;

/**
 * @author romanovich
 * @since 24.05.2017
 */
public final class OperatorFactory {

    private OperatorFactory() {

    }

    public static SelectionStrategy getSelector(SelectionType selectionType) {
        switch (selectionType) {
            case ROULETTE_WHEEL:
                return new RouletteWheelSelection();
            case TOURNAMENT:
                return new TournamentSelection();
            case BEST_PART:
            default:
                return new BetsPartSelection();
        }
    }

    public static Mutator getMutator(MutationType mutationType, double mutationProbability, int mutationCount) {
        switch (mutationType) {
            case EXCHANGE_MUTATION:
                return new ExchangeMutator(mutationProbability, mutationCount);
            case GAUSS_MUTATION:
            default:
                return new GaussMutator(mutationProbability, mutationCount);
        }
    }
}
