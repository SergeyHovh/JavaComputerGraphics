package com.company.Project_2;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

public class Cube {
    private Set<Vertex3D> vertex3DSet = new LinkedHashSet<>();
    private Vector<Face> faces = new Vector<>();

    public Cube(int x, int y, int z, int length) {
        int a = length / 2;
        Vertex3D v1 = new Vertex3D(x + a, y + a, z + a); // r n u
        Vertex3D v2 = new Vertex3D(x + a, y + a, z - a); // r f u
        Vertex3D v3 = new Vertex3D(x - a, y + a, z - a); // l f u
        Vertex3D v4 = new Vertex3D(x - a, y + a, z + a); // l n u
        Vertex3D v5 = new Vertex3D(x + a, y - a, z + a); // r n b
        Vertex3D v6 = new Vertex3D(x + a, y - a, z - a); // r f b
        Vertex3D v7 = new Vertex3D(x - a, y - a, z - a); // l f b
        Vertex3D v8 = new Vertex3D(x - a, y - a, z + a); // l n b
        faces.add(new Face(v1, v2, v3, v4)); // upper face
        faces.add(new Face(v5, v8, v7, v6)); // bottom face
        faces.add(new Face(v5, v6, v2, v1)); // right face
        faces.add(new Face(v7, v8, v4, v3)); // left face
        faces.add(new Face(v1, v4, v8, v5)); // near face
        faces.add(new Face(v3, v2, v6, v7)); // far face
        vertex3DSet.add(v1);
        vertex3DSet.add(v2);
        vertex3DSet.add(v3);
        vertex3DSet.add(v4);
        vertex3DSet.add(v5);
        vertex3DSet.add(v6);
        vertex3DSet.add(v7);
        vertex3DSet.add(v8);
    }

    public Vector<Face> getFaces() {
        return faces;
    }
}
