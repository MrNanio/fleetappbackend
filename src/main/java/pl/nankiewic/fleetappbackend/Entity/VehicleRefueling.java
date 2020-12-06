package pl.nankiewic.fleetappbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "vehicle_refueling")
public class VehicleRefueling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date refuelingDate;
    private String litre;
    @Column(precision=8, scale=2, nullable = false)
    private BigDecimal cost;
    private String description;
    @ManyToOne
    @JoinColumn(name = "VehicleFk", nullable = false)
    private Vehicle vehicle;
    @ManyToOne
    @JoinColumn(name = "UserFk", nullable = false)
    private User user;

    public VehicleRefueling() {
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonIgnore
    public Date getRefuelingDate() {
        return refuelingDate;
    }

    public void setRefuelingDate(Date refuelingDate) {
        this.refuelingDate = refuelingDate;
    }

    @JsonIgnore
    public String getLitre() {
        return litre;
    }

    public void setLitre(String litre) {
        this.litre = litre;
    }
    @JsonIgnore
    public BigDecimal getCost() {
        return cost;
    }
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    @JsonIgnore
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @JsonIgnore
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
