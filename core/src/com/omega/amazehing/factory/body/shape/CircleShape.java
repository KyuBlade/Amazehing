package com.omega.amazehing.factory.body.shape;

public class CircleShape implements Shape {

    private float radius;

    public CircleShape(float radius) {
	this.radius = radius;
    }

    public float getRadius() {
	return radius;
    }
}