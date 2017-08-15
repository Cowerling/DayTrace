package com.cowerling.daytrace.domain.user;

public enum UserMedal {
    CREATOR("Great Creator", 1);

    private String description;
    private int level;

    UserMedal(String description, int level) {
        this.description = description;
        this.level = level;
    }

    @Override
    public String toString() {
        return description;
    }

    public String format() {
        return name().toLowerCase().replace('_', '-');
    }

    public int getLevel() {
        return level;
    }
}
