package pl.nankiewic.fleetappbackend.Controller;

import com.lowagie.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nankiewic.fleetappbackend.Entity.User;
import pl.nankiewic.fleetappbackend.Reports.UserPDFExporter;
import pl.nankiewic.fleetappbackend.Service.CustomUserDetailsService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("reports")
public class ReportsController {
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    public ReportsController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/user")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<User> listUsers = customUserDetailsService.listAll();
        UserPDFExporter exporter = new UserPDFExporter(listUsers);
        exporter.export(response);
    }
    @GetMapping("/vehicle")
    public void exportVehicleReportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vehicle_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<User> listUsers = customUserDetailsService.listAll();
        UserPDFExporter exporter = new UserPDFExporter(listUsers);
        exporter.export(response);
    }
}
