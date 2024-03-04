package com.n11.userservice.enums;

public enum EnumScore {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int value;

    EnumScore(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
