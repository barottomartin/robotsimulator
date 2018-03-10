package com.barottomartin;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        int width = 80;
        int height = 60;
        int cellPixelSize = 10;

        Room room = new Room(width, height ,cellPixelSize);
        Robot robot = new Robot(room);
        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundImage(new Image(
                this.getClass().getClassLoader().getResourceAsStream("bg-texture.png")),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        for (Rectangle r: room.getCells()){
            pane.getChildren().add(r);
        }
        pane.getChildren().add(robot.getShape());

        Scene scene = new Scene(pane, room.getWidth() * room.getCellPixelSize(),
                room.getHeight() * room.getCellPixelSize());
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                robot.move(now);
                //Nice line to display history of navigation until that feature is done
                //group.getChildren().add(robot.rayCast());
            }
        };

        timer.start();
    }
}
