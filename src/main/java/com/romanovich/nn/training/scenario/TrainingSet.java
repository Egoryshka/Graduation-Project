package com.romanovich.nn.training.scenario;

import java.util.List;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public class TrainingSet {

    private List<TrainingEntry> examples;

    public TrainingSet(List<TrainingEntry> examples) {
        this.examples = examples;
    }

    public List<TrainingEntry> getExamples() {
        return examples;
    }
}
