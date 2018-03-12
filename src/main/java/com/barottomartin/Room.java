package com.barottomartin;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private List<Rectangle> cells;
    private ImagePattern pattern;
    private int height;
    private int width;
    private int cellPixelSize;

    public Room(int width, int height, int cellPixelSize) {
        this.height = height;
        this.width = width;
        this.cellPixelSize = cellPixelSize;
        this.pattern = new ImagePattern(
                new Image(this.getClass().getClassLoader().getResourceAsStream("cell-texture.png")));
        this.cells = new ArrayList<>();
        createBounds();
        createRandomCells();
    }

    private void createBounds() {
        Rectangle r;
        // Top and bottom bounds
        for (int i = 0; i < width; i++) {
            r = new Rectangle(i * cellPixelSize, 0,
                    cellPixelSize, cellPixelSize);
            addCell(r);
            r = new Rectangle(i * cellPixelSize, (height - 1) * cellPixelSize,
                    cellPixelSize, cellPixelSize);
            addCell(r);
        }
        // Side bounds
        for (int i = 0; i < height; i++) {
            r = new Rectangle(0,i * cellPixelSize,
                    cellPixelSize, cellPixelSize);
            addCell(r);
            r = new Rectangle((width - 1) * cellPixelSize,i * cellPixelSize,
                    cellPixelSize, cellPixelSize);
            addCell(r);
        }
    }

    private void createRandomCells() {
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (Math.random() < 0.025) {
                    Rectangle r = new Rectangle(i * cellPixelSize, j * cellPixelSize,
                            cellPixelSize, cellPixelSize);
                    addCell(r);
                }
            }
        }
    }

    private void addCell(Rectangle r){
        r.setFill(pattern);
        cells.add(r);
    }

    public List<Rectangle> getCells() {
        return cells;
    }

    public int getCellPixelSize() {
        return cellPixelSize;
    }
}
