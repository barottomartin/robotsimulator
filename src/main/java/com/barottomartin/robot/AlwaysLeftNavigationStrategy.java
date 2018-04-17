package com.barottomartin.robot;

public class AlwaysLeftNavigationStrategy implements NavigationStrategy {

    public final static String NAME = "Always Left";

    @Override
    public double[] move(long now, double x, double y, double frontAngle, double nearestObstacleDistance, double criticalDistance) {
        if (nearestObstacleDistance < criticalDistance) {
            frontAngle = (frontAngle + 90 + 35) % 360;
        } else {
            x = x + Math.sin(Math.toRadians(frontAngle));
            y = y + Math.cos(Math.toRadians(frontAngle));
        }
        return new double[]{x, y, frontAngle};
    }

    public String getNavigationName(){
        return NAME;
    }

}
