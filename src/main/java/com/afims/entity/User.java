package com.afims.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="users", uniqueConstraints=
@UniqueConstraint(name="uk_users_email",columnNames="email"))public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String fullName;
    @Column(nullable=false) private String email;
    @Column(nullable=false) private String phone;
    @Column(nullable=false) private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false) private Role role;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false) private RegistrationStatus status=RegistrationStatus.PENDING;
    private String institution;
    private String department;
    private String institutionId;
    @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
    public enum Role {
        STUDENT,FACULTY,HOD,ADMIN
    }
    public enum RegistrationStatus {
        PENDING,ACTIVE,REJECTED,SUSPENDED
    }
    public User() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long v) {
        id=v;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String v) {
        fullName=v;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String v) {
        email=v;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String v) {
        phone=v;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String v) {
        password=v;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role v) {
        role=v;
    }
    public RegistrationStatus getStatus() {
        return status;
    }
    public void setStatus(RegistrationStatus v) {
        status=v;
    }
    public String getInstitution() {
        return institution;
    }
    public void setInstitution(String v) {
        institution=v;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String v) {
        department=v;
    }
    public String getInstitutionId() {
        return institutionId;
    }
    public void setInstitutionId(String v) {
        institutionId=v;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime v) {
        createdAt=v;
    }
}
