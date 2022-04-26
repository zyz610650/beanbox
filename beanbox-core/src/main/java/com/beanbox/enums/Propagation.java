package com.beanbox.enums;

public enum Propagation {

    DEFAULT(0),
    REQUIRED(1),
    REQUIRES_NEW(2),
    SUPPORTS(3),
    MANDATORY(4),
    NOT_SUPPORTED(5),
    NEVER(6),
    NESTED(7);

    private int value;

    Propagation(int value) {
        this.value = value;
    }
}
