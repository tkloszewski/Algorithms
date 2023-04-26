package com.smart.tkl.lib.dynamic;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Thief {

    public Sack knapSack(List<Item> items, int weight) {
        Sack sack = new Sack(weight);

        GridSack gridSack = new GridSack(sortItems(items), weight);

        for(int i = 1; i < gridSack.rowsCount(); i++) {
            for(int w = 1; w < gridSack.columnsCount(); w++) {
                Item currentItem = gridSack.currentItem(i);

                int subSackColumn = Math.max(0, w  - currentItem.weight);

                GridSack.GridCell prevRowMaxCell = gridSack.getCell(i - 1, w);
                GridSack.GridCell prevRowRemainingWeightMaxCell = gridSack.getCell(i - 1, subSackColumn);

                //If value of new item plus max value of sack for weight = current weight - item weight is less than
                //value of current suck -> then don't add new item. Otherwise add new item.
                if(prevRowMaxCell.totalValue > currentItem.value + prevRowRemainingWeightMaxCell.totalValue) {
                   gridSack.setCell(i, w, prevRowMaxCell);
                }
                else {
                    gridSack.setCell(i, w, new GridSack.GridCell(currentItem, prevRowRemainingWeightMaxCell));
                }
            }
        }

        GridSack.GridCell maxCell = gridSack.getCell(gridSack.rowsCount() - 1, gridSack.columnsCount() - 1);

        sack.setItems(maxCell.items);
        sack.setItemsValue(maxCell.totalValue);
        sack.setItemsWeight(GridSack.totalWeight(maxCell.items));

        return sack;
    }

    private List<Item> sortItems(List<Item> items) {
        return items.stream().sorted((Comparator.comparing(i -> i.weight))).collect(Collectors.toList());
    }
}
