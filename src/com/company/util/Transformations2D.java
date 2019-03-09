package com.company.util;

import static java.lang.Math.*;

public class Transformations2D {
    /**
     * @param angle degrees
     * @return rotate matrix for given angle in degrees
     */
    public static double[][] rotate(double angle) {
        double rad = toRadians(angle);
        double cos = cos(rad);
        double sin = sin(rad);
        return new double[][]{
                {cos, -sin, 0},
                {sin, cos, 0},
                {0, 0, 1}
        };
    }

    public static double[][] scale(double x, double y) {
        return new double[][]{
                {x, 0, 0},
                {0, y, 0},
                {0, 0, 1}
        };
    }

    public static double[][] translate(double x, double y) {
        return new double[][]{
                {1, 0, x},
                {0, 1, y},
                {0, 0, 1}
        };
    }
}
