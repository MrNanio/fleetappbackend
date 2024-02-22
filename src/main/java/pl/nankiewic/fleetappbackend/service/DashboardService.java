package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.chart.provider.ChartDataProvider;
import pl.nankiewic.fleetappbackend.chart.ChartType;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.dto.chart.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.dto.chart.RefuelingChartWithDateView;
import pl.nankiewic.fleetappbackend.dto.chart.RefuelingChartWithVehicleDataView;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.repository.VehicleRefuelingRepository;
import pl.nankiewic.fleetappbackend.repository.VehicleUseRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



@Service
@AllArgsConstructor
public class DashboardService {

    private static final String PERMISSION_DENIED_ERROR = "permission.denied.error";

    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    private final VehicleRefuelingRepository vehicleRefuelingRepository;
    private final VehicleUseRepository vehicleUseRepository;
    private final ChartDataProvider chartDataProvider;

    /**
     private BigDecimal value;
     private ChartType name;
     */
    public List<ChartDataRespondDTO> getSummaryCostByVehicleId(Long vehicleId, LocalDate beginDate, LocalDate endDate) {
        if (checkExistAndPermissionComponent.accessToVehicle(vehicleId)) {
            return Arrays.stream(ChartType.values())
                    .map(type -> chartDataProvider.getSummaryCostByVehicleId(type, vehicleId, beginDate, endDate))
                    .collect(Collectors.toList());
        } else throw new PermissionDeniedException(PERMISSION_DENIED_ERROR);
    }

    /**
     private BigDecimal value;
     private ChartType name;
     */
    public List<ChartDataRespondDTO> getSummaryCostByVehicleOwner(LocalDate beginDate, LocalDate endDate) {
        var userId = JWTokenHelper.getJWTUserId();

        return Arrays.stream(ChartType.values())
                .map(type -> chartDataProvider.getSummaryCostByVehicleOwner(type, userId, beginDate, endDate))
                .collect(Collectors.toList());
    }

    /**
     BigDecimal getCost();
     String getVehicleData();
     */
    public List<RefuelingChartWithVehicleDataView> getFuelCostByVehicleAndDataAndUser(Long userId, LocalDate beginDate, LocalDate endDate) {
        return vehicleRefuelingRepository.fuelCostByVehicleAndUser(userId, beginDate, endDate);
    }

    /**
     BigDecimal getCost();
     String getVehicleData();
     */
    public List<RefuelingChartWithVehicleDataView> getFuelCostByVehicleAndDataByLoginUser(LocalDate beginDate, LocalDate endDate) {
        var userId = JWTokenHelper.getJWTUserId();

        return vehicleRefuelingRepository.fuelCostByVehicleAndUser(userId, beginDate, endDate);
    }

    /**
     *
     BigDecimal getValue();
     LocalDate getDate();
     */
    public List<RefuelingChartWithDateView> getFuelCostByVehicleAndData(Long vehicleId, LocalDate beginDate, LocalDate endDate) {
        if (checkExistAndPermissionComponent.accessToVehicle(vehicleId)) {
            return vehicleRefuelingRepository.refuelingByVehicle(vehicleId, beginDate, endDate);
        } else throw new PermissionDeniedException(PERMISSION_DENIED_ERROR);
    }


    /**
     BigDecimal getCost();
     String getVehicleData();
     */
    public List<RefuelingChartWithVehicleDataView> getSummaryRefuelingByVehicle(LocalDate beginDate, LocalDate endDate) {
        var userId = JWTokenHelper.getJWTUserId();

        return vehicleRefuelingRepository.sumOfRefuelingByVehicle(userId, beginDate, endDate);
    }


    //todo pioprawa
//    public List<ChartDataRespondDTO> getDistanceByVehicleAndDataAndUseType(Long vehicleId, LocalDate beginDate, LocalDate endDate) {
//        if (checkExistAndPermissionComponent.accessToVehicle(vehicleId)) {
//            return vehicleUseRepository.tripByVehicleAndDataAndTripType(vehicleId, beginDate, endDate);
//
//        } else throw new PermissionDeniedException(PERMISSION_DENIED_ERROR);
//    }

