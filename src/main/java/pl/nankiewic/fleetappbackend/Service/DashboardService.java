package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.*;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Repository.*;

import java.sql.Date;

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

    /*
    by vehicle
    */
    public Float vehicleCostByCategoriesRepair(Vehicle vehicle, Date begin, Date end){
        if(vehicleRepairRepository.vehicleSumCostOfRepair(vehicle, begin, end)==null)
        {
            return Float.valueOf("0");
        } else return vehicleRepairRepository.vehicleSumCostOfRepair(vehicle, begin, end);
    }
    public Float vehicleCostByCategoriesInsurance(Vehicle vehicle, Date begin, Date end){
        if(vehicleInsuranceRepository.vehicleSumCostOfInsurance(vehicle, begin, end)==null)
        {
            return Float.valueOf("0");
        } else  return vehicleInsuranceRepository.vehicleSumCostOfInsurance(vehicle, begin, end);

    }
    public Float vehicleCostByCategoriesInspection(Vehicle vehicle, Date begin, Date end){
        if(vehicleInspectionRepository.vehicleSumCostOfInspection(vehicle, begin, end)==null)
        {
            return Float.valueOf("0");
        } else  return vehicleInspectionRepository.vehicleSumCostOfInspection(vehicle, begin, end);
    }
    public Float vehicleCostByCategoriesRefueling(Vehicle vehicle, Date begin, Date end){
        if(vehicleRefuelingRepository.vehicleSumCostOfRefueling(vehicle, begin, end)==null)
        {
            return Float.valueOf("0");
        } else  return vehicleRefuelingRepository.vehicleSumCostOfRefueling(vehicle, begin, end);
    }
    public Iterable<ChartDataRespondDTO> vehicleCostByCategory(String vehicleId, Date begin, Date end){
        Vehicle vehicle=vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania"));
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        ChartDataRespondDTO chartDataRepair=new ChartDataRespondDTO(
                vehicleCostByCategoriesRepair(vehicle, begin, end), "Naprawy");
        summary.add(chartDataRepair);
        ChartDataRespondDTO chartDataInsurance=new ChartDataRespondDTO(
                vehicleCostByCategoriesInsurance(vehicle, begin, end), "Ubezpieczenia");
        summary.add(chartDataInsurance);
        ChartDataRespondDTO chartDataInspection=new ChartDataRespondDTO(
                vehicleCostByCategoriesInspection(vehicle, begin, end), "Przeglądy");
        summary.add(chartDataInspection);
        ChartDataRespondDTO chartDataRefueling=new ChartDataRespondDTO(
                vehicleCostByCategoriesRefueling(vehicle, begin, end), "Paliwo");
        summary.add(chartDataRefueling);
        return summary;
    }
    public Iterable<ChartDataRespondDTO> distanceByVehicleAndDataAndUseType(Date begin, Date end, String vehicleId) {
        Vehicle vehicle=vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania"));
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataUseTypeDTO> list = vehicleUseRepository.tripByVehicleAndDataAndTripType(vehicle, begin, end);

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
            ChartDataRespondDTO chartData=new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue(),
                    dataDTO.getType());
            summary.add(chartData);
        }
        return summary;
    }
    public Iterable<ChartDataRespondDTO> fuelCostByVehicleAndData(Date begin, Date end, String vehicleId) {
        Vehicle vehicle=vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania"));
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataRefuelingDTO> list = vehicleRefuelingRepository.refuelingByVehicle(vehicle, begin, end);
        for (DataRefuelingDTO dataDTO : list) {
            ChartDataRespondDTO chartData=new ChartDataRespondDTO(
                    dataDTO.getValue().floatValue(),
                    dataDTO.getDate().toString());
            summary.add(chartData);
        }
        return summary;

    }
    public Iterable<ChartDataRespondDTO> distanceByVehicleAndData(Date begin, Date end, String vehicleId) {
        Vehicle vehicle=vehicleRepository.findById(Long.parseLong(vehicleId)).orElseThrow(
                () -> new RuntimeException("Bład przetwarzania"));
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataTripDTO> list = vehicleUseRepository.tripByVehicleAndData(vehicle, begin, end);

        for (DataTripDTO dataDTO : list) {
            ChartDataRespondDTO chartData=new ChartDataRespondDTO(
                    dataDTO.getValue().floatValue(),
                    dataDTO.getDate().toString());
            summary.add(chartData);
        }
        return summary;
    }
    /*
    by fleet
     */
    public Float fleetCostByCategoriesRepair(User user, Date begin, Date end){
        if(vehicleRepairRepository.sumOfRepair(user, begin, end)==null)
        {
            return Float.valueOf("0");
        } else return vehicleRepairRepository.sumOfRepair(user, begin, end);
    }
    public Float fleetCostByCategoriesInsurance(User user, Date begin, Date end){
        if(vehicleInsuranceRepository.sumOfInsurance(user, begin, end)==null)
        {
            return Float.valueOf("0");
        } else  return vehicleInsuranceRepository.sumOfInsurance(user, begin, end);

    }
    public Float fleetCostByCategoriesInspection(User user, Date begin, Date end){
        if(vehicleInspectionRepository.sumOfInspection(user, begin, end)==null)
        {
            return Float.valueOf("0");
        } else  return vehicleInspectionRepository.sumOfInspection(user, begin, end);
    }
    public Float fleetCostByCategoriesRefueling(User user, Date begin, Date end){
        if(vehicleRefuelingRepository.sumOfRefueling(user, begin, end)==null)
        {
            return Float.valueOf("0");
        } else  return vehicleRefuelingRepository.sumOfRefueling(user, begin, end);
    }

    public Iterable<ChartDataRespondDTO> fleetCostByCategory(String username, Date begin, Date end){
        User user =userRepository.findUserByEmail(username);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        ChartDataRespondDTO chartDataRepair=new ChartDataRespondDTO(
                fleetCostByCategoriesRepair(user, begin, end), "Naprawy");
        summary.add(chartDataRepair);
        ChartDataRespondDTO chartDataInsurance=new ChartDataRespondDTO(
                fleetCostByCategoriesInsurance(user, begin, end), "Ubezpieczenia");
        summary.add(chartDataInsurance);
        ChartDataRespondDTO chartDataInspection=new ChartDataRespondDTO(
                fleetCostByCategoriesInspection(user, begin, end), "Przeglądy");
        summary.add(chartDataInspection);
        ChartDataRespondDTO chartDataRefueling=new ChartDataRespondDTO(
                fleetCostByCategoriesRefueling(user, begin, end), "Paliwo");
        summary.add(chartDataRefueling);
        return summary;
    }
    public Iterable<ChartDataRespondDTO> sumRefuelingByVehicle(String username, Date begin, Date end)
    {
        User user =userRepository.findUserByEmail(username);
        Iterable<DataDTO> list = vehicleRefuelingRepository.sumOfRefuelingByVehicle(user, begin, end);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        for (DataDTO dataDTO : list) {
            ChartDataRespondDTO chartData=new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue() ,
                    dataDTO.getVehicle().getModel()+" | "+dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }
    public Iterable <ChartDataRespondDTO> sumUseByVehicle(String username, Date begin, Date end){
        User user =userRepository.findUserByEmail(username);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataUseDTO> list = vehicleUseRepository.sumOfRefuelingByVehicle(user, begin, end);
        for (DataUseDTO dataDTO : list) {
            ChartDataRespondDTO chartData=new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue() ,
                    dataDTO.getVehicle().getModel()+" | "+dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }
    public Iterable<ChartDataRespondDTO> numberOfUsesByVehicle(String username, Date begin, Date end) {
        User user =userRepository.findUserByEmail(username);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataTripUserDTO> list = vehicleUseRepository.numberOfUsesByVehicleAndDataAndUser(user, begin, end);
        for (DataTripUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData=new ChartDataRespondDTO(
                    dataDTO.getTrip().floatValue(),
                    dataDTO.getVehicle().getModel()+" | "+dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public Iterable<ChartDataRespondDTO> distanceByVehicleAndDataAndUser(Date begin, Date end, String userId) {
        User user=userRepository.findUserById(Long.parseLong(userId));
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataTripUserDTO> list=vehicleUseRepository.tripByVehicleAndDataAndUser(user, begin, end);

        for (DataTripUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData=new ChartDataRespondDTO(
                    dataDTO.getTrip().floatValue(),
                    dataDTO.getVehicle().getModel()+" | "+dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public Iterable<ChartDataRespondDTO> fuelCostByVehicleAndDataAndUser(Date begin, Date end, String userId) {
        User user=userRepository.findUserById(Long.parseLong(userId));
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataFuelCostUserDTO> list=vehicleRefuelingRepository.fuelCostByVehicleAndUser(user, begin, end);
        for (DataFuelCostUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData=new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue(),
                    dataDTO.getVehicle().getModel()+" | "+dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public Iterable<ChartDataRespondDTO> distanceByVehicleAndDataByLoginUser(String username, Date begin, Date end) {
        User user=userRepository.findUserByEmail(username);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataTripUserDTO> list=vehicleUseRepository.tripByVehicleAndDataAndUser(user, begin, end);
        for (DataTripUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData=new ChartDataRespondDTO(
                    dataDTO.getTrip().floatValue(),
                    dataDTO.getVehicle().getModel()+" | "+dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }

    public Iterable<ChartDataRespondDTO> fuelCostByVehicleAndDataByLoginUser(String username, Date begin, Date end) {
        User user=userRepository.findUserByEmail(username);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        Iterable<DataFuelCostUserDTO> list=vehicleRefuelingRepository.fuelCostByVehicleAndUser(user, begin, end);
        for (DataFuelCostUserDTO dataDTO : list) {
            ChartDataRespondDTO chartData=new ChartDataRespondDTO(
                    dataDTO.getCost().floatValue(),
                    dataDTO.getVehicle().getModel()+" | "+dataDTO.getVehicle().getVehicleRegistrationNumber());
            summary.add(chartData);
        }
        return summary;
    }


}
