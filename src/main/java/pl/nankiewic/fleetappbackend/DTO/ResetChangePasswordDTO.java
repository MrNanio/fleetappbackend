package pl.nankiewic.fleetappbackend.DTO;

public class ResetChangePasswordDTO {
    private String user;
    private String new_password;
    private Long identification;
    private String ver_token;
    private String res_code;

    public ResetChangePasswordDTO(String user, String new_password, Long identification, String ver_token, String res_code) {
        this.user = user;
        this.new_password = new_password;
        this.identification = identification;
        this.ver_token = ver_token;
        this.res_code = res_code;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public Long getIdentification() {
        return identification;
    }

    public void setIdentification(Long identification) {
        this.identification = identification;
    }

    public String getVer_token() {
        return ver_token;
    }

    public void setVer_token(String ver_token) {
        this.ver_token = ver_token;
    }

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }
}
