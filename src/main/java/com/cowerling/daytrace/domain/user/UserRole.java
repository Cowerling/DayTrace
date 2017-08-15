package com.cowerling.daytrace.domain.user;

public enum UserRole {
    SUPER_ADMIN("Super Administrator"), ADMIN("Administrator"), ADVAN_USER("Advanced User"), USER("User");

    private String description;

    UserRole(String description) {
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
