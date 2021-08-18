package pl.nankiewic.fleetappbackend.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DataRefuelingDTO {
    private BigDecimal value;
    private LocalDate date;

    public DataRefuelingDTO(BigDecimal value, LocalDate date) {
        this.value = value;
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
