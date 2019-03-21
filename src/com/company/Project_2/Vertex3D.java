package com.company.Project_2;

import java.util.Objects;

public class Vertex3D {
    int x;
    int y;
    int z;

    Vertex3D(int x, int y, int z) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex3D vertex3D = (Vertex3D) o;
        return x == vertex3D.x &&
                y == vertex3D.y &&
                z == vertex3D.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
