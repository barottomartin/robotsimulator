package com.barottomartin;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

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
        if (nearestObstacleDistance() < getCriticalDistance()) {
            frontAngle = (frontAngle + 90 + Math.random() * 180) % 360;
        } else {
            setX(getX() + Math.sin(Math.toRadians(frontAngle)));
            setY(getY() + Math.cos(Math.toRadians(frontAngle)));
        }
    }

    public Shape getFrontCollisionLimit(){
        return new Arc(getX(), getY(), getCriticalDistance(), getCriticalDistance(),
                (frontAngle + 225) % 360 , 90);
    }

    public double nearestObstacleDistance() {
        Shape collisionLimit = getFrontCollisionLimit();
        Point2D pointOfReference = new Point2D(getX(), getY());
        double minimumDistance = Double.MAX_VALUE;
        for (Rectangle r: room.getCells()) {
            if (collisionLimit.intersects(r.getLayoutBounds())){
                double distance = pointOfReference.distance(r.getX() + 0.5 * room.getCellPixelSize(),
                        r.getY() + 0.5 * room.getCellPixelSize());
                if (distance < minimumDistance){
                    minimumDistance = distance;
                }
            }
        }
        return minimumDistance;
    }
}
