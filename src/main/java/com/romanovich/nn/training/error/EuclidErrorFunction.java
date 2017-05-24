package com.romanovich.nn.training.error;

/**
 * Half squared Euclid distance function.
 *
 * @author romanovich
 * @since 17.05.2017
 */
public class EuclidErrorFunction implements ErrorFunction {

    @Override
    public double calcError(double[] expected, double[] actual) {
        double result = 0;
        for (int i = 0; i < expected.length; i++) {
            result += Math.pow(expected[i] - actual[i], 2);
        }
        return 0.5 * result;
    }

    @Override
    public double calcErrorDerivative(double expected, double actual) {
        return actual - expected;
    }
}
