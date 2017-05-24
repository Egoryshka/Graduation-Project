package com.romanovich.nn.utils.converter;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public interface DataConverter<INPUT, OUTPUT> {

    double[] convertData(INPUT inputData);

    OUTPUT convertBack(double[] output);

    double[] convertExpectedResult(OUTPUT expected);
}
