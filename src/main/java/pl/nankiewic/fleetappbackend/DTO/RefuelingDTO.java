package pl.nankiewic.fleetappbackend.DTO;

import java.time.LocalDate;

public class RefuelingDTO {
    private Long id;
    private Long vehicleId;
    private Long userId;
    private LocalDate refuelingDate;
    private String litre;
    private String cost;
    private String description;

    public RefuelingDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getRefuelingDate() {
        return refuelingDate;
    }

    public void setRefuelingDate(LocalDate refuelingDate) {
        this.refuelingDate = refuelingDate;
    }

    public String getLitre() {
        return litre;
    }

    public void setLitre(String litre) {
        this.litre = litre;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
