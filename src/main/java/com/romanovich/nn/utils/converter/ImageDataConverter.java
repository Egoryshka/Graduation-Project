package com.romanovich.nn.utils.converter;

import com.romanovich.nn.utils.ImgResolution;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author romanovich
 * @since 21.05.2017
 */
public class ImageDataConverter {

    private ImgResolution resolution;

    public ImageDataConverter(ImgResolution resolution) {
        this.resolution = resolution;
    }

    public double[] convert(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            return convert(image);
        } catch (IOException ex) {
            System.out.println("IOException " + ex);
            return new double[0];
        }
    }

    public double[] convert(InputStream imageStream) {
        try {
            BufferedImage image = ImageIO.read(imageStream);
            return convert(image);
        } catch (IOException ex) {
            System.out.println("IOException " + ex);
            return new double[0];
        }
    }

    private double[] convert(BufferedImage image) {
        BufferedImage img = Scalr.apply(image, Scalr.OP_GRAYSCALE);
        img = Scalr.resize(img, Scalr.Method.BALANCED, Scalr.Mode.FIT_EXACT,
                resolution.getWidth(), resolution.getHeight());
        return resolveImage(img.getData());
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
