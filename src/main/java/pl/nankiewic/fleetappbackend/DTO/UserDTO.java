package pl.nankiewic.fleetappbackend.DTO;

import pl.nankiewic.fleetappbackend.Entity.Enum.EnumRole;
import pl.nankiewic.fleetappbackend.Entity.Enum.EnumUserAccountStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class UserDTO {
    @NotNull
    private String email;
    @NotNull
    private EnumRole roles;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private EnumUserAccountStatus userStatus;
    private boolean isEnabled;

    public UserDTO(String email,
                   EnumRole roles,
                   Long id,
                   LocalDateTime createdAt,
                   LocalDateTime lastLoginAt,
                   EnumUserAccountStatus userStatus,
                   boolean isEnabled) {
        this.email = email;
        this.roles = roles;
        this.id = id;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
        this.userStatus = userStatus;
        this.isEnabled = isEnabled;
    }

    public UserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnumRole getRoles() {
        return roles;
    }

    public void setRoles(EnumRole roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public EnumUserAccountStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(EnumUserAccountStatus userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
