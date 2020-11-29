package pl.nankiewic.fleetappbackend.DTO;


public class DataUseTypeDTO {
    String type;
    Long cost;

    public DataUseTypeDTO(String type, Long cost) {
        this.type = type;
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }
}
