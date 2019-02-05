package com.company;

import static java.lang.Math.*;

public class Transformations2D {

    public static double[][] matrixMultiplication(double[][] A, double[][] B) {
        if (A[0].length != B.length) return null;
        int m = A.length;
        int n = A[0].length;
        int k = B[0].length;
        double[][] res = new double[m][k];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < k; j++) {
                for (int p = 0; p < n; p++) {
                    double a = A[i][p];
                    double b = B[p][j];
                    res[i][j] += a * b;
                }
            }
        }
        return res;
    }

    public static double[][] matrixTranspose(double[][] A) {
        double[][] transposed = new double[A[0].length][A.length];
        int length = transposed.length;
        int height = transposed[0].length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                transposed[i][j] = A[j][i];
            }
        }
        return transposed;
    }

    /**
     * @param angle degrees
     * @return rotation matrix for given angle in degrees
     */
    public static double[][] rotationMatrix(double angle) {
        double rad = toRadians(angle);
        return new double[][]{
                {cos(rad), -sin(rad), 0},
                {sin(rad), cos(rad), 0},
                {0, 0, 1}
        };
    }

    public static double[][] scaleMatrix(double x, double y) {
        return new double[][]{
                {x, 0, 0},
                {0, y, 0},
                {0, 0, 1}
        };
    }

    public static double[][] translationMatrix(double x, double y) {
        return new double[][]{
                {1, 0, x},
                {0, 1, y},
                {0, 0, 1}
        };
    }
}
