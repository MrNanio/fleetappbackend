package pl.nankiewic.fleetappbackend.DTO;

public class ChartDataRespondDTO {
    Float value;
    String name;

    public ChartDataRespondDTO(Float value, String name) {
        this.value = value;
        this.name = name;
    }

    public ChartDataRespondDTO() {
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
