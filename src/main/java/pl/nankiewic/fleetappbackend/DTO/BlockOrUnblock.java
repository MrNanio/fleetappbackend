package pl.nankiewic.fleetappbackend.DTO;

public class BlockOrUnblock {
    Long id;
    String userStatus;

    public BlockOrUnblock() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
