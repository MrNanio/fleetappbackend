package pl.nankiewic.fleetappbackend.controller;

import com.itextpdf.text.DocumentException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.service.ReportsService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/report")
public class ReportController {

    private final ReportsService reportsService;

    public ReportController(final ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @GetMapping
    public void exportVehicleReportToPDF(ReportViewFilterParam param,
                                         HttpServletResponse response) throws DocumentException, IOException {
        reportsService.exportPDFReport(param, response);
    }

}