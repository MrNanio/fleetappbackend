package pl.nankiewic.fleetappbackend.reports;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleInspection;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class InspectionPDFExporter {
    private List<VehicleInspection> vehicleInspectionList;
    private Vehicle vehicle;

    public InspectionPDFExporter(List<VehicleInspection> vehicleInspectionList, Vehicle vehicle) {
        this.vehicleInspectionList = vehicleInspectionList;
        this.vehicle = vehicle;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Data inspekcji", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Ważność", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Koszt", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Opis", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (VehicleInspection vehicleInspection : vehicleInspectionList) {
            table.addCell(vehicleInspection.getInspectionDate().toString());
            table.addCell(vehicleInspection.getExpirationDate().toString());
            table.addCell(vehicleInspection.getCost().toString());
            table.addCell(vehicleInspection.getDescription());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista inspekcji " + vehicle.getVehicleRegistrationNumber(), font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
