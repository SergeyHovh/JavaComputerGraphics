package com.company.Project_2;

import java.util.Arrays;
import java.util.Vector;

import static com.company.util.VectorOperations.crossProduct;
import static com.company.util.VectorOperations.normalize;

public class Face {
    private Vector<Vertex3D> face = new Vector<>();
    private double[] N;
    private double[][] normal;

    public Face(Vertex3D... face) {
        this.face.addAll(Arrays.asList(face));
        Vertex3D first = this.face.get(0);
        Vertex3D second = this.face.get(1);
        Vertex3D third = this.face.get(2);
        double[] a = {first.x - second.x, first.y - second.y, first.z - second.z};
        double[] b = {second.x - third.x, second.y - third.y, second.z - third.z};
        N = crossProduct(a, b);
        // TODO: 3/19/2019 3d -> 4d
        normalize(N);
        double[] norm = new double[4];
        norm[0] = N[0];
        norm[1] = N[1];
        norm[2] = N[2];
        norm[3] = 1;
        normal = new double[][]{norm};
    }

    public Vector<Vertex3D> getFace() {
        return face;
    }

    public void setFace(Vector<Vertex3D> face) {
        this.face = face;
    }

    public double[][] getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return face.toString();
    }
}
