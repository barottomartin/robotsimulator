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
        Rectangle r;
        // Top and bottom bounds
        for (int i = 0; i < width; i++) {
            r = new Rectangle(i * cellPixelSize, 0,
                    cellPixelSize, cellPixelSize);
            cells.add(r);
            r = new Rectangle(i * cellPixelSize, (height - 1) * cellPixelSize,
                    cellPixelSize, cellPixelSize);
            cells.add(r);
        }
        // Side bounds
        for (int i = 0; i < height; i++) {
            r = new Rectangle(0,i * cellPixelSize,
                    cellPixelSize, cellPixelSize);
            cells.add(r);
            r = new Rectangle((width - 1) * cellPixelSize,i * cellPixelSize,
                    cellPixelSize, cellPixelSize);
            cells.add(r);
        }
    }

    private void createRandomCells() {
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
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
