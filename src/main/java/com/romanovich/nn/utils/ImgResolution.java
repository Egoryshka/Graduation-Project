package com.romanovich.nn.utils;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public enum ImgResolution {

    W4_H6(4, 6),
    W6_H8(6, 8),
    W9_H12(9, 12),
    W10_H16(10, 16),
    W12_H16(12, 16);

    private int width;
    private int height;

    ImgResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getImageSize() {
        return width * height;
    }
}
