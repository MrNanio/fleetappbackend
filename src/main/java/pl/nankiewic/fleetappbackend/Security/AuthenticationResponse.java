package pl.nankiewic.fleetappbackend.Security;

public class AuthenticationResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private Long id;
    private String roles;


    public AuthenticationResponse(String token, String email, Long id, String roles) {
        this.token = token;
        this.email = email;
        this.id = id;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

}
