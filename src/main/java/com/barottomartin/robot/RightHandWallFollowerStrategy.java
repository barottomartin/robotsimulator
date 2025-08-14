package com.barottomartin.robot;

public class RightHandWallFollowerStrategy implements NavigationStrategy {

    public final static String NAME = "Right-Hand Wall Follower";

    @Override
    public double[] move(long now, double x, double y, double frontAngle, double nearestObstacleDistance, double criticalDistance) {
        if (nearestObstacleDistance < criticalDistance) {
            // Obstacle detected, turn left to keep the wall on the right
            frontAngle = (frontAngle - 90) % 360;
            if (frontAngle < 0) {
                frontAngle += 360;
            }
        }

        // Always move forward
        x = x + Math.sin(Math.toRadians(frontAngle));
        y = y + Math.cos(Math.toRadians(frontAngle));

        return new double[]{x, y, frontAngle};
    }

    @Override
    public String getNavigationName() {
        return NAME;
    }
}
