package com.barottomartin;


import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Robot {

    private Circle shape;
    private double frontAngle;
    private Room room;

    public Robot(Room room) {
        shape = new Circle(5);
        shape.setCenterX(255);
        shape.setCenterY(255);
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

    public void move(long now){
        if (nearestObstacle() < room.getCellPixelSize()) {
            frontAngle = Math.random() * 360;
        }
        setX(getX() + Math.sin(Math.toRadians(frontAngle)));
        setY(getY() + Math.cos(Math.toRadians(frontAngle)));

    }

    public Line rayCast(){
        // Line long enough for any direction
        Line line = new Line(getX(), getY(), getX(), getY() + room.getHeight() * room.getWidth());
        Rotate rotation = new Rotate();
        rotation.pivotXProperty().bind(line.startXProperty());
        rotation.pivotYProperty().bind(line.startYProperty());
        rotation.setAngle(frontAngle);
        line.getTransforms().add(rotation);
        return line;
    }

    public double nearestObstacle() {
        Line line = rayCast();
        Point2D lineStart = new Point2D(line.getStartX(), line.getStartY());
        double minimumDistance = Double.MAX_VALUE;
        for (Rectangle r: room.getCells()) {
            if (line.intersects(r.getLayoutBounds())){
                //For regular cells this is kind of OK but not for
                double dist = lineStart.distance(r.getX(), r.getY());
                if (dist < minimumDistance){
                    minimumDistance = dist;
                }
            }
        }
        return minimumDistance;
    }
}
