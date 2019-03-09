package com.company.Project_2;

import java.util.Arrays;
import java.util.Vector;

public class Face {
    private Vector<Vertex3D> face = new Vector<>();

    public Face(Vertex3D... face) {
        this.face.addAll(Arrays.asList(face));
    }

    public Vector<Vertex3D> getFace() {
        return face;
    }

    public void setFace(Vector<Vertex3D> face) {
        this.face = face;
    }

    @Override
    public String toString() {
        return face.toString();
    }
}
