package com.company;import javax.swing.*;import java.awt.*;public class Base extends JFrame {    public Base(String name, int width, int height) {        super(name);        setSize(width, height);        setLocationRelativeTo(null);        setResizable(true);        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);        setVisible(true);    }    public Base(String name) {        this(name, 640, 480);    }    @Override    public Component add(Component comp) { // focus on added component        Component add = super.add(comp);        if (comp.isFocusable())            comp.requestFocus();        return add;    }}