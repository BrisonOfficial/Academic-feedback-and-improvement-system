package com.afims.entity;
import jakarta.persistence.*;
import java.time.*;
@Entity
@Table(name="improvement_plans") public class ImprovementPlan {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
    @ManyToOne(optional=false)
    @JoinColumn(name="faculty_id") User faculty;
    @Column(nullable=false) String title;
    @Column(length=2000) String problemStatement;
    @Column(length=2000) String evidence;
    String rootCause;
    @Column(length=2000) String proposedAction;
    LocalDate targetDate;
    String priority;
    String expectedOutcome;
    String measurementMethod;
    @Enumerated(EnumType.STRING) Status status=Status.DRAFT;
    @Column(length=2000) String hodComments;
    LocalDateTime createdAt=LocalDateTime.now();
    LocalDateTime updatedAt=LocalDateTime.now();
    public enum Status {
        DRAFT,SUBMITTED,UNDER_REVIEW,APPROVED,CHANGES_REQUESTED,REJECTED,IMPLEMENTING,COMPLETED
    }
    public ImprovementPlan() {
    }
    public Long getId() {
        return id;
    }
    public User getFaculty() {
        return faculty;
    }
    public void setFaculty(User v) {
        faculty=v;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String v) {
        title=v;
    }
    public String getProblemStatement() {
        return problemStatement;
    }
    public void setProblemStatement(String v) {
        problemStatement=v;
    }
    public String getEvidence() {
        return evidence;
    }
    public void setEvidence(String v) {
        evidence=v;
    }
    public String getRootCause() {
        return rootCause;
    }
    public void setRootCause(String v) {
        rootCause=v;
    }
    public String getProposedAction() {
        return proposedAction;
    }
    public void setProposedAction(String v) {
        proposedAction=v;
    }
    public LocalDate getTargetDate() {
        return targetDate;
    }
    public void setTargetDate(LocalDate v) {
        targetDate=v;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String v) {
        priority=v;
    }
    public String getExpectedOutcome() {
        return expectedOutcome;
    }
    public void setExpectedOutcome(String v) {
        expectedOutcome=v;
    }
    public String getMeasurementMethod() {
        return measurementMethod;
    }
    public void setMeasurementMethod(String v) {
        measurementMethod=v;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status v) {
        status=v;
    }
    public String getHodComments() {
        return hodComments;
    }
    public void setHodComments(String v) {
        hodComments=v;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime v) {
        updatedAt=v;
    }
}
