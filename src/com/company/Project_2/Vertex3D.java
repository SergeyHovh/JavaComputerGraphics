package com.company.Project_2;

public class Vertex3D {
    int x;
    int y;
    int z;
    boolean transformed = false;

    Vertex3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Vertex3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
