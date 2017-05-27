package com.romanovich.nn.training.strategy.genetic.operations;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public enum SelectionType {
    TOURNAMENT("Tournament selection"),
    ROULETTE_WHEEL("Roulette-wheel selection"),
    BEST_PART("The best individuals selection");

    private String selection;

    SelectionType(String selection) {
        this.selection = selection;
    }

    public String getSelection() {
        return selection;
    }

    public static List<String> getSelections() {
        List<String> result = new ArrayList<>();
        for (SelectionType type : values()) {
            result.add(type.getSelection());
        }
        return result;
    }

    public static SelectionType find(String function) {
        for (SelectionType type : values()) {
            if (StringUtils.equals(type.getSelection(), function)) {
                return type;
            }
        }
        return SelectionType.ROULETTE_WHEEL;
    }
}
