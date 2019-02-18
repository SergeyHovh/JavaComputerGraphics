package com.company.Project_1;

import com.company.base.Cell;
import com.company.base.GridPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import static java.awt.event.KeyEvent.*;

public class LineDrawAndPolygonTransformations extends GridPanel {

    private boolean centerAllowed;
    private Vector<Cell> points = new Vector<>();
    private Vector<com.company.Project_1.Polygon> polygons = new Vector<>();
    private boolean centered = false;
    private int selectedID = 0;

    protected LineDrawAndPolygonTransformations(int resolution, double w, double h, boolean centerAllowed) {
        super(resolution, w, h);
        noGridLines();
        this.centerAllowed = centerAllowed;
        if (!this.centerAllowed) {
            setCenter(0, 0);
            clearGrid();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for (com.company.Project_1.Polygon polygon : polygons) {
            for (Cell polygonVertex : polygon.getPolygonVertices()) {
                graphics2D.setColor(polygonVertex.getColor());
                graphics2D.fill(polygonVertex);
                if (gridLines) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.draw(polygonVertex);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (centerAllowed && !centered) {
            setCenter(getClickedI(), getClickedJ());
            centered = !centered;
        } else {
            Cell cell = getGrid()[getClickedI()][getClickedJ()];
            cell.setColor(Color.RED);
            cell.setxPos(getXPosition());
            cell.setyPos(getYPosition());
            points.add(cell);
            drawPolygon(points);
            for (com.company.Project_1.Polygon polygon : polygons) {
                drawPolygon(polygon.getPolygonVertices());
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        int x = getCenterX(), y = getCenterY();
        switch (e.getKeyCode()) {
            case VK_SPACE:
                if (points.size() > 2) {
                    drawLine(points.lastElement(), points.firstElement());
                    createPolygon(true);
                }
                break;
            case VK_SHIFT:
                createPolygon(false);
                break;
            case VK_C:
                clearGrid();
                break;
            case VK_R:
                // rotate clockwise
                rotatePolygon(true);
                break;
            case VK_T:
                // rotate counter clockwise
                rotatePolygon(false);
                break;
            case VK_I:
                // scalePolygon up Y
                scalePolygon(0, 1);
                break;
            case VK_K:
                // scalePolygon down Y
                scalePolygon(0, -1);
                break;
            case VK_L:
                // scalePolygon up X
                scalePolygon(1, 0);
                break;
            case VK_J:
                // scalePolygon down X
                scalePolygon(-1, 0);
                break;
            case VK_LEFT:
                movePolygon(-1, 0);
                break;
            case VK_RIGHT:
                movePolygon(1, 0);
                break;
            case VK_UP:
                movePolygon(0, 1);
                break;
            case VK_DOWN:
                movePolygon(0, -1);
                break;
            case VK_N:
                selectPolygon(true);
                break;
            case VK_P:
                selectPolygon(false);
                break;
        }
        // update grid
        super.clearGrid();
        if (centerAllowed) setCenter(x, y);
        for (com.company.Project_1.Polygon polygon : polygons) {
            drawPolygon(polygon);
        }
    }

    private void rotatePolygon(boolean clockwise) {
        for (com.company.Project_1.Polygon polygon : polygons) {
            if (polygon.isSelected()) {
                polygon.rotate(clockwise);
            }
        }
    }

    private void scalePolygon(int x, int y) {
        for (com.company.Project_1.Polygon polygon : polygons) {
            if (polygon.isSelected()) {
                polygon.scale(x, y, 0.25);
            }
        }
    }

    private void movePolygon(int x, int y) {
        for (com.company.Project_1.Polygon polygon : polygons) {
            if (polygon.isSelected()) {
                polygon.move(x, y);
            }
        }
    }

    private void drawPolygon(com.company.Project_1.Polygon polygon) {
        Vector<Cell> vertices = polygon.getPolygonVertices();
        drawPolygon(vertices);
        if (polygon.isComplete()) {
            drawLine(vertices.firstElement(), vertices.lastElement());
        }
        plot(polygon.getMassX(), polygon.getMassY());
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

    private void selectPolygon(boolean next) {
        if (polygons.size() > 1) {
            if (next)
                selectedID++;
            else
                selectedID--;
            selectedID += polygons.size();
            selectedID %= polygons.size();

            for (com.company.Project_1.Polygon polygon : polygons) {
                polygon.setSelected(false);
                for (Cell polygonVertex : polygon.getPolygonVertices()) {
                    polygonVertex.setColor(Color.RED);
                }
            }
            polygons.get(selectedID).select();
        }
    }

    private void createPolygon(boolean complete) {
        com.company.Project_1.Polygon e1 = new com.company.Project_1.Polygon(new Vector<>(points));
        for (com.company.Project_1.Polygon polygon : polygons) {
            polygon.setSelected(false);
        }
        e1.select();
        e1.setComplete(complete);
        selectedID++;
        polygons.add(e1);
        points.clear();
    }

    @Override
    protected void clearGrid() {
        super.clearGrid();
        polygons.clear();
        points.clear();
        if (centerAllowed)
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
        int length = getGrid().length;
        int xAxis = (getCenterX() + x + length) % length;
        int yAxis = (getCenterY() - y + length) % length;
        getGrid()[xAxis][yAxis].setColor(Color.RED);
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
}
