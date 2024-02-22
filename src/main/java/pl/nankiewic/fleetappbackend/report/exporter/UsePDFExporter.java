package pl.nankiewic.fleetappbackend.report.exporter;

import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Service;
import pl.nankiewic.fleetappbackend.report.ReportType;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.provider.UseReportDataProvider;

import java.util.List;

@Service
public class UsePDFExporter extends AbstractPDFExporter {

    private final UseReportDataProvider useReportDataProvider;

    public UsePDFExporter(final UseReportDataProvider useReportDataProvider) {
        this.useReportDataProvider = useReportDataProvider;
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
        useReportDataProvider.getReportData(param).forEach(v -> {

        });
    }

    @Override
    public ReportType supportType() {
        return ReportType.USE_BY_VEHICLE;
    }

}