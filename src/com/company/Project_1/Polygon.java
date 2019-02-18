package com.company.Project_1;

import com.company.base.Cell;

import java.awt.*;
import java.util.Vector;

import static com.company.Project_1.Transformations2D.*;

class Polygon {
    private Vector<Cell> polygonVertices;
    private boolean selected = false, complete = true;
    private int massX = 0, massY = 0;

    Polygon(Vector<Cell> set) {
        polygonVertices = set;
        calculateMassCenter();
    }

    private void calculateMassCenter() {
        for (Cell polygonVertex : polygonVertices) {
            massX += polygonVertex.getxPos();
            massY += polygonVertex.getyPos();
        }
        massX /= polygonVertices.size();
        massY /= polygonVertices.size();
    }

    void select() {
        selected = true;
        for (Cell polygonVertex : polygonVertices) {
            polygonVertex.setColor(Color.BLUE);
        }
    }

    void move(int x, int y) {
        transformAboutPoint(translationMatrix(x, y), massX, massY);
        massX += x;
        massY += y;
    }

    void scale(int X, int Y, double scaleDiff) {
        double scaleY = 1;
        double scaleX = 1;
        scaleX += X * scaleDiff;
        scaleY += Y * scaleDiff;
        double[][] scaleMatrix = scaleMatrix(scaleX, scaleY);
        transformAboutPoint(scaleMatrix, massX, massY);
    }

    void rotate(boolean clockwise) {
        double[][] rotationMatrix;
        int angle = 15;
        if (clockwise)
            rotationMatrix = rotationMatrix(angle);
        else
            rotationMatrix = rotationMatrix(-angle);
        transformAboutPoint(rotationMatrix, massX, massY);
    }

    private void transformAboutPoint(double[][] transformation, int x, int y) {
        if (x != 0 && y != 0) {
            double[][] directTranslation = translationMatrix(x, y);
            double[][] doubles = matrixMultiplication(directTranslation, transformation);
            double[][] reverseTranslation = translationMatrix(-x, -y);
            assert doubles != null;
            double[][] resultingTransformation = matrixMultiplication(doubles, reverseTranslation);
            transform(resultingTransformation);
        } else {
            transform(transformation);
        }
    }

    private void transform(double[][] transformation) {
        for (Cell polygonVertex : polygonVertices) {
            int x = polygonVertex.getxPos();
            int y = polygonVertex.getyPos();
            double[][] currentVector = matrixTranspose(new double[][]{
                    {x, y, 1}
            });
            double[][] multiplication = matrixMultiplication(transformation, currentVector);
            assert multiplication != null;
            int xPos = (int) Math.round(multiplication[0][0]);
            int yPos = (int) Math.round(multiplication[1][0]);
            polygonVertex.setxPos(xPos);
            polygonVertex.setyPos(yPos);
        }
    }

    Vector<Cell> getPolygonVertices() {
        return polygonVertices;
    }

    boolean isSelected() {
        return selected;
    }

    void setSelected(boolean selected) {
        this.selected = selected;
    }

    boolean isComplete() {
        return complete;
    }

    void setComplete(boolean complete) {
        this.complete = complete;
    }

    int getMassX() {
        return massX;
    }

    int getMassY() {
        return massY;
    }
}
