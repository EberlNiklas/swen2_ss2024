package com.example.swen2_ss2024.pdfGenerator;

import com.example.swen2_ss2024.entity.TourLog;
import com.example.swen2_ss2024.service.TourLogListService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;

public class TourLogPDFGenerator {

    private final TourLogListService tourLogListService;

    private static final String DESTINATION = "TourLog_Export.pdf";

    public TourLogPDFGenerator(TourLogListService tourLogListService) {
        this.tourLogListService = tourLogListService;
    }

    public void createPdfWithMap() throws IOException {
        TourLog tourLog = tourLogListService.getSelectedTourLog();
        if (tourLog == null) {
            System.out.println("No tour log selected.");
            return;
        }

        // Initialize PDF writer and document
        PdfWriter writer = new PdfWriter(DESTINATION);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Add tour log data to the document
        document.add(new Paragraph("Tour Log Name: " + tourLog.getName()));
        document.add(new Paragraph("Date: " + tourLog.getDate()));
        document.add(new Paragraph("Rating: " + tourLog.getRating()));
        document.add(new Paragraph("Info: " + tourLog.getInfo()));
        document.add(new Paragraph("Distance: " + tourLog.getDistance()));
        document.add(new Paragraph("Duration: " + tourLog.getDuration()));

        // Close the document
        document.close();
        System.out.println("PDF created at: " + DESTINATION);
    }
}
