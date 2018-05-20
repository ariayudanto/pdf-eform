package com.aria.pdf.eform;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.interactive.form.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static final String DEFAULT_FORM_PATH = "/Users/aria/dev/pdf-eform/src/main/resources/form.pdf";

    public static void main(String[] args) throws Exception {
        String formPath = "";
        if (args.length != 1)
            formPath = Main.DEFAULT_FORM_PATH;
        else
            formPath = args[0];


        Main main = new Main();
        main.writeField(formPath);

    }

    public void writeField(String formPath) throws Exception {
        System.out.println("Loading form : " + formPath);

        File formFile = new File(formPath);
        PDDocument doc = PDDocument.load(formFile);
        PDPage firstPage = doc.getPage(0);

        PDPageContentStream stream = new PDPageContentStream(
                doc,
                firstPage,
                PDPageContentStream.AppendMode.APPEND,
                true,
                true);

        // for debug only!!
        drawCoordinateRuler(stream);

        PDFont font = PDType1Font.COURIER;
        stream.setFont(font, 9);

        stream.beginText();
        stream.newLineAtOffset(170, 786);
        stream.showText("34252223242"); // cif
        stream.endText();

        stream.beginText();
        stream.newLineAtOffset(170, 771);
        stream.showText("423784623874432"); // account no
        stream.endText();

        stream.beginText();
        stream.newLineAtOffset(170, 756);
        stream.showText("56363746573749923"); // account no
        stream.endText();


        stream.close();

        doc.save(formPath + ".result.pdf");
    }

    public void listField(String formPath) throws IOException {

        System.out.println("Loading form : " + formPath);

        File formFile = new File(formPath);
        PDDocument doc = PDDocument.load(formFile);

        PDDocumentCatalog catalog = doc.getDocumentCatalog();
        PDMetadata metadata = catalog.getMetadata();
        PDAcroForm form = catalog.getAcroForm();
        List fields = form.getFields();

        System.out.println("Fields " + fields.get(0).getClass());

        for (Object field : fields) {

            if (field instanceof PDTextField) {
                PDTextField textField = (PDTextField) field;
                System.out.println("Text Field " + textField.getFullyQualifiedName() + " " + textField.getValue());
            } else if (field instanceof PDChoice) {
                PDChoice choiceField = (PDChoice) field;
                System.out.println("Choice Field " + choiceField.getFullyQualifiedName() + " " + choiceField.getValue());
            } else if (field instanceof PDCheckBox) {
                PDCheckBox checkBox = (PDCheckBox) field;
                System.out.println("CheckBox Field " + checkBox.getFullyQualifiedName() + " " + checkBox.getValue());
            } else {
                System.out.print(field);
                System.out.print(" = ");
                System.out.print(field.getClass());
                System.out.println();
            }

        }
    }

    /**
     * Draw ruler coordinate on the edge of screen (for debug purpose only!!)
     * @param stream
     * @throws IOException
     */
    public void drawCoordinateRuler(PDPageContentStream stream) throws IOException {
        PDFont font = PDType1Font.COURIER;
        stream.setFont(font, 5);

        int coordX = 0;
        int coordY = 0;

        while (coordY <= 1000) {
            coordY += 5;

            stream.beginText();
            stream.newLineAtOffset(coordX, coordY);
            stream.showText("y = " + coordY);
            stream.endText();
        }

        coordX = 630;
        coordY = 0;

        while (coordY <= 1000) {
            coordY += 5;

            stream.beginText();
            stream.newLineAtOffset(coordX, coordY);
            stream.showText("y = " + coordY);
            stream.endText();
        }

        coordX = 0;
        coordY = 5;

        while (coordX <= 700) {
            coordX += 20;
            stream.beginText();
            stream.newLineAtOffset(coordX, coordY);
            stream.showText("" + coordX);
            stream.endText();
        }

        coordX = 0;
        coordY = 900;

        while (coordX <= 700) {
            coordX += 20;
            stream.beginText();
            stream.newLineAtOffset(coordX, coordY);
            stream.showText("" + coordX);
            stream.endText();
        }

    }
}
