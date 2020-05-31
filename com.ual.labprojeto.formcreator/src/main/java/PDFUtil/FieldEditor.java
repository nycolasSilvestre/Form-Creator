package PDFUtil;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.IOException;

public class FieldEditor {
    public void updateFieldValue(PDDocument pdDocument,String fieldName, String newValue) throws IOException {
        PDField temp = pdDocument.getDocumentCatalog().getAcroForm().getField(fieldName);
        temp.setValue(newValue);
    }
}
