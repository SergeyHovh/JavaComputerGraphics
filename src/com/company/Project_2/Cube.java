package com.company.Project_2;

import com.company.util.MatrixOperations;
import com.company.util.Transformations3D;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

public class Cube {
    private Set<Vertex3D> vertex3DSet = new LinkedHashSet<>();
    private Vector<Face> faces = new Vector<>();
    private double centerX, centerY, centerZ;

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
        faces.add(new Face(v5, v6, v7, v8)); // bottom face
        faces.add(new Face(v1, v2, v6, v5)); // right face
        faces.add(new Face(v3, v4, v8, v7)); // left face
        faces.add(new Face(v1, v4, v8, v5)); // near face
        faces.add(new Face(v2, v3, v7, v6)); // far face
        vertex3DSet.add(v1);
        vertex3DSet.add(v2);
        vertex3DSet.add(v3);
        vertex3DSet.add(v4);
        vertex3DSet.add(v5);
        vertex3DSet.add(v6);
        vertex3DSet.add(v7);
        vertex3DSet.add(v8);
    }

    void translate(double x, double y, double z) {
        transformAboutCenter(Transformations3D.translate(x, y, z));
        centerX += x;
        centerY += y;
        centerY += z;
    }

    void scale(double x, double y, double z) {
        transformAboutCenter(Transformations3D.scale(x, y, z));
    }

    void rotateX(double degrees) {
        transformAboutCenter(Transformations3D.rotateX(degrees));
    }

    void rotateY(double degrees) {
        transformAboutCenter(Transformations3D.rotateY(degrees));
    }

    void rotateZ(double degrees) {
        transformAboutCenter(Transformations3D.rotateZ(degrees));
    }

    void transformAboutCenter(double[][] transformation) {
        getCenter();
        transformAboutPoint(transformation, centerX, centerY, centerZ);
    }

    private void transformAboutPoint(double[][] transformation, double x, double y, double z) {
        if (x != 0 && y != 0 && z != 0) {
            double[][] direct = Transformations3D.translate(x, y, z);
            double[][] reverse = Transformations3D.translate(-x, -y, -z);
            double[][] doubles = MatrixOperations.matrixMultiplication(direct, transformation);
            double[][] result = MatrixOperations.matrixMultiplication(doubles, reverse);
            transform(result);
        } else {
            transform(transformation);
        }
    }

    private void transform(double[][] transformation) {
        for (Face face : faces) {
            for (Vertex3D vertex3D : face.getFace()) {
                if (!vertex3D.transformed) {
                    vertex3D.transformed = true;
                    double[][] vector = MatrixOperations.matrixTranspose(new double[][]{
                            {vertex3D.x, vertex3D.y, vertex3D.z, 1}
                    });
                    double[][] mult = MatrixOperations.matrixMultiplication(transformation, vector);
                    int x = (int) Math.round(mult[0][0]);
                    int y = (int) Math.round(mult[1][0]);
                    int z = (int) Math.round(mult[2][0]);
                    vertex3D.x = x;
                    vertex3D.y = y;
                    vertex3D.z = z;
                }
            }
        }

        for (Face face : faces) {
            for (Vertex3D vertex3D : face.getFace()) {
                vertex3D.transformed = false;
            }
        }
    }


    void getCenter() {
        int size = vertex3DSet.size();
        centerX = centerY = centerZ = 0;
        for (Vertex3D vertex3D : vertex3DSet) {
            centerX += vertex3D.x;
            centerY += vertex3D.y;
            centerZ += vertex3D.z;
        }
        centerX /= size;
        centerY /= size;
        centerZ /= size;
    }

    @Override
    public String toString() {
        return "Cube{" +
                "vertex3DSet=" + vertex3DSet +
                '}';
    }

    public Vector<Face> getFaces() {
        return faces;
    }


}
