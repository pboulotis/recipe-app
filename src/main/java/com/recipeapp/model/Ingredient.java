package com.recipeapp.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Ingredient {

    private String name;
    private String measurement;
    private boolean optional = false;

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(String name, String measurement, boolean optional) {
        this.name = name;
        this.measurement = measurement;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
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
        return optional == that.optional && Objects.equals(name, that.name) && Objects.equals(measurement, that.measurement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, measurement, optional);
    }
}

