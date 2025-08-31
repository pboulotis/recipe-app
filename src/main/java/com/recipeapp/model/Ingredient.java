package com.recipeapp.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Ingredient {

    private String name;
    private double quantity;
    private String unit;
    private boolean optional;

    public Ingredient() {}

    public Ingredient(String name, double quantity, String unit, boolean optional) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Double.compare(quantity, that.quantity) == 0
                && optional == that.optional
                && Objects.equals(name, that.name)
                && Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, unit, optional);
    }
}
