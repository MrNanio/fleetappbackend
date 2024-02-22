package pl.nankiewic.fleetappbackend.report.exporter;

import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.report.ReportType;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.provider.RefuelingReportDataProvider;

import java.util.List;

@Service
public class RefuelingPDFExporter extends AbstractPDFExporter {

    private final RefuelingReportDataProvider refuelingReportDataProvider;

    public RefuelingPDFExporter(final RefuelingReportDataProvider refuelingReportDataProvider) {
        this.refuelingReportDataProvider = refuelingReportDataProvider;
    }

    @Override
    String getReportTitle(Long vehicleId) {
        refuelingReportDataProvider.getVehicleData(vehicleId);
        return "null";
    }

    @Override
    int getNumberOfColumn() {
        return 3;
    }

    @Override
    List<String> getHeaders() {
        return List.of();
    }

    @Override
    void addRows(PdfPTable table, ReportViewFilterParam param) {
        refuelingReportDataProvider.getReportData(param).forEach(v -> {

        });
    }

    @Override
    public ReportType supportType() {
        return ReportType.REFUELING_BY_VEHICLE;
    }

}
