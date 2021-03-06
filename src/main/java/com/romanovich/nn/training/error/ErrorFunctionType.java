package com.romanovich.nn.training.error;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author romanovich
 * @since 22.05.2017
 */
public enum ErrorFunctionType {

    EUCLID_ERROR("Half of the Euclidean distance square"),
    LOGARITHMIC_PLAUSIBILITY_ERROR("Logarithmic plausibility");

    private String function;

    ErrorFunctionType(String function) {
        this.function = function;
    }

    public String getFunction() {
        return function;
    }

    public static List<String> getFunctions() {
        List<String> result = new ArrayList<>();
        for (ErrorFunctionType type : values()) {
            result.add(type.getFunction());
        }
        return result;
    }

    public static ErrorFunctionType find(String function) {
        for (ErrorFunctionType type : values()) {
            if (StringUtils.equals(type.getFunction(), function)) {
                return type;
            }
        }
        return ErrorFunctionType.EUCLID_ERROR;
    }
}
