package pl.nankiewic.fleetappbackend.Reports;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleUse;


import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UsePDFExporter {
    private List<VehicleUse> vehicleUseList;
    private Vehicle vehicle;

    public UsePDFExporter(List<VehicleUse> vehicleUseList, Vehicle vehicle) {
        this.vehicleUseList = vehicleUseList;
        this.vehicle = vehicle;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("data", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("opis", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("km", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("kto", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (VehicleUse vehicleUse : vehicleUseList) {
            table.addCell(vehicleUse.getTripDate().toString());
            table.addCell(vehicleUse.getDescription());
            table.addCell(vehicleUse.getTrip().toString());
            table.addCell(vehicleUse.getUser().getEmail());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista użyć " + vehicle.getVehicleRegistrationNumber(), font);
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
