package com.barottomartin.robot;

public interface NavigationStrategy {

    /**
     *
     * @param now Current time
     * @param x Actual x position of the Robot
     * @param y Actual y position of the Robot
     * @param frontAngle Actual orientation of the Robot
     * @param nearestObstacleDistance Distance of the nearest obstacle from the Robot's perspective
     * @param criticalDistance Distance considered critical between the Robot and an obstacle
     * @return An array with the new x position, y position and frontAngle of the Robot
     */
    double[] move(long now, double x, double y, double frontAngle, double nearestObstacleDistance,
                  double criticalDistance);

    String getNavigationName();

}
