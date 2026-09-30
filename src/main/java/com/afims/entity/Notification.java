package com.afims.entity;
import jakarta.persistence.*;
import java.time.*;
@Entity
@Table(name="notifications") public class Notification {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
    @ManyToOne(optional=false)
    @JoinColumn(name="user_id") User user;
    String title;
    @Column(length=1000) String message;
    String type;
    boolean readFlag=false;
    LocalDateTime createdAt=LocalDateTime.now();
    public Notification() {
    }
    public Notification(User u,String t,String m,String ty) {
        user=u;
        title=t;
        message=m;
        type=ty;
    }
    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public String getTitle() {
        return title;
    }
    public String getMessage() {
        return message;
    }
    public String getType() {
        return type;
    }
    public boolean isReadFlag() {
        return readFlag;
    }
    public void setReadFlag(boolean v) {
        readFlag=v;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
