package com.barottomartin;

import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private List<Rectangle> cells;
    private int height;
    private int width;
    private int cellPixelSize;

    public Room(int width, int height, int cellPixelSize) {
        this.height = height;
        this.width = width;
        this.cellPixelSize = cellPixelSize;

        cells = new ArrayList<>();
        createBounds();
        createRandomCells();
    }

    private void createBounds() {
        //replace this with lines of cells
        cells.add(new Rectangle(0, -1 * cellPixelSize, width * cellPixelSize, cellPixelSize));
        cells.add(new Rectangle(-1 * cellPixelSize, 0, cellPixelSize, height * cellPixelSize));
        cells.add(new Rectangle(0, height * cellPixelSize, width * cellPixelSize, cellPixelSize));
        cells.add(new Rectangle(width * cellPixelSize, 0, cellPixelSize, height * cellPixelSize));
    }

    private void createRandomCells() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (Math.random() < 0.025) {
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
