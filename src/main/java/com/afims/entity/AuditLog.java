package com.afims.entity;
import jakarta.persistence.*;
import java.time.*;
@Entity
@Table(name="audit_logs") public class AuditLog {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
    @ManyToOne
    @JoinColumn(name="user_id") User user;
    String action;
    String entityName;
    Long entityId;
    String ipAddress;
    LocalDateTime timestamp=LocalDateTime.now();
    public AuditLog() {
    }
    public AuditLog(User u,String a,String e,Long id,String ip) {
        user=u;
        action=a;
        entityName=e;
        entityId=id;
        ipAddress=ip;
    }
    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public String getAction() {
        return action;
    }
    public String getEntityName() {
        return entityName;
    }
    public Long getEntityId() {
        return entityId;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
