package org.joscha.google;

public enum GoogleScope {
    PROFILE("https://www.googleapis.com/auth/userinfo.profile");

    private final String stringRepresentation;

    GoogleScope(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
