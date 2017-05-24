package com.romanovich.nn.utils.converter;

import com.romanovich.nn.utils.ImgResolution;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public class ImageNumberConverter<INPUT, OUTPUT> implements DataConverter<BufferedImage, Integer> {

    private ImgResolution resolution;

    public ImageNumberConverter(ImgResolution resolution) {
        this.resolution = resolution;
    }

    @Override
    public double[] convertData(BufferedImage inputData) {
        BufferedImage img = Scalr.apply(inputData, Scalr.OP_GRAYSCALE);
        img = Scalr.resize(img, Scalr.Method.BALANCED, Scalr.Mode.FIT_EXACT,
                resolution.getWidth(), resolution.getHeight());
        return resolveImage(img.getData());
    }

    @Override
    public Integer convertBack(double[] output) {
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

    @Override
    public double[] convertExpectedResult(Integer expected) {
        if (expected > 10 || expected < 0) {
            throw new IllegalArgumentException();
        }
        double[] result = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        result[expected] = 1;
        return result;
    }

    private double[] resolveImage(Raster img) {
        double[] vector = new double[img.getHeight() * img.getWidth()];
        int index = 0;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                vector[index++] = resolvePixelValue(img.getSample(j, i, 0));
            }
        }
        return vector;
    }

    private double resolvePixelValue(int pixelValue) {
        return (255 - pixelValue) / 255.0;
    }
}
