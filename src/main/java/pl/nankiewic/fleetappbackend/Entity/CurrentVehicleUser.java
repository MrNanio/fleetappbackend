package pl.nankiewic.fleetappbackend.Entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "current_vehicle_users")
public class CurrentVehicleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "vehicle_id", nullable = false, unique = true)
    private Vehicle vehicle;

    public CurrentVehicleUser(User user, Vehicle vehicle) {
        this.user = user;
        this.vehicle = vehicle;
    }

}
