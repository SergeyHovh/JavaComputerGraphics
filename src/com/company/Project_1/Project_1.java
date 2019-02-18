package com.company.Project_1;

import com.company.base.Base;

public class Project_1 extends Base {
    public Project_1(String name) {
        super(name, 780, 780);
        add(new LineDrawAndPolygonTransformations(390, 810, 750, false));
        setResizable(false);
    }
}
