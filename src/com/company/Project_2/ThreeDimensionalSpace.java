package com.company.Project_2;

import com.company.base.Cell;
import com.company.base.GridPanel;
import com.company.util.Drawing2D;
import com.company.util.MatrixOperations;

import java.awt.event.KeyEvent;
import java.util.Vector;

import static com.company.util.MatrixOperations.matrixMultiplication;
import static com.company.util.Transformations3D.*;

public class ThreeDimensionalSpace extends GridPanel {
    private Drawing2D drawing2D;
    private Cube cube;
    private double ratio, theta = 90;
    private double angleX = 0, angleY = 0, translateX = 0, translateY = 0, translateZ = -5;

    public ThreeDimensionalSpace(int N, double w, double h) {
        super(N, w, h);
        ratio = w / h;
        cube = new Cube(0, 0, 0, 2);
        drawing2D = new Drawing2D(this);
        setCenter(N / 2, N / 2);
        noGridLines();
        clearGrid();
        plotCube(cube);
    }

    private void plotCube(Cube cube) {
        for (Face face : cube.getFaces()) {
            plotFace(face);
        }
    }

    private void plotFace(Face face) {
        double[][] worldMatrix = translate(translateX, translateY, translateZ);
        double[][] projectionMatrix = Camera.projectionMatrix(theta, ratio, 0, 100);
        Vector<Vertex3D> vertex3DS = face.getFace();
        Vector<Vertex3D> points = new Vector<>();
        double[][] rotate = matrixMultiplication(rotateX(angleX), rotateY(angleY));
        double[][] m1 = matrixMultiplication(worldMatrix, rotate);
        double[][] project = matrixMultiplication(projectionMatrix, m1);
        for (Vertex3D vertex3D : vertex3DS) {
            double[][] V = MatrixOperations.matrixTranspose(new double[][]{
                    {vertex3D.x, vertex3D.y, vertex3D.z, 1}
            });
            double[][] mult2 = matrixMultiplication(project, V);

            double xPrime = mult2[0][0] / mult2[3][0];
            double yPrime = mult2[1][0] / mult2[3][0];
            int x = (int) Math.round(getCenterX() * xPrime);
            int y = (int) Math.round(getCenterY() * yPrime);
            points.add(new Vertex3D(x, y, 1));
        }
        drawPoints(points);
    }

    private void drawPoints(Vector<Vertex3D> points) {
        if (points.isEmpty()) return;
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
        int angle = 5;
        double step = 0.1;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                angleY -= angle;
                break;
            case KeyEvent.VK_RIGHT:
                angleY += angle;
                break;
            case KeyEvent.VK_UP:
                angleX -= angle;
                break;
            case KeyEvent.VK_DOWN:
                angleX += angle;
                break;
            case KeyEvent.VK_W:
                translateY += step;
                break;
            case KeyEvent.VK_S:
                translateY -= step;
                break;
            case KeyEvent.VK_A:
                translateX -= step;
                break;
            case KeyEvent.VK_D:
                translateX += step;
                break;
            case KeyEvent.VK_Z:
                translateZ += step;
                break;
            case KeyEvent.VK_X:
                translateZ -= step;
                break;
            case KeyEvent.VK_Q:
                theta -= 5;
                break;
            case KeyEvent.VK_E:
                theta += 5;
                break;
            case KeyEvent.VK_SPACE:
                theta = 90;
                break;
        }
        plotCube(cube);
    }
}
