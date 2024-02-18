package pl.nankiewic.fleetappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.entity.*;
import pl.nankiewic.fleetappbackend.exceptions.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.reports.*;
import pl.nankiewic.fleetappbackend.repository.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ReportsService {
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleUseRepository vehicleUseRepository;
    private final VehicleRepairRepository vehicleRepairRepository;
    private final VehicleInsuranceRepository vehicleInsuranceRepository;
    private final VehicleInspectionRepository vehicleInspectionRepository;
    private final VehicleRefuelingRepository vehicleRefuelingRepository;
    private final CheckExistAndPermissionComponent checkExistAndPermissionComponent;

    public void exportReportByUserToPDF(String resourceId,
                                        String beginDate,
                                        String endDate,
                                        String reportType,
                                        HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vehicle_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        response.setHeader("Pragma", "public");

        Long id = Long.valueOf(resourceId);
        LocalDate begin = LocalDate.parse(beginDate);
        LocalDate end = LocalDate.parse(endDate);

        switch (reportType) {
            case "refueling": {
                List<VehicleRefueling> list = (List<VehicleRefueling>) getRefuelingByUserAndDate(id, begin, end);
                RefuelingPDFExporter exporter = new RefuelingPDFExporter(list, getVehicleInfo((long) 2));
                exporter.export(response);
                break;
            }
            case "use": {
                List<VehicleUse> list = (List<VehicleUse>) getUseByUserAndDate(id, begin, end);
                UsePDFExporter exporter = new UsePDFExporter(list, getVehicleInfo((long) 2));
                exporter.export(response);
                break;
            }
        }
    }

    public void exportReportByVehicleToPDF(String resourceId,
                                           String beginDate,
                                           String endDate,
                                           String reportType,
                                           HttpServletResponse response,
                                           Authentication authentication) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vehicle_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        Long id = Long.valueOf(resourceId);
        if (!checkExistAndPermissionComponent.accessToVehicle(id)) {
            throw new PermissionDeniedException("Odmowa dostępu");
        }
        LocalDate begin = LocalDate.parse(beginDate);
        LocalDate end = LocalDate.parse(endDate);
        switch (reportType) {
            case "refueling": {
                List<VehicleRefueling> list = (List<VehicleRefueling>) getRefuelingByVehicleAndDate(id, begin, end);
                RefuelingPDFExporter exporter = new RefuelingPDFExporter(list, getVehicleInfo(id));
                exporter.export(response);
                break;
            }
            case "use": {
                List<VehicleUse> list = (List<VehicleUse>) getUseByVehicleAndDate(id, begin, end);
                UsePDFExporter exporter = new UsePDFExporter(list, getVehicleInfo(id));
                exporter.export(response);
                break;
            }
            case "repair": {
                List<VehicleRepair> list = (List<VehicleRepair>) getRepairByVehicleAndDate(id, begin, end);
                RepairPDFExporter exporter = new RepairPDFExporter(list, getVehicleInfo(id));
                exporter.export(response);
                break;
            }
            case "insurance": {
                List<VehicleInsurance> list = (List<VehicleInsurance>) getInsuranceByVehicleAndDate(id, begin, end);
                InsurancePDFExporter exporter = new InsurancePDFExporter(list, getVehicleInfo(id));
                exporter.export(response);
                break;
            }
            case "inspection": {
                List<VehicleInspection> list = (List<VehicleInspection>) getInspectionByVehicleAndDate(id, begin, end);
                InspectionPDFExporter exporter = new InspectionPDFExporter(list, getVehicleInfo(id));
                exporter.export(response);
                break;
            }
        }

    }

    private Iterable<VehicleRefueling> getRefuelingByVehicleAndDate(Long id, LocalDate begin, LocalDate end) {
        if (vehicleRepository.existsById(id)) {
            Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bład przetwarzania"));
            return vehicleRefuelingRepository.findAllByVehicleIsAndRefuelingDateIsBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("not found");
    }

    private Iterable<VehicleInsurance> getInsuranceByVehicleAndDate(Long id, LocalDate begin, LocalDate end) {
        if (vehicleRepository.existsById(id)) {
            Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bład przetwarzania"));
            return vehicleInsuranceRepository.findAllByVehicleAndEffectiveDateBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("not found");
    }

    private Iterable<VehicleInspection> getInspectionByVehicleAndDate(Long id, LocalDate begin, LocalDate end) {
        if (vehicleRepository.existsById(id)) {
            Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bład przetwarzania"));
            return vehicleInspectionRepository.findAllByVehicleAndInspectionDateBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("not found");
    }

    private Iterable<VehicleRepair> getRepairByVehicleAndDate(Long id, LocalDate begin, LocalDate end) {
        if (vehicleRepository.existsById(id)) {
            Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bład przetwarzania"));
            return vehicleRepairRepository.findAllByVehicleAndRepairDateBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("not found");
    }

    private Iterable<VehicleUse> getUseByVehicleAndDate(Long id, LocalDate begin, LocalDate end) {
        if (vehicleRepository.existsById(id)) {
            Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bład przetwarzania"));
            return vehicleUseRepository.findAllByVehicleIsAndTripDateBetween(vehicle, begin, end);
        } else throw new EntityNotFoundException("not found");
    }

    private Iterable<VehicleUse> getUseByUserAndDate(Long id, LocalDate begin, LocalDate end) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono użytkownika"));
        return vehicleUseRepository.findAllByUserIsAndTripDateBetween(user, begin, end);
    }

    private Iterable<VehicleRefueling> getRefuelingByUserAndDate(Long id, LocalDate begin, LocalDate end) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Nie znaleziono użytkownika"));
        return vehicleRefuelingRepository.findAllByUserIsAndRefuelingDateIsBetween(user, begin, end);
    }

    private Iterable<Vehicle> getVehicleByUser(String email) {
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findUserByEmail(email).orElseThrow();
            return vehicleRepository.findVehiclesByUser(user);
        } else throw new EntityNotFoundException("not found");
    }

    private Vehicle getVehicleInfo(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bład przetwarzania"));
    }
}
