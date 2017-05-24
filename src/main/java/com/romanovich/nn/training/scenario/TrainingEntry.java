package com.romanovich.nn.training.scenario;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public class TrainingEntry {

    private double[] input;
    private double[] expected;

    public TrainingEntry(double[] input, double[] expected) {
        this.input = input;
        this.expected = expected;
    }

    public double[] getInput() {
        return input;
    }

    public double[] getExpected() {
        return expected;
    }
}
