package pl.nankiewic.fleetappbackend.DTO;

import pl.nankiewic.fleetappbackend.Entity.Vehicle;

import java.math.BigDecimal;

public class DataUseDTO {
    private Vehicle vehicle;
    private Long cost;

    public DataUseDTO(Vehicle vehicle, Long cost) {
        this.vehicle = vehicle;
        this.cost = cost;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }
}
