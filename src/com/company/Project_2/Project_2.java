package com.company.Project_2;

import com.company.base.Base;

public class Project_2 extends Base {

    public Project_2(String name) {
        super(name);
        add(new ThreeDimensionalSpace(450, 810, 780));
        setResizable(false);
    }
}
