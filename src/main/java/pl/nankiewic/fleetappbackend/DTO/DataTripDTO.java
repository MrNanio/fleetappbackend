package pl.nankiewic.fleetappbackend.DTO;

import java.util.Date;

public class DataTripDTO {
    private Short value;
    private Date date;

    public DataTripDTO(Short value, Date date) {
        this.value = value;
        this.date = date;
    }

    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
