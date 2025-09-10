package com.recipeapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    APPETIZER("Appetizer"),
    BEVERAGE("Beverage"),
    MAINCOURSE("Main Course"),
    DESSERT("Dessert");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Category fromJson(String value) {
        if (value == null) {
            return null;
        }
        return Category.valueOf(value.toUpperCase());
    }
}
