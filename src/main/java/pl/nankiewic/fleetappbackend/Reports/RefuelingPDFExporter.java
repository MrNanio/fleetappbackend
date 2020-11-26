package pl.nankiewic.fleetappbackend.Reports;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRefueling;


import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class RefuelingPDFExporter {
    private List<VehicleRefueling> refuelingList;
    private Vehicle vehicle;

    public RefuelingPDFExporter(List<VehicleRefueling> refuelingList, Vehicle vehicle) {
        this.refuelingList = refuelingList;
        this.vehicle = vehicle;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Data ", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Litry", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Koszt", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("kto tankował", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("opis", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (VehicleRefueling refueling : refuelingList) {
            table.addCell(refueling.getRefuelingDate().toString());
            table.addCell(refueling.getLitre());
            table.addCell(refueling.getCost().toString());
            table.addCell(refueling.getUser().getEmail());
            table.addCell(refueling.getDescription());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista tankowań " + vehicle.getVehicleRegistrationNumber(), font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
