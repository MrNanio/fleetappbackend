package pl.nankiewic.fleetappbackend.DTO;

import pl.nankiewic.fleetappbackend.Entity.Vehicle;

public class DataTripUserDTO {
    Vehicle vehicle;
    Long trip;

    public DataTripUserDTO(Vehicle vehicle, Long trip) {
        this.vehicle = vehicle;
        this.trip = trip;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Long getTrip() {
        return trip;
    }

    public void setTrip(Long trip) {
        this.trip = trip;
    }
}
