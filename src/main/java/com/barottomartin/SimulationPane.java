package com.barottomartin;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class SimulationPane extends HBox {

    private Room room;
    private Robot robot;
    private AnimationTimer timer;
    public static EventType RELOAD_EVENT = new EventType<>("RESIZE EVENT");


    public SimulationPane() {
        super();
        createSimulationElements();
        initUI();
    }

    private void createSimulationElements(){
        Properties props = Properties.getInstance();
        this.room = new Room(Integer.valueOf(props.getProperty("width")),
                Integer.valueOf(props.getProperty("height")),
                Integer.valueOf(props.getProperty("cellPixelSize")));
        this.robot = new Robot(room, Integer.valueOf(props.getProperty("robotRadius")));
        this.timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                robot.move(now);
                //Nice line to display history of navigation until that feature is done
                //group.getChildren().add(robot.rayCast());
            }
        };
    }

    private void reload(){
        this.getChildren().removeAll(this.getChildren());
        createSimulationElements();
        initUI();
        this.fireEvent(new Event(RELOAD_EVENT));
    }

    private void initUI(){
        Pane roomPane = new Pane();
        roomPane.setBackground(new Background(new BackgroundImage(new Image(
                this.getClass().getClassLoader().getResourceAsStream("bg-texture.png")),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        for (Rectangle r: room.getCells()){
            roomPane.getChildren().add(r);
        }
        roomPane.getChildren().add(robot.getShape());
        roomPane.addEventFilter(MouseEvent.MOUSE_CLICKED, this::placeRobot);
        this.getChildren().add(roomPane);

        Button playButton = new Button("Play");
        playButton.setOnAction((ActionEvent e) -> {
            timer.start();
        });
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction((ActionEvent e) -> {
            timer.stop();
        });
        Button resetButton = new Button("Reset");
        resetButton.setOnAction((ActionEvent e) -> {
            timer.stop();
            reload();
        });

        Label l1 = new Label("Width");
        TextField widthText = new TextField();
        addNumericChangeListener(widthText, "width");
        Label l2 = new Label("Height");
        TextField heightText = new TextField();
        addNumericChangeListener(heightText, "height");
        Label l3 = new Label("Cell px size");
        TextField cellPxText = new TextField();
        addNumericChangeListener(cellPxText, "cellPixelSize");
        Label l4 = new Label("Robot Radius");
        TextField radiusText = new TextField();
        addNumericChangeListener(radiusText, "robotRadius");

        GridPane grid = new GridPane();
        HBox buttonsPane = new HBox();
        buttonsPane.getChildren().add(playButton);
        buttonsPane.getChildren().add(pauseButton);
        buttonsPane.getChildren().add(resetButton);
        buttonsPane.setSpacing(10);
        grid.add(buttonsPane, 0, 0, 2,1);
        grid.add(l1,0, 1);
        grid.add(widthText,1, 1);
        grid.add(l2,0, 2);
        grid.add(heightText,1, 2);
        grid.add(l3,0, 3);
        grid.add(cellPxText,1, 3);
        grid.add(l4,0, 4);
        grid.add(radiusText,1, 4);
        grid.setHgap(2);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        this.getChildren().add(grid);
    }

    private void addNumericChangeListener(TextField textField, String confField){
        textField.textProperty().setValue(Properties.getInstance().getProperty(confField));
        textField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.equals("")){
                try {
                    Integer.valueOf(newValue);
                    Properties.getInstance().setProperty(confField, newValue);
                } catch (NumberFormatException e) {
                    textField.textProperty().setValue(oldValue);
                }
            }
        }));
    }

    private void placeRobot(MouseEvent event) {
        robot.setX(event.getX());
        robot.setY(event.getY());
    }

}
