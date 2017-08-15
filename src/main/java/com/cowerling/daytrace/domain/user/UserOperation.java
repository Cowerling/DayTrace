package com.cowerling.daytrace.domain.user;

public enum UserOperation {
    LOGIN("Login");

    private String description;

    UserOperation(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public String format() {
        return name().toLowerCase().replace('_', '-');
    }
}
