package com.romanovich.nn.layer.neuron.activation;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author romanovich
 * @since 26.05.2017
 */
public enum ActivationType {
    SIGMOID("Sigmoid function"),
    HYPERBOLIC("Hyperbolic tangens");

    private String function;

    ActivationType(String function) {
        this.function = function;
    }

    public String getFunction() {
        return function;
    }

    public static List<String> getFunctions() {
        List<String> result = new ArrayList<>();
        for (ActivationType type : values()) {
            result.add(type.getFunction());
        }
        return result;
    }

    public static ActivationType find(String function) {
        for (ActivationType type : values()) {
            if (StringUtils.equals(type.getFunction(), function)) {
                return type;
            }
        }
        return ActivationType.SIGMOID;
    }
}
