package com.romanovich.nn.training.strategy;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author romanovich
 * @since 26.05.2017
 */
public enum TrainerType {

    BACK_PROPAGATION("Back propagation algorithm"),
    GENETIC("Genetic Engine");

    private String trainer;

    TrainerType(String trainer) {
        this.trainer = trainer;
    }

    public String getTrainer() {
        return trainer;
    }

    public static List<String> getTrainers() {
        List<String> result = new ArrayList<>();
        for (TrainerType type : values()) {
            result.add(type.getTrainer());
        }
        return result;
    }

    public static TrainerType find(String function) {
        for (TrainerType type : values()) {
            if (StringUtils.equals(type.getTrainer(), function)) {
                return type;
            }
        }
        return TrainerType.BACK_PROPAGATION;
    }
}
