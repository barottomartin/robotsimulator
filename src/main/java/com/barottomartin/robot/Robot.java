package com.barottomartin.robot;

import com.barottomartin.Properties;
import com.barottomartin.Room;
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
    private NavigationStrategy navigationStrategy;

    public Robot(Room room) {
        switch (Properties.getInstance().getProperty("navigationStrategy")) {
            case "Always Left":
                navigationStrategy = new AlwaysLeftNavigationStrategy();
                break;
            case "Right-Hand Wall Follower":
                navigationStrategy = new RightHandWallFollowerStrategy();
                break;
            case "Pledge Algorithm":
                navigationStrategy = new PledgeAlgorithmStrategy();
                break;
            default:
                navigationStrategy = new RandomNavigationStrategy();
        }
        shape = new Circle(Integer.valueOf(Properties.getInstance().getProperty("robotRadius")));
        shape.setFill(Color.ORANGERED);
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
        return 0.5 * Math.sqrt(2 * Math.pow(room.getCellPixelSize(), 2)) + shape.getRadius();
    }

    public void move(long now){
        double[] coordinates = navigationStrategy.move(now, getX(), getY(), frontAngle, nearestObstacleDistance(),
                getCriticalDistance());
        setX(coordinates[0]);
        setY(coordinates[1]);
        frontAngle = coordinates[2];
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
