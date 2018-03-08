package com.barottomartin;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Room room = new Room(80, 60 ,10);
        Robot robot = new Robot(room);
        Group group = new Group();
        Scene scene = new Scene(group, room.getWidth() * room.getCellPixelSize(),
                room.getHeight() * room.getCellPixelSize());

        for (Rectangle r: room.getCells()){
            group.getChildren().add(r);
        }
        group.getChildren().add(robot.getShape());

        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                robot.move(now);
            }
        };

        timer.start();
    }
}
