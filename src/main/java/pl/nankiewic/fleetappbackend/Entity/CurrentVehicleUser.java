package pl.nankiewic.fleetappbackend.Entity;

import javax.persistence.*;

@Entity
@Table(name = "current_vehicle_users")
public class CurrentVehicleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UserFk", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "VehicleFk", nullable = false, unique = true)
    private Vehicle vehicle;

    public CurrentVehicleUser() {
    }

    public CurrentVehicleUser(User user, Vehicle vehicle) {
     this.user=user;
     this.vehicle=vehicle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
