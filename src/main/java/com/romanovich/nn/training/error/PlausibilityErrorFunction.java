package com.romanovich.nn.training.error;

/**
 * Logarithmic plausibility function.
 *
 * @author romanovich
 * @since 17.05.2017
 */
public class PlausibilityErrorFunction implements ErrorFunction {
    @Override
    public double calcError(double[] expected, double[] actual) {
        double result = 0;
        for (int i = 0; i < expected.length; i++) {
            result += expected[i] * Math.log10(actual[i]) + (1 - expected[i]) * Math.log10(1 - actual[i]);
        }
        return -result;
    }

    @Override
    public double calcErrorDerivative(double expected, double actual) {
        return -(expected/actual - (1 - expected)/(1 - actual));
    }
}
