package pl.nankiewic.fleetappbackend.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class DataRefuelingDTO {
    private BigDecimal value;
    private Date date;

    public DataRefuelingDTO(BigDecimal value, Date date) {
        this.value = value;
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
