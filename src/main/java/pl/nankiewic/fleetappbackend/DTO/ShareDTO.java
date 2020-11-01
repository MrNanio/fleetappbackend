package pl.nankiewic.fleetappbackend.DTO;

import java.util.List;

public class ShareDTO {
    private Long userId;
    private List<String> vehicleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(List<String> vehicleId) {
        this.vehicleId = vehicleId;
    }
}
