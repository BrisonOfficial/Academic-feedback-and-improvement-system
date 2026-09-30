package com.afims.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="feedback",uniqueConstraints=
@UniqueConstraint(name="uk_feedback_duplicate",columnNames= {
    "student_id","faculty_id","course","cycle_id"
}
)) public class Feedback {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
    @ManyToOne(optional=false)
    @JoinColumn(name="student_id") User student;
    @ManyToOne(optional=false)
    @JoinColumn(name="faculty_id") User faculty;
    @Column(nullable=false) String course;
    String subject;
    @ManyToOne(optional=false)
    @JoinColumn(name="cycle_id") FeedbackCycle cycle;
    int overallRating;
    boolean anonymous;
    @Column(length=2000) String comment;
    @Column(length=1000) String suggestion;
    LocalDateTime createdAt=LocalDateTime.now();
    public Feedback() {
    }
    public Long getId() {
        return id;
    }
    public User getStudent() {
        return student;
    }
    public void setStudent(User v) {
        student=v;
    }
    public User getFaculty() {
        return faculty;
    }
    public void setFaculty(User v) {
        faculty=v;
    }
    public String getCourse() {
        return course;
    }
    public void setCourse(String v) {
        course=v;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String v) {
        subject=v;
    }
    public FeedbackCycle getCycle() {
        return cycle;
    }
    public void setCycle(FeedbackCycle v) {
        cycle=v;
    }
    public int getOverallRating() {
        return overallRating;
    }
    public void setOverallRating(int v) {
        overallRating=v;
    }
    public boolean isAnonymous() {
        return anonymous;
    }
    public void setAnonymous(boolean v) {
        anonymous=v;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String v) {
        comment=v;
    }
    public String getSuggestion() {
        return suggestion;
    }
    public void setSuggestion(String v) {
        suggestion=v;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
