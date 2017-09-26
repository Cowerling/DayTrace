package com.cowerling.daytrace.domain.user.form;

import com.cowerling.daytrace.annotation.Phone;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

public class UserProfileForm {
    private String alias;
    private String gender;
    private String birthday;

    @Phone
    private String phone;

    private String brief;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
