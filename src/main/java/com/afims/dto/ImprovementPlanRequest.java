package com.afims.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public class ImprovementPlanRequest {
    @NotBlank String title;
    @NotBlank String problemStatement;
    String evidence;
    String rootCause;
    @NotBlank String proposedAction;
    LocalDate targetDate;
    String priority;
    String expectedOutcome;
    String measurementMethod;
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
}
