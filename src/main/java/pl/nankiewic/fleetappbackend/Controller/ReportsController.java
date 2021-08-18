package pl.nankiewic.fleetappbackend.Controller;

import com.lowagie.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.Entity.*;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Reports.*;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.ReportsService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/reports")
public class ReportsController {
    private final ReportsService reportsService;
    private final CheckService checkService;
    @Autowired
    public ReportsController(ReportsService reportsService, CheckService checkService) {
        this.reportsService = reportsService;
        this.checkService = checkService;
    }

    @GetMapping("/by_user")
    public void exportVehicleReportToPDF( @RequestParam (name = "r") String report,
                                          @RequestParam (name = "u") String idS,
                                          @RequestParam (name = "b") String beginS,
                                          @RequestParam (name = "e") String endS,
                                          HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vehicle_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        response.setHeader("Pragma", "public");
        Long id=Long.valueOf(idS);
        LocalDate begin = LocalDate.parse(beginS);
        LocalDate end = LocalDate.parse(endS);
        switch (report) {
            case "refueling": {
                List<VehicleRefueling> list = (List<VehicleRefueling>) reportsService.refuelingByUser(id, begin, end);
                RefuelingPDFExporter exporter = new RefuelingPDFExporter(list, reportsService.getVehicleInfo((long) 2));
                exporter.export(response);
                break;
            }
            case "use": {
                List<VehicleUse> list = (List<VehicleUse>) reportsService.useByUser(id, begin, end);
                UsePDFExporter exporter = new UsePDFExporter(list, reportsService.getVehicleInfo((long) 2));
                exporter.export(response);
                break;
            }
        }
    }
    /*
    http://localhost:8080/reports/by_user?r=use&u=2&b=2020-08-01&e=2020-12-22
    http://localhost:8080/reports/by_vehicle?r=use&v=2&b=2020-08-01&e=2020-12-22
     */
    @GetMapping(value = "/by_vehicle", produces = MediaType.APPLICATION_PDF_VALUE)
    public void exportReportToPDF(
            @RequestParam(name = "r") String report,
            @RequestParam(name = "v") String idS,
            @RequestParam(name = "b") String beginS,
            @RequestParam(name = "e") String endS,
            HttpServletResponse response, Authentication authentication) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vehicle_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        Long id = Long.valueOf(idS);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!checkService.accessToVehicle(userDetails.getUsername(), id)) {
            throw new PermissionDeniedException("Odmowa dostępu");
        }
        LocalDate begin = LocalDate.parse(beginS);
        LocalDate end = LocalDate.parse(endS);
        switch (report) {
            case "refueling": {
                List<VehicleRefueling> list = (List<VehicleRefueling>) reportsService.refuelingByVehicle(id, begin, end);
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
            case "repair": {
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
        }
    }
}
