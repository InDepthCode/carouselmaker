package com.linkedinCarausels.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PdfExportService {

    public File generatePdfFromSlide(List<String> slides, String title) throws IOException{
        PDDocument document = new PDDocument();

        for(String slide: slides){
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 20);
            contentStream.newLineAtOffset(70,700);
            contentStream.setLeading(25f);

            String[] lines = slide.split("\n");
            for(String line: lines){
                contentStream.showText(line);
                contentStream.newLine();
            }
            contentStream.endText();
            contentStream.close();
        }

        String filename = "carousel/" + title.replaceAll(" ","_") + ".pdf";
        File file = new File(filename);
        file.getParentFile().mkdirs();
        document.save(file);
        document.close();
        return file;
    }
}
