package pl.nankiewic.fleetappbackend.service;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.report.ReportType;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.exporter.AbstractPDFExporter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class ReportsService {

    private final List<AbstractPDFExporter> pdfExporters;

    public ReportsService(final List<AbstractPDFExporter> exporters) {
        this.pdfExporters = exporters;
    }

    public void exportPDFReport(ReportViewFilterParam param,
                                HttpServletResponse response) throws DocumentException, IOException {
        getPDFExporter(param.getType())
                .export(response, param);
    }

    private AbstractPDFExporter getPDFExporter(ReportType type) {
        return pdfExporters.stream()
                .filter(v -> v.supportType() == type)
                .findFirst()
                .orElseThrow();
    }

}