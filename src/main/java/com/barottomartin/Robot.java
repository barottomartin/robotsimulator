package com.barottomartin;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Robot {

    private Circle shape;
    private double frontAngle;
    private Room room;

    public Robot(Room room, int radius) {
        shape = new Circle(radius);
        shape.setFill(Color.CHOCOLATE);
        shape.setCenterX(35);
        shape.setCenterY(35);
        frontAngle = 0;
        this.room = room;
    }

    public Circle getShape() {
        return shape;
    }

    public void setX(double x) {
        shape.setCenterX(x);
    }

    public void setY(double y) {
        shape.setCenterY(y);
    }

    public double getX() {
        return shape.getCenterX();
    }

    public double getY() {
        return shape.getCenterY();
    }

    public double getCriticalDistance(){
        return (room.getCellPixelSize() / 2) + shape.getRadius();
    }

    public void move(long now){
        if (nearestObstacle() < getCriticalDistance()) {
            frontAngle = (frontAngle + 90 + Math.random() * 180) % 360;
        } else {
            setX(getX() + Math.sin(Math.toRadians(frontAngle)));
            setY(getY() + Math.cos(Math.toRadians(frontAngle)));
        }
    }

    public Line rayCast(){
        Line line = new Line(getX(), getY(),
                getX() + getCriticalDistance() * Math.sin(Math.toRadians(frontAngle)),
                getY() + getCriticalDistance() * Math.cos(Math.toRadians(frontAngle)));
        return line;
    }

    public double nearestObstacle() {
        Line line = rayCast();
        Point2D lineStart = new Point2D(line.getStartX(), line.getStartY());
        double minimumDistance = Double.MAX_VALUE;
        for (Rectangle r: room.getCells()) {
            if (line.intersects(r.getLayoutBounds())){
                double distance = lineStart.distance(r.getX() + 0.5 * room.getCellPixelSize(),
                        r.getY() + 0.5 * room.getCellPixelSize());
                if (distance < minimumDistance){
                    minimumDistance = distance;
                }
            }
        }
        return minimumDistance;
    }
}
