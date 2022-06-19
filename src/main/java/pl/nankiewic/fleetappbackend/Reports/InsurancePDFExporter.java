package pl.nankiewic.fleetappbackend.Reports;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleInsurance;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class InsurancePDFExporter {
    private List<VehicleInsurance> vehicleInsuranceList;
    private Vehicle vehicle;

    public InsurancePDFExporter(List<VehicleInsurance> vehicleInsuranceList, Vehicle vehicle) {
        this.vehicleInsuranceList = vehicleInsuranceList;
        this.vehicle = vehicle;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Poczatek ", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Koniec", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Typ", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Numer", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Koszt", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (VehicleInsurance vehicleInsurance : vehicleInsuranceList) {
            table.addCell(vehicleInsurance.getEffectiveDate().toString());
            table.addCell(vehicleInsurance.getExpirationDate().toString());
            table.addCell(vehicleInsurance.getInsuranceType().getInsuranceType().name());
            table.addCell(vehicleInsurance.getPolicyNumber());
            table.addCell(vehicleInsurance.getCost().toString());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista ubezpieczeń " + vehicle.getVehicleRegistrationNumber(), font);
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
