package com.codingsaint.covidhelp.domains;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@Entity
public class NeighbourNeeds  extends PanacheEntity {
    @Id
    private Long id;
    @Column(unique = true)
    private String needId;
    private String name;
    private String email;
    private String flatNumber;
    private String item;
    private String quantity;
    private Boolean isFullfilled;
    private Boolean  isPicked;
    private String  fullFilledBy;
    private String  pickedBy;
    private LocalDate date;
    private String mobile;

    public String getNeedId() {
        return needId;
    }

    public void setNeedId(String needId) {
        this.needId = needId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean getFullfilled() {
        return isFullfilled;
    }

    public void setFullfilled(Boolean fullfilled) {
        isFullfilled = fullfilled;
    }

    public Boolean getPicked() {
        return isPicked;
    }

    public void setPicked(Boolean picked) {
        isPicked = picked;
    }

    public String getFullFilledBy() {
        return fullFilledBy;
    }

    public void setFullFilledBy(String fullFilledBy) {
        this.fullFilledBy = fullFilledBy;
    }

    public String getPickedBy() {
        return pickedBy;
    }

    public void setPickedBy(String pickedBy) {
        this.pickedBy = pickedBy;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static NeighbourNeeds findByNeedId(String needId) {
        return find("needId", needId).firstResult();
    }

    @Override
    public String toString() {
        return "NeighbourNeeds{" +
                "id=" + id +
                ", needId='" + needId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", flatNumber='" + flatNumber + '\'' +
                ", item='" + item + '\'' +
                ", quantity='" + quantity + '\'' +
                ", isFullfilled=" + isFullfilled +
                ", isPicked=" + isPicked +
                ", fullFilledBy='" + fullFilledBy + '\'' +
                ", pickedBy='" + pickedBy + '\'' +
                ", date=" + date +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
