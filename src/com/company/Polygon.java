package com.company;

import java.util.Vector;

public class Polygon {
    private Vector<Cell> polygonVertices;

    Polygon(Vector<Cell> set) {
        this.polygonVertices = set;
    }

    public Vector<Cell> getPolygonVertices() {
        return polygonVertices;
    }
}
