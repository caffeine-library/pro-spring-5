package org.binchoo.env.propagation.services;

public enum ExceptionLocation {

    NONE, BEFORE_UPDATE, AFTER_UPDATE;

    void throwException() {
        throw new RuntimeException(this.name());
    }
}
