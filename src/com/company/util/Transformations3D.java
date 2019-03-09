package com.company.util;

import static java.lang.Math.*;

public class Transformations3D {

    public static double[][] translate(double x, double y, double z) {
        return new double[][]{
                {1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, z},
                {0, 0, 0, 1}
        };
    }

    public static double[][] scale(double x, double y, double z) {
        return new double[][]{
                {x, 0, 0, 0},
                {0, y, 0, 0},
                {0, 0, z, 0},
                {0, 0, 0, 1}
        };
    }

    public static double[][] rotateX(double angle) {
        double rad = toRadians(angle);
        double sin = sin(rad);
        double cos = cos(rad);
        return new double[][]{
                {1, 0, 0, 0},
                {0, cos, -sin, 0},
                {0, sin, cos, 0},
                {0, 0, 0, 1}
        };
    }

    public static double[][] rotateY(double angle) {
        double rad = toRadians(angle);
        double sin = sin(rad);
        double cos = cos(rad);
        return new double[][]{
                {cos, 0, sin, 0},
                {0, 1, 0, 0},
                {-sin, 0, cos, 0},
                {0, 0, 0, 1}
        };
    }

    public static double[][] rotateZ(double angle) {
        double rad = toRadians(angle);
        double sin = sin(rad);
        double cos = cos(rad);
        return new double[][]{
                {cos, sin, 0, 0},
                {-sin, cos, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
    }
}
