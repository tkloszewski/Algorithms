package com.smart.tkl.dynamic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

public class GridSack {

    private GridCell grid[][];
    private int rowsCount;
    private int columnsCount;
    private List<Item> items;

    public GridSack(List<Item> items, int weight) {
        this.items = items;

        this.rowsCount = items.size() + 1;
        this.columnsCount = weight + 1;
        grid = new GridCell[this.rowsCount][];
        for(int  i = 0; i < this.rowsCount; i++) {
            grid[i] = new GridCell[this.columnsCount];
        }
        for(int i = 0; i < this.columnsCount; i++) {
            grid[0][i] = new GridCell();
        }
        for(int i = 1; i < this.rowsCount; i++) {
            grid[i][0] = new GridCell();
        }
    }

    public void setCell(int row, int column, GridCell gridCell) {
        grid[row][column] = new GridCell(gridCell);
    }

    public GridCell getCell(int row, int column) {
        return grid[row][column];
    }

    public Integer getValue(int row, int column) {
        GridCell gridCell = grid[row][column];
        return gridCell.totalValue;
    }

    public List<Item> getItems(int row, int column) {
        GridCell gridCell = grid[row][column];
        return gridCell.items;
    }

    public Item currentItem(int row) {
        return this.items.get(row - 1);
    }

    public int rowsCount() {
        return rowsCount;
    }

    public int columnsCount() {
        return columnsCount;
    }

    static class GridCell {
        List<Item> items;
        Integer totalValue;

        public GridCell() {
            items = new ArrayList<>();
            totalValue = 0;
        }

        public GridCell(List<Item> items) {
             this.items = items;
             this.totalValue = totalValue(items);
        }

        public GridCell(Item item) {
            this.items = Collections.singletonList(item);
            this.totalValue = item.value;
        }

        public GridCell(GridCell cell) {
            this.items = cell.items;
            this.totalValue = cell.totalValue;
        }

        public GridCell(Item item, GridCell cell) {
            this.items = new ArrayList<>();
            this.items.add(item);
            this.items.addAll(cell.items);
            this.totalValue = item.value + cell.totalValue;
        }
    }

    public static Integer totalValue(List<Item> items) {
        return items.stream().map(i -> i.value).reduce(0, Integer::sum);
    }

    public static Integer totalWeight(List<Item> items) {
        return items.stream().map(i -> i.weight).reduce(0, Integer::sum);
    }

}
