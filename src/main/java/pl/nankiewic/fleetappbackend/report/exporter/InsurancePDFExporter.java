package pl.nankiewic.fleetappbackend.report.exporter;

import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.report.ReportType;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.provider.InsuranceReportDataProvider;

import java.util.List;

@Service
public class InsurancePDFExporter extends AbstractPDFExporter {

    private final InsuranceReportDataProvider insuranceReportDataProvider;

    public InsurancePDFExporter(final InsuranceReportDataProvider insuranceReportDataProvider) {
        this.insuranceReportDataProvider = insuranceReportDataProvider;
    }

    @Override
    String getReportTitle(Long vehicleId) {
        return null;
    }

    @Override
    int getNumberOfColumn() {
        return 0;
    }

    @Override
    List<String> getHeaders() {
        return List.of();
    }

    @Override
    void addRows(PdfPTable table, ReportViewFilterParam param) {
        insuranceReportDataProvider.getReportData(param).forEach(v -> {

        });
    }

    @Override
    public ReportType supportType() {
        return ReportType.INSURANCE_BY_VEHICLE;
    }

}
