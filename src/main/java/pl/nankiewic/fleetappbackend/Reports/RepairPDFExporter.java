package pl.nankiewic.fleetappbackend.Reports;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import pl.nankiewic.fleetappbackend.Entity.Vehicle;
import pl.nankiewic.fleetappbackend.Entity.VehicleRepair;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class RepairPDFExporter {
    private List<VehicleRepair> vehicleRepairList;
    private Vehicle vehicle;

    public RepairPDFExporter(List<VehicleRepair> vehicleRepairList, Vehicle vehicle) {
        this.vehicleRepairList = vehicleRepairList;
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

        cell.setPhrase(new Phrase("Tytul", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Opis", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Koszt", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (VehicleRepair vehicleRepair : vehicleRepairList) {
            table.addCell(vehicleRepair.getRepairDate().toString());
            table.addCell(vehicleRepair.getTitle());
            table.addCell(vehicleRepair.getDescription());
            table.addCell(vehicleRepair.getCost().toString());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista napraw: " + vehicle.getVehicleRegistrationNumber(), font);
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