    //todo trip
//    public List<ChartDataRespondDTO> getSummaryUseByVehicle(String begin, String end) {
//        LocalDate beginDate = LocalDate.parse(begin);
//        LocalDate endDate = LocalDate.parse(end);
//        var userId = JWTokenHelper.getJWTUserId();
//        List<ChartDataRespondDTO> summary = new ArrayList<>();
//        List<DataUseDTO> list = vehicleUseRepository.sumOfTripByUser(userId, beginDate, endDate);
//        for (DataUseDTO dataDTO : list) {
//            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
//                    dataDTO.getCost().floatValue(),
//                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
//            summary.add(chartData);
//        }
//        return summary;
//    }
//
//    public List<ChartDataRespondDTO> getNumberOfUsesByVehicle(String begin, String end) {
//        LocalDate beginDate = LocalDate.parse(begin);
//        LocalDate endDate = LocalDate.parse(end);
//        var userId = JWTokenHelper.getJWTUserId();
//        List<ChartDataRespondDTO> summary = new ArrayList<>();
//        List<DataTripUserDTO> list = vehicleUseRepository.numberOfUsesByVehicleAndDataAndUser(userId, beginDate, endDate);
//        for (DataTripUserDTO dataDTO : list) {
//            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
//                    dataDTO.getTrip().floatValue(),
//                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
//            summary.add(chartData);
//        }
//        return summary;
//    }

//    public List<ChartDataRespondDTO> getDistanceByVehicleAndDataAndUser(String userId, String begin, String end) {
//
//        LocalDate beginDate = LocalDate.parse(begin);
//        LocalDate endDate = LocalDate.parse(end);
//        List<ChartDataRespondDTO> summary = new ArrayList<>();
//        List<DataTripUserDTO> list = vehicleUseRepository.tripByVehicleAndDataAndUser(Long.parseLong(userId), beginDate, endDate);
//
//        for (DataTripUserDTO dataDTO : list) {
//            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
//                    dataDTO.getTrip().floatValue(),
//                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
//            summary.add(chartData);
//        }
//        return summary;
//    }
//
//
//
//    public List<ChartDataRespondDTO> getDistanceByVehicleAndDataByLoginUser(String begin, String end) {
//        LocalDate beginDate = LocalDate.parse(begin);
//        LocalDate endDate = LocalDate.parse(end);
//        var userId = JWTokenHelper.getJWTUserId();
//        List<ChartDataRespondDTO> summary = new ArrayList<>();
//        List<DataTripUserDTO> list = vehicleUseRepository.tripByVehicleAndDataAndUser(userId, beginDate, endDate);
//
//
//        for (DataTripUserDTO dataDTO : list) {
//            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
//                    dataDTO.getTrip().floatValue(),
//                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
//            summary.add(chartData);
//        }
//        return summary;
//    }

//    public List<ChartDataRespondDTO> getDistanceByVehicleAndData(LocalDate beginDate, LocalDate endDate, Long vehicleId) {
//        if (checkExistAndPermissionComponent.accessToVehicle(Long.parseLong(vehicleId))) {
//
//            Vehicle vehicle = vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
//                    () -> new EntityNotFoundException("Bład przetwarzania"));
//            List<ChartDataRespondDTO> summary = new ArrayList<>();
//            List<DataTripDTO> list = vehicleUseRepository.tripByVehicleAndData(vehicle, begin, end);
//
//            for (DataTripDTO dataDTO : list) {
//                ChartDataRespondDTO chartData = new ChartDataRespondDTO(
//                        dataDTO.getValue().floatValue(),
//                        dataDTO.getDate().toString());
//                summary.add(chartData);
//            }
//            return summary;
//        } else throw new PermissionDeniedException(PERMISSION_DENIED_ERROR);
//    }

}