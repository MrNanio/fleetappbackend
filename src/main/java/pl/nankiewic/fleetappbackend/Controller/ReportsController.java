package pl.nankiewic.fleetappbackend.Controller;

import com.lowagie.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.Entity.*;
import pl.nankiewic.fleetappbackend.Reports.*;
import pl.nankiewic.fleetappbackend.Service.ReportsService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@RestController@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/reports")
public class ReportsController {
    private ReportsService reportsService;
    @Autowired
    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @GetMapping("/vehicle")
    public void exportVehicleReportToPDF(Authentication authentication, HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vehicle_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<Vehicle> list = (List<Vehicle>) reportsService.vehicleByUser(userDetails.getUsername());
        VehiclePDFExporter exporter = new VehiclePDFExporter(list);
        exporter.export(response);
    }
    @GetMapping("/gen")
    public void exportReportToPDF(
            @RequestParam (name = "report") String report,
            @RequestParam (name = "value") String idS,
            @RequestParam (name = "begin") String beginS,
            @RequestParam (name = "end") String endS,
            HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vehicle_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        Long id=Long.valueOf(idS);
        LocalDate begin= LocalDate.parse(beginS);
        LocalDate end= LocalDate.parse(endS);
        switch (report) {
            case "refueling": {
                List<Refueling> list = (List<Refueling>) reportsService.refuelingByVehicle(id, begin, end);
                RefuelingPDFExporter exporter = new RefuelingPDFExporter(list, reportsService.getVehicleInfo(id));
                exporter.export(response);
                break;
            }
            case "use": {
                List<VehicleUse> list = (List<VehicleUse>) reportsService.useByVehicle(id, begin, end);
                UsePDFExporter exporter = new UsePDFExporter(list, reportsService.getVehicleInfo(id));
                exporter.export(response);
                break;
            }
            case "repairs": {
                List<VehicleRepair> list = (List<VehicleRepair>) reportsService.repairByVehicle(id, begin, end);
                RepairPDFExporter exporter = new RepairPDFExporter(list, reportsService.getVehicleInfo(id));
                exporter.export(response);
                break;
            }
            case "insurance": {
                List<VehicleInsurance> list = (List<VehicleInsurance>) reportsService.insuranceByVehicle(id, begin, end);
                InsurancePDFExporter exporter = new InsurancePDFExporter(list, reportsService.getVehicleInfo(id));
                exporter.export(response);
                break;
            }
            case "inspection": {
                List<VehicleInspection> list = (List<VehicleInspection>) reportsService.inspectionByVehicle(id, begin, end);
                InspectionPDFExporter exporter = new InspectionPDFExporter(list, reportsService.getVehicleInfo(id));
                exporter.export(response);
                break;
            }
            case "useByUser": {
                List<VehicleUse> list = (List<VehicleUse>) reportsService.useByUser(id, begin, end);
                UsePDFExporter exporter = new UsePDFExporter(list, reportsService.getVehicleInfo(id));
                exporter.export(response);
                break;
            }
        }
    }
}
