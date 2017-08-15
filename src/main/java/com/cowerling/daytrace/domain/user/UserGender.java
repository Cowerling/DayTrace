package com.cowerling.daytrace.domain.user;

import org.apache.commons.lang3.StringUtils;

public enum UserGender {
    MALE, FEMALE;

    @Override
    public String toString() {
        return StringUtils.capitalize(name());
    }
}
