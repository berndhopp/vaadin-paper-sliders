package org.joscha.facebook;

public enum FacebookScope {
    NAME("name"),
    EMAIL("email"),
    BIRTHDAY("user_birthday"),
    GENDER("user_gender");

    private final String stringRepresentation;

    FacebookScope(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
