package com.company.util;

import com.company.Project_1.Polygon;
import com.company.base.Cell;
import com.company.base.GridPanel;

import java.awt.*;
import java.util.Vector;

public class Drawing2D {

    private GridPanel gridPanel;

    public Drawing2D(GridPanel panel) {
        gridPanel = panel;
    }

    /**
     * draws a straight line from p1 to p2 using the Bresenham's algorithm
     *
     * @param p1 starting point
     * @param p2 ending point
     */
    public void drawLine(Cell p1, Cell p2) {
        int x1, y1, x2, y2, dx, dy;
        int octant = octant(p1, p2);
        int[] points = getPoints(p1, p2, octant);
        x1 = points[0];
        y1 = points[1];
        x2 = points[2];
        y2 = points[3];
        dy = (y2 - y1);
        dx = (x2 - x1);
        int d = 2 * dy - dx;
        int dE = 2 * dy;
        int dNE = 2 * (dy - dx);
        int absDx = Math.abs(dx);
        int absDy = Math.abs(dy);
        if (absDy == 0) { // horizontal line
            for (int i = 0; i < Math.abs(dx); i++) {
                int j = i;
                if (dx < 0) j *= -1;
                plot(x1 + j, y1);
            }
        } else if (absDx == 0) { // vertical line
            for (int i = 0; i < Math.abs(dy); i++) {
                int j = i;
                if (dy > 0) j *= -1;
                plot(x1, y1 - j);
            }
        } else {
            // midpoint/Bresenham algorithm
            while (x1 < x2) {
                if (d <= 0) {
                    d += dE;
                } else {
                    d += dNE;
                    y1++;
                }
                x1++;
                plot(x1, y1, octant);
            }
        }
    }

    /**
     * connects points from given ordered set creating a polygon
     *
     * @param points vertex set
     */
    public void drawPolygon(Vector<Cell> points) {
        if (points.size() >= 2) {
            for (int i = 0; i < points.size() - 1; i++) {
                Cell p1 = points.get(i);
                Cell p2 = points.get(i + 1);
                drawLine(p1, p2);
            }
        }
    }

    public void drawPolygon(Polygon polygon) {
        Vector<Cell> vertices = polygon.getPolygonVertices();
        drawPolygon(vertices);
        if (polygon.isComplete()) {
            drawLine(vertices.firstElement(), vertices.lastElement());
        }
        plot(polygon.getMassX(), polygon.getMassY());
    }

    private int octant(Cell p1, Cell p2) {
        int oct = -1;
        int x1, x2, y1, y2, dy, dx, absDx, absDy;
        x1 = p1.getxPos();
        x2 = p2.getxPos();
        y1 = p1.getyPos();
        y2 = p2.getyPos();
        dy = y2 - y1;
        dx = x2 - x1;
        absDx = Math.abs(dx);
        absDy = Math.abs(dy);
        if (dy > 0) {
            if (dx > 0) {
                oct = absDx > absDy ? 1 : 2;
            } else if (dx < 0) {
                oct = absDx < absDy ? 3 : 4;
            }
        } else if (dy < 0) {
            if (dx < 0) {
                oct = absDx > absDy ? 5 : 6;
            } else if (dx > 0) {
                oct = absDx < absDy ? 7 : 8;
            }
        }
        return oct;
    }

    private void plot(int x1, int y1, int octant) {
        switch (octant) {
            case 1:
                plot(x1, y1);
                break;
            case 2:
                plot(y1, x1);
                break;
            case 3:
                plot(-y1, x1);
                break;
            case 4:
                plot(-x1, y1);
                break;
            case 5:
                plot(-x1, -y1);
                break;
            case 6:
                plot(-y1, -x1);
                break;
            case 7:
                plot(y1, -x1);
                break;
            case 8:
                plot(x1, -y1);
                break;
        }
    }

    private void plot(int x, int y) {
        int length = gridPanel.getGrid().length;
        int xAxis = (gridPanel.getCenterX() + x + length) % length;
        int yAxis = (gridPanel.getCenterY() - y + length) % length;
        gridPanel.getGrid()[xAxis][yAxis].setColor(Color.RED);
    }

    /**
     * @param p1     first point
     * @param p2     second point
     * @param octant in which octant
     * @return array of size 4 - 0 -> x1, 1 -> y1, 2 -> x2, 3 -> y2
     */
    private int[] getPoints(Cell p1, Cell p2, int octant) {
        int[] res = new int[4];
        int x1, x2, y1, y2;
        switch (octant) {
            case 1: // x, y -> x, y
                x1 = p1.getxPos();
                y1 = p1.getyPos();
                x2 = p2.getxPos();
                y2 = p2.getyPos();
                break;
            case 2: // x, y -> y, x
                x1 = p1.getyPos();
                y1 = p1.getxPos();
                x2 = p2.getyPos();
                y2 = p2.getxPos();
                break;
            case 3: // x, y -> -y, x
                x1 = p1.getyPos();
                y1 = -p1.getxPos();
                x2 = p2.getyPos();
                y2 = -p2.getxPos();
                break;
            case 4: // x, y -> -x, y
                x1 = -p1.getxPos();
                y1 = p1.getyPos();
                x2 = -p2.getxPos();
                y2 = p2.getyPos();
                break;
            case 5: // x, y -> -x, -y
                x1 = -p1.getxPos();
                y1 = -p1.getyPos();
                x2 = -p2.getxPos();
                y2 = -p2.getyPos();
                break;
            case 6: // x, y -> -y, -x
                x1 = -p1.getyPos();
                y1 = -p1.getxPos();
                x2 = -p2.getyPos();
                y2 = -p2.getxPos();
                break;
            case 7: // x, y -> y, -x
                x1 = -p1.getyPos();
                y1 = p1.getxPos();
                x2 = -p2.getyPos();
                y2 = p2.getxPos();
                break;
            case 8: // x, y -> x, -y
                x1 = p1.getxPos();
                y1 = -p1.getyPos();
                x2 = p2.getxPos();
                y2 = -p2.getyPos();
                break;
            default:
                x1 = p1.getxPos();
                y1 = p1.getyPos();
                x2 = p2.getxPos();
                y2 = p2.getyPos();
        }
        res[0] = x1;
        res[1] = y1;
        res[2] = x2;
        res[3] = y2;
        return res;
    }
}
