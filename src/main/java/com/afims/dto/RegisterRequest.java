package com.afims.dto;
import com.afims.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public class RegisterRequest {
    @NotBlank    private String fullName;
    @Email
    @NotBlank    private String email;
    @NotBlank
    @Size(min = 10, max = 15)    private String phone;
    @Size(min = 8)    private String password;
    @NotBlank    private String confirmPassword;
    @NotBlank    private String institution;
    @NotBlank    private String department;
    @NotBlank    private String institutionId;
    @NotNull    private User.Role role;
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public String getInstitution() {
        return institution;
    }
    public void setInstitution(String institution) {
        this.institution = institution;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getInstitutionId() {
        return institutionId;
    }
    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }
    public User.Role getRole() {
        return role;
    }
    public void setRole(User.Role role) {
        this.role = role;
    }
}
