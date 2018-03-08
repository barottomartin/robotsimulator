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
        shape.setCenterX(40);
        shape.setCenterY(40);
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
        setX(getX() + 1);
        setY(getY() + 1);
    }

    public Rectangle nearestObstacle(){
        // Line long enough for any direction
        Line line = new Line(getX(), getY(), getX(), getY() + room.getHeight() * room.getWidth());
        Point2D lineStart = new Point2D(line.getStartX(), line.getStartY());
        Rotate rotation = new Rotate();
        rotation.pivotXProperty().bind(line.startXProperty());
        rotation.pivotYProperty().bind(line.startYProperty());
        rotation.setAngle(frontAngle + 45);
        line.getTransforms().add(rotation);

        double minimumDistance = Double.MAX_VALUE;
        Rectangle obstacle = null;
        for (Rectangle r: room.getCells()) {
            if (line.intersects(r.getLayoutBounds())){
                double dist = lineStart.distance(r.getX(), r.getY());
                if (dist < minimumDistance){
                    minimumDistance = dist;
                    obstacle = r;
                }
            }
        }
        return obstacle;
    }
}
