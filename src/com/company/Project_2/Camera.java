package com.company.Project_2;

import static java.lang.Math.tan;
import static java.lang.Math.toRadians;

public class Camera {

    public static double[][] projectionMatrix(double theta, double ratio, double near, double far) {
        double rad = toRadians(theta);
        double oneOverTan = 1.0 / tan(rad / 2);
        return new double[][]{
                {oneOverTan / ratio, 0, 0, 0},
                {0, oneOverTan, 0, 0},
                {0, 0, -(near + far) / (far - near), 2 * far * near / (near - far)},
                {0, 0, -1, 0}
        };
    }
}
