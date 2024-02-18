package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.config.jwt.JWTokenHelper;
import pl.nankiewic.fleetappbackend.dto.*;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.repository.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DashboardService {

    private static final float FLOAT_VALUE_OF_ZERO = 0.0F;

    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;
    private final VehicleInspectionRepository vehicleInspectionRepository;
    private final VehicleInsuranceRepository vehicleInsuranceRepository;
    private final VehicleRefuelingRepository vehicleRefuelingRepository;
    private final VehicleRepairRepository vehicleRepairRepository;
    private final VehicleUseRepository vehicleUseRepository;
    private final VehicleRepository vehicleRepository;

    public List<ChartDataRespondDTO> getVehicleCostByCategory(String vehicleId, String begin, String end) {
        if (checkExistAndPermissionComponent.accessToVehicle(Long.parseLong(vehicleId))) {
            LocalDate beginDate = LocalDate.parse(begin);
            LocalDate endDate = LocalDate.parse(end);

            Vehicle vehicle = vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
                    () -> new EntityNotFoundException("Bład przetwarzania"));
            List<ChartDataRespondDTO> summary = new ArrayList<>();
            ChartDataRespondDTO chartDataRepair = new ChartDataRespondDTO(
                    vehicleCostByCategoriesRepair(vehicle, beginDate, endDate), "Naprawy");
            summary.add(chartDataRepair);
            ChartDataRespondDTO chartDataInsurance = new ChartDataRespondDTO(
                    vehicleCostByCategoriesInsurance(vehicle, beginDate, endDate), "Ubezpieczenia");
            summary.add(chartDataInsurance);
            ChartDataRespondDTO chartDataInspection = new ChartDataRespondDTO(
                    vehicleCostByCategoriesInspection(vehicle, beginDate, endDate), "Przeglądy");
            summary.add(chartDataInspection);
            ChartDataRespondDTO chartDataRefueling = new ChartDataRespondDTO(
                    vehicleCostByCategoriesRefueling(vehicle, beginDate, endDate), "Paliwo");
            summary.add(chartDataRefueling);
            return summary;
        } else throw new PermissionDeniedException("Odmowa dostępu");
    }

    public List<ChartDataRespondDTO> getDistanceByVehicleAndDataAndUseType(String vehicleId, String begin, String end) {
        if (checkExistAndPermissionComponent.accessToVehicle(Long.parseLong(vehicleId))) {
            LocalDate beginDate = LocalDate.parse(begin);
            LocalDate endDate = LocalDate.parse(end);

            Vehicle vehicle = vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
                    () -> new EntityNotFoundException("Bład przetwarzania"));
            List<ChartDataRespondDTO> summary = new ArrayList<>();
            List<DataUseTypeDTO> list = vehicleUseRepository.tripByVehicleAndDataAndTripType(vehicle, beginDate, endDate);

            for (DataUseTypeDTO dataDTO : list) {
                switch (dataDTO.getType()) {
                    case "city":
                        dataDTO.setType("MIEJSKI");
                        break;
                    case "average":
                        dataDTO.setType("MIESZANY");
                        break;
                    case "country":
                        dataDTO.setType("POZAMIEJSKI");
                        break;
                }
                ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                        dataDTO.getCost().floatValue(),
                        dataDTO.getType());
                summary.add(chartData);
            }
            return summary;
        } else throw new PermissionDeniedException("Odmowa dostępu");
    }

    public List<ChartDataRespondDTO> getFuelCostByVehicleAndData(String vehicleId, String begin, String end) {
        if (checkExistAndPermissionComponent.accessToVehicle(Long.parseLong(vehicleId))) {
            LocalDate beginDate = LocalDate.parse(begin);
            LocalDate endDate = LocalDate.parse(end);
            Vehicle vehicle = vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
                    () -> new EntityNotFoundException("Bład przetwarzania"));
            List<ChartDataRespondDTO> summary = new ArrayList<>();
            List<DataRefuelingDTO> list = vehicleRefuelingRepository.refuelingByVehicle(vehicle, beginDate, endDate);
            for (DataRefuelingDTO dataDTO : list) {
                ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                        dataDTO.getValue().floatValue(),
                        dataDTO.getDate().toString());
                summary.add(chartData);
            }
            return summary;
        } else throw new PermissionDeniedException("Odmowa dostępu");
    }

    public List<ChartDataRespondDTO> getDistanceByVehicleAndData(String beginDate, String endDate, String vehicleId) {
        if (checkExistAndPermissionComponent.accessToVehicle(Long.parseLong(vehicleId))) {
            LocalDate begin = LocalDate.parse(beginDate);
            LocalDate end = LocalDate.parse(endDate);

            Vehicle vehicle = vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
                    () -> new EntityNotFoundException("Bład przetwarzania"));
            List<ChartDataRespondDTO> summary = new ArrayList<>();
            List<DataTripDTO> list = vehicleUseRepository.tripByVehicleAndData(vehicle, begin, end);

            for (DataTripDTO dataDTO : list) {
                ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                        dataDTO.getValue().floatValue(),
                        dataDTO.getDate().toString());
                summary.add(chartData);
            }
            return summary;
        } else throw new PermissionDeniedException("Odmowa dostępu");
    }

    public List<ChartDataRespondDTO> getFleetCostByCategory(String begin, String end) {

        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);

        var userId = JWTokenHelper.getJWTUserId();

        List<ChartDataRespondDTO> summary = new ArrayList<>();
        ChartDataRespondDTO chartDataRepair = new ChartDataRespondDTO(
                fleetCostByCategoriesRepair(userId, beginDate, endDate), "Naprawy");
        summary.add(chartDataRepair);
        ChartDataRespondDTO chartDataInsurance = new ChartDataRespondDTO(
                fleetCostByCategoriesInsurance(userId, beginDate, endDate), "Ubezpieczenia");
        summary.add(chartDataInsurance);
        ChartDataRespondDTO chartDataInspection = new ChartDataRespondDTO(
                fleetCostByCategoriesInspection(userId, beginDate, endDate), "Przeglądy");
        summary.add(chartDataInspection);
        ChartDataRespondDTO chartDataRefueling = new ChartDataRespondDTO(
                fleetCostByCategoriesRefueling(userId, beginDate, endDate), "Paliwo");
        summary.add(chartDataRefueling);
        return summary;
    }

    public List<ChartDataRespondDTO> getSummaryRefuelingByVehicle(String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        var userId = JWTokenHelper.getJWTUserId();
        List<DataDTO> list = vehicleRefuelingRepository.sumOfRefuelingByVehicle(userId, beginDate, endDate);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        for (DataDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public List<ChartDataRespondDTO> getSummaryUseByVehicle(String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        var userId = JWTokenHelper.getJWTUserId();
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        List<DataUseDTO> list = vehicleUseRepository.sumOfRefuelingByVehicle(userId, beginDate, endDate);
        for (DataUseDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public List<ChartDataRespondDTO> getNumberOfUsesByVehicle(String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        var userId = JWTokenHelper.getJWTUserId();
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        List<DataTripUserDTO> list = vehicleUseRepository.numberOfUsesByVehicleAndDataAndUser(userId, beginDate, endDate);
        for (DataTripUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getTrip().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public List<ChartDataRespondDTO> getDistanceByVehicleAndDataAndUser(String userId, String begin, String end) {

        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        List<DataTripUserDTO> list = vehicleUseRepository.tripByVehicleAndDataAndUser(Long.parseLong(userId), beginDate, endDate);

        for (DataTripUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getTrip().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public List<ChartDataRespondDTO> getFuelCostByVehicleAndDataAndUser(String userId, String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        List<DataFuelCostUserDTO> list = vehicleRefuelingRepository.fuelCostByVehicleAndUser(Long.parseLong(userId), beginDate, endDate);
        for (DataFuelCostUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public List<ChartDataRespondDTO> getDistanceByVehicleAndDataByLoginUser(String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        var userId = JWTokenHelper.getJWTUserId();
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        List<DataTripUserDTO> list = vehicleUseRepository.tripByVehicleAndDataAndUser(userId, beginDate, endDate);
        for (DataTripUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getTrip().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public List<ChartDataRespondDTO> getFuelCostByVehicleAndDataByLoginUser(String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        var userId = JWTokenHelper.getJWTUserId();
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        List<DataFuelCostUserDTO> list = vehicleRefuelingRepository.fuelCostByVehicleAndUser(userId, beginDate, endDate);
        for (DataFuelCostUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    private Float fleetCostByCategoriesRepair(Long userId, LocalDate begin, LocalDate end) {
        if (Objects.isNull(vehicleRepairRepository.sumOfRepair(userId, begin, end))) {
            return FLOAT_VALUE_OF_ZERO;
        } else return vehicleRepairRepository.sumOfRepair(userId, begin, end);
    }

    private Float fleetCostByCategoriesInsurance(Long userId, LocalDate begin, LocalDate end) {
        if (Objects.isNull(vehicleInsuranceRepository.sumOfInsurance(userId, begin, end))) {
            return FLOAT_VALUE_OF_ZERO;
        } else return vehicleInsuranceRepository.sumOfInsurance(userId, begin, end);

    }

    private Float fleetCostByCategoriesInspection(Long userId, LocalDate begin, LocalDate end) {
        if (Objects.isNull(vehicleInspectionRepository.sumOfInspection(userId, begin, end))) {
            return FLOAT_VALUE_OF_ZERO;
        } else return vehicleInspectionRepository.sumOfInspection(userId, begin, end);
    }

    private Float fleetCostByCategoriesRefueling(Long userId, LocalDate begin, LocalDate end) {
        if (Objects.isNull(vehicleRefuelingRepository.sumOfRefueling(userId, begin, end))) {
            return FLOAT_VALUE_OF_ZERO;
        } else return vehicleRefuelingRepository.sumOfRefueling(userId, begin, end);
    }

    private Float vehicleCostByCategoriesRepair(Vehicle vehicle, LocalDate begin, LocalDate end) {
        if (Objects.isNull(vehicleRepairRepository.vehicleSumCostOfRepair(vehicle, begin, end))) {
            return FLOAT_VALUE_OF_ZERO;
        } else return vehicleRepairRepository.vehicleSumCostOfRepair(vehicle, begin, end);
    }

    private Float vehicleCostByCategoriesInsurance(Vehicle vehicle, LocalDate begin, LocalDate end) {
        if (Objects.isNull(vehicleInsuranceRepository.vehicleSumCostOfInsurance(vehicle, begin, end))) {
            return FLOAT_VALUE_OF_ZERO;
        } else return vehicleInsuranceRepository.vehicleSumCostOfInsurance(vehicle, begin, end);

    }

    private Float vehicleCostByCategoriesInspection(Vehicle vehicle, LocalDate begin, LocalDate end) {
        if (Objects.isNull(vehicleInspectionRepository.vehicleSumCostOfInspection(vehicle, begin, end))) {
            return FLOAT_VALUE_OF_ZERO;
        } else return vehicleInspectionRepository.vehicleSumCostOfInspection(vehicle, begin, end);
    }

    private Float vehicleCostByCategoriesRefueling(Vehicle vehicle, LocalDate begin, LocalDate end) {
        if (Objects.isNull(vehicleRefuelingRepository.vehicleSumCostOfRefueling(vehicle, begin, end))) {
            return FLOAT_VALUE_OF_ZERO;
        } else return vehicleRefuelingRepository.vehicleSumCostOfRefueling(vehicle, begin, end);
    }

}
