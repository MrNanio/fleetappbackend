package pl.nankiewic.fleetappbackend.DTO;

public class IdDTO {
    private String email;
    private String newPassword;
    private Long id;
    private String token;

    public IdDTO(String email, String newPassword, Long id, String token) {
        this.email = email;
        this.newPassword = newPassword;
        this.id = id;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
