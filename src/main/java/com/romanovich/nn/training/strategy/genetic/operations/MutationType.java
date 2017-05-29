package com.romanovich.nn.training.strategy.genetic.operations;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author romanovich
 * @since 24.05.2017
 */
public enum MutationType {
    EXCHANGE_MUTATION("Gens exchange mutation"),
    RANDOM_MUTATION("Random number mutation");

    private String mutation;

    MutationType(String mutation) {
        this.mutation = mutation;
    }

    public String getMutation() {
        return mutation;
    }

    public static List<String> getMutations() {
        List<String> result = new ArrayList<>();
        for (MutationType type : values()) {
            result.add(type.getMutation());
        }
        return result;
    }

    public static MutationType find(String function) {
        for (MutationType type : values()) {
            if (StringUtils.equals(type.getMutation(), function)) {
                return type;
            }
        }
        return MutationType.RANDOM_MUTATION;
    }
}
