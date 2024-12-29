package com.example.scenebuildercode;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Product {
    private int id;
    private String name;
    private String color;
    private double price;
    private String generation;
    private final BooleanProperty selected;

    public Product(int id, String name, String color, String generation, double price) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.price = price;
        this.generation = generation;
        this.selected = new SimpleBooleanProperty(false);


    }
    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }
}

