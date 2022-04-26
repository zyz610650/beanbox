package com.beanbox.enums;

public enum Propagation {

    PROPAGATION_DEFAULT(1),
    PROPAGATION_REQUIRED(1),
    PROPAGATION_SUPPORTS(2),
    PROPAGATION_MANDATORY(3),
    PROPAGATION_REQUIRES_NEW(4),
    PROPAGATION_NOT_SUPPORTED(5),
    PROPAGATION_NEVER(6),
    PROPAGATION_NESTED(7);

    private int value;

    Propagation(int value) {
        this.value = value;
    }
}
