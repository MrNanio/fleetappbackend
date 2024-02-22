package pl.nankiewic.fleetappbackend.report.exporter;

import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.report.ReportType;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.provider.RepairReportDataProvider;

import java.util.List;

@Service
public class RepairPDFExporter extends AbstractPDFExporter {

    private final RepairReportDataProvider repairReportDataProvider;

    public RepairPDFExporter(final RepairReportDataProvider repairReportDataProvider) {
        this.repairReportDataProvider = repairReportDataProvider;
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
        repairReportDataProvider.getReportData(param).forEach(v->{

        });
    }

    @Override
    public ReportType supportType() {
        return ReportType.REPAIR_BY_VEHICLE;
    }

}