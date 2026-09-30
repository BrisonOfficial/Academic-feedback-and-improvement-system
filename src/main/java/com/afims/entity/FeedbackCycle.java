package com.afims.entity;
import jakarta.persistence.*;
import java.time.*;
@Entity
@Table(name="feedback_cycles") public class FeedbackCycle {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
    @Column(nullable=false) String name;
    String academicYear;
    Integer semester;
    LocalDate startDate,endDate;
    @Enumerated(EnumType.STRING) Status status;
    public enum Status {
        UPCOMING,ACTIVE,CLOSED
    }
    public FeedbackCycle() {
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
    public String getAcademicYear() {
        return academicYear;
    }
    public void setAcademicYear(String v) {
        academicYear=v;
    }
    public Integer getSemester() {
        return semester;
    }
    public void setSemester(Integer v) {
        semester=v;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate v) {
        startDate=v;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate v) {
        endDate=v;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status v) {
        status=v;
    }
}
