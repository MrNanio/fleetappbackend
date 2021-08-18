package pl.nankiewic.fleetappbackend.DTO;

import java.time.LocalDate;

public class DataTripDTO {
    private Short value;
    private LocalDate date;

    public DataTripDTO(Short value, LocalDate date) {
        this.value = value;
        this.date = date;
    }

    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
