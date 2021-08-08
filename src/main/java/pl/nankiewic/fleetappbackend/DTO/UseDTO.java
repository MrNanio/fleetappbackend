package pl.nankiewic.fleetappbackend.DTO;

import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class UseDTO {
    @NotNull
    private Long id;
    @NotNull
    private Long vehicleId;
    @NotNull
    private Long userId;
    @NotNull
    private Short trip;
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyy")
    private Date tripDate;
    private String tripType;
    private String description;

    public UseDTO() {
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

    public Short getTrip() {
        return trip;
    }

    public void setTrip(Short trip) {
        this.trip = trip;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
