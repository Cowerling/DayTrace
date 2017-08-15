package com.cowerling.daytrace.domain.user;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Alias("user")
public class User {
    private Long id;

    @NotNull
    @Size(min = 6, max = 30, message = "{name.size}")
    private String name;

    @NotNull
    @Size(min = 6, max = 30, message = "{password.size}")
    private String password;

    @NotNull
    @Email(message = "{email.valid}")
    private String email;

    private UserRole userRole;

    private Date registerDate;

    private String photo;

    public User() {
        userRole = UserRole.USER;
        registerDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
