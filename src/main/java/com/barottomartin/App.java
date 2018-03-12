package com.barottomartin;

import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Robot Simulator");
        SimulationPane pane = new SimulationPane();
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();

        pane.addEventHandler(EventType.ROOT, event -> {
            if (event.getEventType() == SimulationPane.RELOAD_EVENT) {
                scene.getWindow().sizeToScene();
            }
        });
    }
}
