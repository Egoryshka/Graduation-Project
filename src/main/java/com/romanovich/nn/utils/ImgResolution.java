package com.romanovich.nn.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author romanovich
 * @since 23.05.2017
 */
public enum ImgResolution {

    W4_H6(4, 6, "Width: 4; Height: 6"),
    W6_H8(6, 8, "Width: 6; Height: 8"),
    W9_H12(9, 12, "Width: 9; Height: 12"),
    W10_H16(10, 16, "Width: 10; Height: 16"),
    W12_H16(12, 16, "Width: 12; Height: 16");

    private int width;
    private int height;
    private String description;

    ImgResolution(int width, int height, String description) {
        this.width = width;
        this.height = height;
        this.description = description;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getDescription() {
        return description;
    }

    public static List<String> getDescriptions() {
        List<String> result = new ArrayList<>();
        for (ImgResolution resolution : values()) {
            result.add(resolution.getDescription());
        }
        return result;
    }

    public static ImgResolution findByDescription(String description) {
        for (ImgResolution resolution : values()) {
            if (StringUtils.equals(resolution.getDescription(), description)) {
                return resolution;
            }
        }
        return ImgResolution.W9_H12;
    }

    public int getImageSize() {
        return width * height;
    }
}
