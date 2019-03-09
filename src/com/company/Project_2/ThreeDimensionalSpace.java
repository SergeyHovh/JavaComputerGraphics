package com.company.Project_2;

import com.company.base.Cell;
import com.company.base.GridPanel;
import com.company.util.Drawing2D;
import com.company.util.MatrixOperations;
import com.company.util.Transformations3D;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class ThreeDimensionalSpace extends GridPanel {
    private Drawing2D drawing2D;
    private Cube cube;
    private double ratio;

    public ThreeDimensionalSpace(int N, double w, double h) {
        super(N, w, h);
        noGridLines();
        ratio = w / h;
        cube = new Cube(0, 0, 0, 60);
        setCenter(N / 2, N / 2);
        clearGrid();
        drawing2D = new Drawing2D(this);
        plotCube(cube);
        cube.getCenter();
    }

    private void plotCube(Cube cube) {
        for (Face face : cube.getFaces()) {
            plotFace(face);
        }
    }

    private void plotFace(Face face) {
        double[][] worldMatrix = Transformations3D.translate(0, 0, -5);
        double[][] projectionMatrix = Camera.projectionMatrix(90, ratio, 0, 1);
        Vector<Vertex3D> vertex3DS = face.getFace();
        Vector<Vertex3D> points = new Vector<>();
        for (Vertex3D vertex3D : vertex3DS) {
            double[][] V = MatrixOperations.matrixTranspose(new double[][]{{vertex3D.x, vertex3D.y, vertex3D.z, 1}});
            double[][] mult1 = MatrixOperations.matrixMultiplication(worldMatrix, V);
            double[][] mult2 = MatrixOperations.matrixMultiplication(projectionMatrix, mult1);
            int x = (int) Math.round(mult2[0][0]);
            int y = (int) Math.round(mult2[1][0]);
            point(x, y).setColor(Color.RED);
            points.add(new Vertex3D(x, y, 1));
        }
        drawPoints(points);
    }

    private void drawPoints(Vector<Vertex3D> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            Vertex3D v1 = points.get(i);
            Vertex3D v2 = points.get(i + 1);
            drawing2D.drawLine(point(v1.x, v1.y), point(v2.x, v2.y));
        }
        drawing2D.drawLine(
                point(points.firstElement().x, points.firstElement().y),
                point(points.lastElement().x, points.lastElement().y));
    }

    private Cell point(int x, int y) {
        int length = getGrid().length;
        Cell cell = getGrid()[(getCenterX() + x + length) % length][(getCenterY() - y + length) % length];
        cell.setxPos(x % length);
        cell.setyPos(y % length);
        return cell;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        clearGrid();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                cube.rotateY(-15);
                break;
            case KeyEvent.VK_RIGHT:
                cube.rotateY(15);
                break;
            case KeyEvent.VK_UP:
                cube.rotateX(10);
                break;
            case KeyEvent.VK_DOWN:
                cube.rotateX(-10);
                break;
        }
        plotCube(cube);
    }
}
