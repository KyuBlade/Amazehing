package com.omega.amazehing.exception;

public class PathNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2450584541048545013L;

    public PathNotFoundException() {
	super("Path not found");
    }
}