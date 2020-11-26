package pl.nankiewic.fleetappbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.DTO.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.DTO.DataDTO;
import pl.nankiewic.fleetappbackend.DTO.DataUseDTO;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Repository.*;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;
@Service
public class DashboardService {
    VehicleInspectionRepository vehicleInspectionRepository;
    VehicleInsuranceRepository vehicleInsuranceRepository;
    VehicleRefuelingRepository vehicleRefuelingRepository;
    VehicleRepairRepository vehicleRepairRepository;
    VehicleUseRepository vehicleUseRepository;
    UserRepository userRepository;
    @Autowired
    public DashboardService(VehicleInspectionRepository vehicleInspectionRepository,
                            VehicleInsuranceRepository vehicleInsuranceRepository,
                            VehicleRefuelingRepository vehicleRefuelingRepository,
                            VehicleRepairRepository vehicleRepairRepository,
                            VehicleUseRepository vehicleUseRepository, UserRepository userRepository) {
        this.vehicleInspectionRepository = vehicleInspectionRepository;
        this.vehicleInsuranceRepository = vehicleInsuranceRepository;
        this.vehicleRefuelingRepository = vehicleRefuelingRepository;
        this.vehicleRepairRepository = vehicleRepairRepository;
        this.vehicleUseRepository = vehicleUseRepository;
        this.userRepository = userRepository;
    }

    public Float costByCategoriesRepair(User user, Date begin, Date end){
        if(vehicleRepairRepository.sumOfRepair(user, begin, end)==null)
        {
            return Float.valueOf("0");
        } else return vehicleRepairRepository.sumOfRepair(user, begin, end);
    }
    public Float costByCategoriesInsurance(User user, Date begin, Date end){
        if(vehicleInsuranceRepository.sumOfInsurance(user, begin, end)==null)
        {
            return Float.valueOf("0");
        } else  return vehicleInsuranceRepository.sumOfInsurance(user, begin, end);

    }
    public Float costByCategoriesInspection(User user, Date begin, Date end){
        if(vehicleInspectionRepository.sumOfInspection(user, begin, end)==null)
        {
            return Float.valueOf("0");
        } else  return vehicleInspectionRepository.sumOfInspection(user, begin, end);
    }
    public Float costByCategoriesRefueling(User user, Date begin, Date end){
        if(vehicleRefuelingRepository.sumOfRefueling(user, begin, end)==null)
        {
            return Float.valueOf("0");
        } else  return vehicleRefuelingRepository.sumOfRefueling(user, begin, end);
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
    public Iterable<ChartDataRespondDTO> sumCategory(String username, Date begin, Date end){
        User user =userRepository.findUserByEmail(username);
        List<ChartDataRespondDTO> summary = new ArrayList<>();
        ChartDataRespondDTO chartDataRepair=new ChartDataRespondDTO(
                costByCategoriesRepair(user, begin, end), "Naprawy");
        summary.add(chartDataRepair);
        ChartDataRespondDTO chartDataInsurance=new ChartDataRespondDTO(
                costByCategoriesInsurance(user, begin, end), "Ubezpieczenia");
        summary.add(chartDataInsurance);
        ChartDataRespondDTO chartDataInspection=new ChartDataRespondDTO(
                costByCategoriesInspection(user, begin, end), "Przeglądy");
        summary.add(chartDataInspection);
        ChartDataRespondDTO chartDataRefueling=new ChartDataRespondDTO(
                costByCategoriesRefueling(user, begin, end), "Paliwo");
        summary.add(chartDataRefueling);
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
}
