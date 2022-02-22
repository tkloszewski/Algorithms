package com.smart.tkl.dynamic;

import java.util.List;

public class Sack {

    private int weight;

    private List<Item> items;
    private int itemsWeight;
    private int itemsValue;

    public Sack(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getItemsWeight() {
        return itemsWeight;
    }

    public void setItemsWeight(int itemsWeight) {
        this.itemsWeight = itemsWeight;
    }

    public int getItemsValue() {
        return itemsValue;
    }

    public void setItemsValue(int itemsValue) {
        this.itemsValue = itemsValue;
    }
}
