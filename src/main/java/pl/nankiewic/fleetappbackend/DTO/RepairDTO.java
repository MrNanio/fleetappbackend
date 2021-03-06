package pl.nankiewic.fleetappbackend.DTO;
import java.math.BigDecimal;
import java.sql.Date;
public class RepairDTO {
    private Long id;
    private Long vehicleId;
    private String title;
    private Date repairDate;
    private BigDecimal cost;
    private String description;

    public RepairDTO() {
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getRepairDate() {
        return repairDate;
    }
    public void setRepairDate(Date repairDate) {
        this.repairDate = repairDate;
    }
    public BigDecimal getCost() {
        return cost;
    }
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
