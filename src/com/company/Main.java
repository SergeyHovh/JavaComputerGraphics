package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import static java.awt.event.KeyEvent.*;

public class Main extends GridPanel {

    private Vector<Cell> points = new Vector<>();
    private Vector<Polygon> polygons = new Vector<>();
    private boolean centered = false;

    protected Main(int N, double w, double h) {
        super(N, w, h);
    }

    public static void main(String[] args) {
        Base f = new Base("Line", 750, 780);
        Main m = new Main(390, 780, 780);
        f.setResizable(false);
        f.add(m);
        m.noGridLines();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (!centered) {
            setCenter(getClickedI(), getClickedJ());
            centered = !centered;
        } else {
            Cell cell = getGrid()[getClickedI()][getClickedJ()];
            cell.setColor(Color.RED);
            cell.setxPos(getXPosition());
            cell.setyPos(getYPosition());
            points.add(cell);
            drawPolygon(points);
            for (Polygon polygon : polygons) {
                drawPolygon(polygon.getPolygonVertices());
            }
        }
    }

    private void drawPolygon(Vector<Cell> points) {
        if (points.size() >= 2) {
            for (int i = 0; i < points.size() - 1; i++) {
                Cell p1 = points.get(i);
                Cell p2 = points.get(i + 1);
                drawLine(p1, p2);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        switch (e.getKeyCode()) {
            case VK_SPACE:
                if (points.size() > 2) {
                    drawLine(points.lastElement(), points.firstElement());
                    polygons.add(new Polygon(points));
                    points.clear();
                }
                break;
            case VK_C:
                clearGrid();
                break;
            case VK_ENTER:
                fill();
                break;
        }
    }

    private void fill() {

    }

    @Override
    protected void clearGrid() {
        super.clearGrid();
        polygons.clear();
        points.clear();
        centered = false;
    }

    private void drawLine(Cell p1, Cell p2) {
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
        getGrid()[getCenterX() + x][getCenterY() - y].setColor(Color.RED);
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
                if (absDx > absDy) { // 1
                    oct = 1;
                } else { // 2
                    oct = 2;
                }
            } else if (dx < 0) {
                if (absDx < absDy) { // 3
                    oct = 3;
                } else { // 4
                    oct = 4;
                }
            }
        } else if (dy < 0) {
            if (dx < 0) {
                if (absDx > absDy) { // 5
                    oct = 5;
                } else { // 6
                    oct = 6;
                }
            } else if (dx > 0) {
                if (absDx < absDy) { // 7
                    oct = 7;
                } else { // 8
                    oct = 8;
                }
            }
        }
        return oct;
    }
}
