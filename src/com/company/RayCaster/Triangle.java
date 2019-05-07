package com.company.RayCaster;

public class Triangle {
    Point[] pointsAndColor = new Point[4]; // first 3 elements are 3 points, and the 4-th is the color

    Triangle(Point p1, Point p2, Point p3, Point color) {
        pointsAndColor[0] = p1;
        pointsAndColor[1] = p2;
        pointsAndColor[2] = p3;
        pointsAndColor[3] = color;
    }
}
