package pl.nankiewic.fleetappbackend.dto.chart;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RefuelingChartWithDateView {

    BigDecimal getValue();

    LocalDate getDate();

}
