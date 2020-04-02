package com.codingsaint.covidhelp.domains;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
 public class NeighbourUser extends PanacheEntity {

    @Id
    private Long id;
    @NotNull(message = "First Name is required")
    private String name;
    @NotNull(message = "Door/Flat Number is required")
    private String flatNumber;
    @NotNull(message = "Email is required")
    @Column(unique = true)
    private String email;
    @NotNull(message = "Mobile No is required")
    private String mobile;


    @NotNull(message = "Password is required")
    private String password;
    //private Integer iterationCount;
   // private  String salt;

    private String role;
    private Boolean active;

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

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public static NeighbourUser findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public static NeighbourUser findByRole(String admin) {
        return find("role", admin).firstResult();
    }
    public static List<NeighbourUser> findByActive(Boolean active) {
        return find("active", active).list();
    }
}