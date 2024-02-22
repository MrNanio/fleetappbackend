package pl.nankiewic.fleetappbackend.report.exporter;

import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.report.ReportType;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.provider.InspectionReportDataProvider;

import java.util.List;

@Service
public class InspectionPDFExporter extends AbstractPDFExporter {

    private final InspectionReportDataProvider inspectionReportDataProvider;

    public InspectionPDFExporter(final InspectionReportDataProvider inspectionReportDataProvider) {
        this.inspectionReportDataProvider = inspectionReportDataProvider;
    }

    @Override
    String getReportTitle(Long vehicleId) {
        inspectionReportDataProvider.getVehicleData(vehicleId);
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
        var reportData = inspectionReportDataProvider.getReportData(param);

        reportData.forEach(v -> {

        });

    }

    @Override
    public ReportType supportType() {
        return ReportType.INSPECTION_BY_VEHICLE;
    }

}