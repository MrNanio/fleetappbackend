package pl.nankiewic.fleetappbackend.dto.chart;

import java.math.BigDecimal;

public interface RefuelingChartWithVehicleDataView {

    BigDecimal getCost();

    String getVehicleData();

}