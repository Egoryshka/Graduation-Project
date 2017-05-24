package com.romanovich.nn.training.error;

/**
 * @author romanovich
 * @since 22.05.2017
 */
public final class ErrorFunctionFactory {

    private ErrorFunctionFactory() {

    }

    public static ErrorFunction getErrorFunctionFunction(ErrorFunctionType errorFunctionType) {
        switch (errorFunctionType) {
            case LOGARITHMIC_PLAUSIBILITY_ERROR:
                return new PlausibilityErrorFunction();
            case EUCLID_ERROR:
            default:
                return new EuclidErrorFunction();
        }
    }
}
