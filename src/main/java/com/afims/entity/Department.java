package com.afims.entity;
import jakarta.persistence.*;
@Entity
@Table(name="departments") public class Department {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
    @Column(nullable=false,unique=true) String name;
    String code;
    public Department() {
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String v) {
        name=v;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String v) {
        code=v;
    }
}
