package com.romanovich.nn.utils.converter;

import org.apache.commons.lang3.StringUtils;

/**
 * @author romanovich
 * @since 21.05.2017
 */
public class NumberConverter {

    public static double[] convert(String s) {
        double[] result = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        if (!StringUtils.isNumeric(s)) {
            return result;
        }
        int number = Integer.valueOf(s);
        result[number] = 1;
        return result;
    }

    public static int convertBack(double[] output) {
        if (output.length != 10) {
            throw new IllegalArgumentException();
        }
        int result = 0;
        double max = 0;
        for (int i = 0; i < output.length; i++) {
            if (output[i] > max) {
                result = i;
                max = output[i];
            }
        }
        return result;
    }
}
