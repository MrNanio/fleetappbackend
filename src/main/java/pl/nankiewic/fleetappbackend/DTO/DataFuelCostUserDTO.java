package pl.nankiewic.fleetappbackend.DTO;

import pl.nankiewic.fleetappbackend.Entity.Vehicle;

import java.math.BigDecimal;

public class DataFuelCostUserDTO {
    private BigDecimal cost;
    private Vehicle vehicle;

    public DataFuelCostUserDTO(BigDecimal cost, Vehicle vehicle) {
        this.cost = cost;
        this.vehicle = vehicle;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
