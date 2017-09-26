package com.cowerling.daytrace.domain.user;

import java.util.Arrays;

public enum UserMedal {
    CREATOR("Great Creator", 1),
    BUILDER_I("Significant Builder I", 2),
    BUILDER_II("Significant Builder II", 3),
    BUILDER_III("Significant Builder III", 4);

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

    public static UserMedal[] award(UserRole userRole) {
        return Arrays.stream(UserMedal.values()).filter(x -> x.level > userRole.ordinal() + 1 && userRole.ordinal() < UserRole.ADVAN_USER.ordinal()).toArray(size -> new UserMedal[size]);
    }
}
