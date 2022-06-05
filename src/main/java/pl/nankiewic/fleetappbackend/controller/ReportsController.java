package pl.nankiewic.fleetappbackend.controller;

import com.lowagie.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.service.ReportsService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/reports")
public class ReportsController {

    private final ReportsService reportsService;

    @GetMapping("/by_user")
    public void exportVehicleReportToPDF(@RequestParam(name = "r") String report,
                                         @RequestParam(name = "u") String idS,
                                         @RequestParam(name = "b") String beginS,
                                         @RequestParam(name = "e") String endS,
                                         HttpServletResponse response) throws DocumentException, IOException {

        reportsService.exportReportByUserToPDF(idS, beginS, endS, report, response);
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

        reportsService.exportReportByVehicleToPDF(idS, beginS, endS, report, response, authentication);
    }
}
