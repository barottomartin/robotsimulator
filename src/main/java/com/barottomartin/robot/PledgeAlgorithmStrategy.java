package com.barottomartin.robot;

public class PledgeAlgorithmStrategy implements NavigationStrategy {

    public static final String NAME = "Pledge Algorithm";
    private double totalAngleTurned = 0;
    private boolean wallFollowing = false;

    @Override
    public double[] move(long now, double x, double y, double frontAngle, double nearestObstacleDistance, double criticalDistance) {
        if (nearestObstacleDistance < criticalDistance && !wallFollowing) {
            wallFollowing = true;
            // Hit a wall, start following it by turning left
            double turnAngle = -90;
            frontAngle = (frontAngle + turnAngle) % 360;
            if (frontAngle < 0) {
                frontAngle += 360;
            }
            totalAngleTurned += turnAngle;
        } else if (wallFollowing) {
            if (totalAngleTurned == 0 && nearestObstacleDistance >= criticalDistance) {
                // Pledge fulfilled and path is clear, leave the wall
                wallFollowing = false;
            } else {
                // Still following the wall
                if (nearestObstacleDistance < criticalDistance) {
                    // Still facing a wall, turn left again
                    double turnAngle = -90;
                    frontAngle = (frontAngle + turnAngle) % 360;
                    if (frontAngle < 0) {
                        frontAngle += 360;
                    }
                    totalAngleTurned += turnAngle;
                } else {
                    // Try to hug the wall by turning right slightly
                    double turnAngle = 5;
                    frontAngle = (frontAngle + turnAngle) % 360;
                    totalAngleTurned += turnAngle;
                }
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
