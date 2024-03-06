package com.n11.restaurantservice.errormessage;

import com.n11.restaurantservice.general.BaseErrorMessage;

public enum RestaurantErrorMessage implements BaseErrorMessage {
    NO_RESTAURANTS_FOUND("No restaurants found!"),
    DUPLICATE_RESTAURANT_NAME("There is a restaurant with the same name!"),
    RESTAURANT_ALREADY_EXISTS("Restaurant already exists!");

    private final String message;

    RestaurantErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
