package com.omega.amazehing.factory.body.shape;


public class RectangleShape implements Shape {

    private float width;
    private float height;

    public RectangleShape(float width, float height) {
	this.width = width;
	this.height = height;
    }

    public float getHeight() {
	return height;
    }

    public float getWidth() {
	return width;
    }
}