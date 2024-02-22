package pl.nankiewic.fleetappbackend.report.exporter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pl.nankiewic.fleetappbackend.report.ReportType;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class AbstractPDFExporter {

    abstract String getReportTitle(Long vehicleId);
    abstract int getNumberOfColumn();
    abstract List<String> getHeaders();
    abstract void addRows(PdfPTable table, ReportViewFilterParam param);

    public abstract ReportType supportType();

    public void export(HttpServletResponse response, ReportViewFilterParam param) throws IOException, DocumentException {

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vehicle_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        response.setHeader("Pragma", "public");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        var font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
        var paragraph = new Paragraph(getReportTitle(param.getVehicleId()), font);
        paragraph.setAlignment(1);
        document.add(paragraph);

        var header = getHeaders();
        var table = new PdfPTable(getNumberOfColumn());

        addTableHeader(table, header);
        addRows(table, param);
        document.add(table);
        document.close();
    }

    private void addTableHeader(PdfPTable table, List<String> headers) {
        headers.forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }

}