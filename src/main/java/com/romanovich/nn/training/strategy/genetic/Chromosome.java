package com.romanovich.nn.training.strategy.genetic;

import java.util.Comparator;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public class Chromosome {

    private double[] genes;
    private double error;

    public Chromosome(double[] genes) {
        this.genes = genes;
        this.error = Double.POSITIVE_INFINITY;
    }

    public double[] getGenes() {
        return genes;
    }

    public void setGenes(double[] genes) {
        this.genes = genes;
        this.error = Double.POSITIVE_INFINITY;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public static class ChromosomeComparator implements Comparator<Chromosome> {

        @Override
        public int compare(Chromosome o1, Chromosome o2) {
            if (o1.getError() >= o2.getError()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
