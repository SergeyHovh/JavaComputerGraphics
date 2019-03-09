package com.company.Project_1;

import com.company.base.Cell;
import com.company.base.GridPanel;
import com.company.util.Drawing2D;

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
    private Drawing2D drawing2D;

    protected LineDrawAndPolygonTransformations(int resolution, double w, double h, boolean centerAllowed) {
        super(resolution, w, h);
        noGridLines();
        this.centerAllowed = centerAllowed;
        if (!this.centerAllowed) {
            setCenter(0, 0);
            clearGrid();
        }
        drawing2D = new Drawing2D(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for (Polygon polygon : polygons) {
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
            drawing2D.drawPolygon(points);
            for (com.company.Project_1.Polygon polygon : polygons) {
                drawing2D.drawPolygon(polygon.getPolygonVertices());
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
                    drawing2D.drawLine(points.lastElement(), points.firstElement());
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
            drawing2D.drawPolygon(polygon);
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
        for (Polygon polygon : polygons) {
            if (polygon.isSelected()) {
                polygon.scale(x, y, 0.25);
            }
        }
    }

    private void movePolygon(int x, int y) {
        for (Polygon polygon : polygons) {
            if (polygon.isSelected()) {
                polygon.move(x, y);
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
}
