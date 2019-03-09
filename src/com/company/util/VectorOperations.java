package com.company.util;

public class VectorOperations {
    public static double dotProduct(double[] A, double[] B) {
        if (A.length != B.length) return Double.MIN_VALUE;
        double val = 0;
        for (int i = 0; i < A.length; i++) {
            val += A[i] * B[i];
        }
        return val;
    }

    public static double[] crossProduct(double[] A, double[] B) {
        if (A.length != B.length) return null;
        if (A.length != 3) return null;
        double[] V = new double[3];
        V[0] = A[1] * B[2] - A[2] * B[1];
        V[1] = A[2] * B[0] - A[0] * B[2];
        V[2] = A[0] * B[1] - A[1] * B[0];
        return V;
    }
}
