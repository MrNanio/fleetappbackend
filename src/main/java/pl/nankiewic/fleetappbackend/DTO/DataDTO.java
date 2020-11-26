package pl.nankiewic.fleetappbackend.DTO;

import pl.nankiewic.fleetappbackend.Entity.Vehicle;

import java.math.BigDecimal;

public class DataDTO {
    Vehicle vehicle;
    BigDecimal cost;

    public DataDTO(Vehicle vehicle, BigDecimal cost) {
        this.vehicle = vehicle;
        this.cost = cost;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
