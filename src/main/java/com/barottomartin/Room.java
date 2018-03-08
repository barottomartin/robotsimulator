package com.barottomartin;

import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private List<Rectangle> cells;
    private int height;
    private int width;
    private int cellPixelSize;

    public Room(int width, int height, int cellPixelSize){
        this.height = height;
        this.width = width;
        this.cellPixelSize = cellPixelSize;

        cells = new ArrayList<>();
        createRandomCells();
    }

    private void createRandomCells(){
        for (int i = 0; i < width; i++){
            for (int j = 0; j < width; j++) {
                if (Math.random() < 0.03) {
                    Rectangle r = new Rectangle(i * cellPixelSize, j * cellPixelSize,
                            cellPixelSize, cellPixelSize);
                    cells.add(r);
                }
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<Rectangle> getCells() {
        return cells;
    }

    public int getCellPixelSize() {
        return cellPixelSize;
    }
}
