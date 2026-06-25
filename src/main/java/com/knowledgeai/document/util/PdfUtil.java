package com.knowledgeai.document.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfUtil {

    public static String extractText(InputStream inputStream)
            throws IOException {

        try (PDDocument document =
                     Loader.loadPDF(inputStream.readAllBytes())) {

            PDFTextStripper stripper =
                    new PDFTextStripper();

            return stripper.getText(document);
        }
    }
}