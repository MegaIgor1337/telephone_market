package market.service.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.experimental.UtilityClass;
import market.model.entity.Order;
import market.model.entity.OrderProduct;
import market.model.entity.Product;

import java.io.ByteArrayOutputStream;
import java.util.List;

@UtilityClass
public class GeneratePdfFile {

    public static ByteArrayOutputStream generatePdf(Order order) throws DocumentException {
        if (order == null) {
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();


            // Здесь можно добавить информацию о заказе в PDF
            document.add(new Paragraph("Order ID: " + order.getId()));
            document.add(new Paragraph("User: " + order.getUser().getUsername()));
            document.add(new Paragraph("Date of order: " + order.getDate().getYear() + "-" +
                                       order.getDate().getMonth().getValue() + "-"
                                       + order.getDate().getDayOfMonth()));
            document.add(new Paragraph("Date of delivery: " + order.getDateOfDelivery()));
            document.add(new Paragraph("Price: " + order.getCost()));
            document.add(new Paragraph("Status: " + order.getStatus()));
            document.add(new Paragraph("Delivery address: " + order.getAddress().getCountry() + ", "
                                       + order.getAddress().getCity() +
                                       ", " + order.getAddress().getStreet() +
                                       ", " + order.getAddress().getHouse() +
                                       ", " + order.getAddress().getFlat()));
            document.add(new Paragraph("Count of products: " + order.getProducts().size()));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(6); // 6 столбцов: brand, model, color, country, count, price
            addTableHeader(table);
            addProductsToTable(table, order.getProducts());
            document.add(table);

            // Добавьте другие необходимые поля о заказе и продуктах

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return outputStream;
    }

    private static void addTableHeader(PdfPTable table) {
        table.addCell("Brand");
        table.addCell("Model");
        table.addCell("Color");
        table.addCell("Country");
        table.addCell("Count");
        table.addCell("Price");
    }

    private static void addProductsToTable(PdfPTable table, List<OrderProduct> products) {
        for (OrderProduct product : products) {
            Product productInfo = product.getProduct();
            table.addCell(productInfo.getBrand().getBrand());
            table.addCell(productInfo.getModel().getModel());
            table.addCell(productInfo.getColor().getColor());
            table.addCell(productInfo.getCountry().getCountry());
            table.addCell(String.valueOf(product.getUserCount()));
            table.addCell(productInfo.getCost().toString());
        }
    }
}
