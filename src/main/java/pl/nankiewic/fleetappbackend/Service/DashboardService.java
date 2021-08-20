package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.*;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Repository.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {
    private final VehicleInspectionRepository vehicleInspectionRepository;
    private final VehicleInsuranceRepository vehicleInsuranceRepository;
    private final VehicleRefuelingRepository vehicleRefuelingRepository;
    private final VehicleRepairRepository vehicleRepairRepository;
    private final VehicleUseRepository vehicleUseRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Autowired
    public DashboardService(VehicleInspectionRepository vehicleInspectionRepository,
                            VehicleInsuranceRepository vehicleInsuranceRepository,
                            VehicleRefuelingRepository vehicleRefuelingRepository,
                            VehicleRepairRepository vehicleRepairRepository,
                            VehicleUseRepository vehicleUseRepository,
                            VehicleRepository vehicleRepository,
                            UserRepository userRepository) {
        this.vehicleInspectionRepository = vehicleInspectionRepository;
        this.vehicleInsuranceRepository = vehicleInsuranceRepository;
        this.vehicleRefuelingRepository = vehicleRefuelingRepository;
        this.vehicleRepairRepository = vehicleRepairRepository;
        this.vehicleUseRepository = vehicleUseRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }


    public Iterable<ChartDataRespondDTO> getVehicleCostByCategory(String vehicleId, String begin, String end) {

        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);

        Vehicle vehicle = vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania"));
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
    }

    public Iterable<ChartDataRespondDTO> getDistanceByVehicleAndDataAndUseType(String vehicleId, String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);

        Vehicle vehicle = vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania"));
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataUseTypeDTO> list = vehicleUseRepository.tripByVehicleAndDataAndTripType(vehicle, beginDate, endDate);

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
    }

    public Iterable<ChartDataRespondDTO> getFuelCostByVehicleAndData(String vehicleId, String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        Vehicle vehicle = vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania"));
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataRefuelingDTO> list = vehicleRefuelingRepository.refuelingByVehicle(vehicle, beginDate, endDate);
        for (DataRefuelingDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getValue().floatValue(),
                    dataDTO.getDate().toString());
            summary.add(chartData);
        }
        return summary;

    }

    public Iterable<ChartDataRespondDTO> getDistanceByVehicleAndData(String beginDate, String endDate, String vehicleId) {

        LocalDate begin = LocalDate.parse(beginDate);
        LocalDate end = LocalDate.parse(endDate);

        Vehicle vehicle = vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania"));
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataTripDTO> list = vehicleUseRepository.tripByVehicleAndData(vehicle, begin, end);

        for (DataTripDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getValue().floatValue(),
                    dataDTO.getDate().toString());
            summary.add(chartData);
        }
        return summary;
    }

    public Iterable<ChartDataRespondDTO> getFleetCostByCategory(String username, String begin, String end) {

        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);

        User user = userRepository.findUserByEmail(username);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        ChartDataRespondDTO chartDataRepair = new ChartDataRespondDTO(
                fleetCostByCategoriesRepair(user, beginDate, endDate), "Naprawy");
        summary.add(chartDataRepair);
        ChartDataRespondDTO chartDataInsurance = new ChartDataRespondDTO(
                fleetCostByCategoriesInsurance(user, beginDate, endDate), "Ubezpieczenia");
        summary.add(chartDataInsurance);
        ChartDataRespondDTO chartDataInspection = new ChartDataRespondDTO(
                fleetCostByCategoriesInspection(user, beginDate, endDate), "Przeglądy");
        summary.add(chartDataInspection);
        ChartDataRespondDTO chartDataRefueling = new ChartDataRespondDTO(
                fleetCostByCategoriesRefueling(user, beginDate, endDate), "Paliwo");
        summary.add(chartDataRefueling);
        return summary;
    }

    public Iterable<ChartDataRespondDTO> getSummaryRefuelingByVehicle(String username, String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        User user = userRepository.findUserByEmail(username);
        Iterable<DataDTO> list = vehicleRefuelingRepository.sumOfRefuelingByVehicle(user, beginDate, endDate);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        for (DataDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public Iterable<ChartDataRespondDTO> getSummaryUseByVehicle(String username, String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        User user = userRepository.findUserByEmail(username);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataUseDTO> list = vehicleUseRepository.sumOfRefuelingByVehicle(user, beginDate, endDate);
        for (DataUseDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public Iterable<ChartDataRespondDTO> getNumberOfUsesByVehicle(String username, String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        User user = userRepository.findUserByEmail(username);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataTripUserDTO> list = vehicleUseRepository.numberOfUsesByVehicleAndDataAndUser(user, beginDate, endDate);
        for (DataTripUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getTrip().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public Iterable<ChartDataRespondDTO> getDistanceByVehicleAndDataAndUser(String userId, String begin, String end) {

        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono użytkownika"));
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataTripUserDTO> list = vehicleUseRepository.tripByVehicleAndDataAndUser(user, beginDate, endDate);

        for (DataTripUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getTrip().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public Iterable<ChartDataRespondDTO> getFuelCostByVehicleAndDataAndUser(String userId, String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono użytkownika"));
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataFuelCostUserDTO> list = vehicleRefuelingRepository.fuelCostByVehicleAndUser(user, beginDate, endDate);
        for (DataFuelCostUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public Iterable<ChartDataRespondDTO> getDistanceByVehicleAndDataByLoginUser(String username, String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        User user = userRepository.findUserByEmail(username);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataTripUserDTO> list = vehicleUseRepository.tripByVehicleAndDataAndUser(user, beginDate, endDate);
        for (DataTripUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getTrip().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public Iterable<ChartDataRespondDTO> getFuelCostByVehicleAndDataByLoginUser(String username, String begin, String end) {
        LocalDate beginDate = LocalDate.parse(begin);
        LocalDate endDate = LocalDate.parse(end);
        User user = userRepository.findUserByEmail(username);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataFuelCostUserDTO> list = vehicleRefuelingRepository.fuelCostByVehicleAndUser(user, beginDate, endDate);
        for (DataFuelCostUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData = new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue(),
                    dataDTO.getVehicle().getModel() + " | " + dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    private Float fleetCostByCategoriesRepair(User user, LocalDate begin, LocalDate end) {
        if (vehicleRepairRepository.sumOfRepair(user, begin, end) == null) {
            return Float.valueOf("0");
        } else return vehicleRepairRepository.sumOfRepair(user, begin, end);
    }

    private Float fleetCostByCategoriesInsurance(User user, LocalDate begin, LocalDate end) {
        if (vehicleInsuranceRepository.sumOfInsurance(user, begin, end) == null) {
            return Float.valueOf("0");
        } else return vehicleInsuranceRepository.sumOfInsurance(user, begin, end);

    }

    private Float fleetCostByCategoriesInspection(User user, LocalDate begin, LocalDate end) {
        if (vehicleInspectionRepository.sumOfInspection(user, begin, end) == null) {
            return Float.valueOf("0");
        } else return vehicleInspectionRepository.sumOfInspection(user, begin, end);
    }

    private Float fleetCostByCategoriesRefueling(User user, LocalDate begin, LocalDate end) {
        if (vehicleRefuelingRepository.sumOfRefueling(user, begin, end) == null) {
            return Float.valueOf("0");
        } else return vehicleRefuelingRepository.sumOfRefueling(user, begin, end);
    }

    private Float vehicleCostByCategoriesRepair(Vehicle vehicle, LocalDate begin, LocalDate end) {
        if (vehicleRepairRepository.vehicleSumCostOfRepair(vehicle, begin, end) == null) {
            return Float.valueOf("0");
        } else return vehicleRepairRepository.vehicleSumCostOfRepair(vehicle, begin, end);
    }

    private Float vehicleCostByCategoriesInsurance(Vehicle vehicle, LocalDate begin, LocalDate end) {
        if (vehicleInsuranceRepository.vehicleSumCostOfInsurance(vehicle, begin, end) == null) {
            return Float.valueOf("0");
        } else return vehicleInsuranceRepository.vehicleSumCostOfInsurance(vehicle, begin, end);

    }

    private Float vehicleCostByCategoriesInspection(Vehicle vehicle, LocalDate begin, LocalDate end) {
        if (vehicleInspectionRepository.vehicleSumCostOfInspection(vehicle, begin, end) == null) {
            return Float.valueOf("0");
        } else return vehicleInspectionRepository.vehicleSumCostOfInspection(vehicle, begin, end);
    }

    private Float vehicleCostByCategoriesRefueling(Vehicle vehicle, LocalDate begin, LocalDate end) {
        if (vehicleRefuelingRepository.vehicleSumCostOfRefueling(vehicle, begin, end) == null) {
            return Float.valueOf("0");
        } else return vehicleRefuelingRepository.vehicleSumCostOfRefueling(vehicle, begin, end);
    }

}
