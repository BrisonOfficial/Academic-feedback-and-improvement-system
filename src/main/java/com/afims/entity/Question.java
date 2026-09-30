package com.afims.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="questions") public class Question {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
    @Column(nullable=false) String questionText;
    String category;
    String ratingType="RATING_1_5";
    @Column(name="required_flag") boolean required=true;
    boolean active=true;
    Integer displayOrder=1;
    String targetRole="STUDENT";
    LocalDateTime createdAt=LocalDateTime.now();
    public Question() {
    }
    public Long getId() {
        return id;
    }
    public String getQuestionText() {
        return questionText;
    }
    public void setQuestionText(String v) {
        questionText=v;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String v) {
        category=v;
    }
    public String getRatingType() {
        return ratingType;
    }
    public void setRatingType(String v) {
        ratingType=v;
    }
    public boolean isRequired() {
        return required;
    }
    public void setRequired(boolean v) {
        required=v;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean v) {
        active=v;
    }
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    public void setDisplayOrder(Integer v) {
        displayOrder=v;
    }
    public String getTargetRole() {
        return targetRole;
    }
    public void setTargetRole(String v) {
        targetRole=v;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
