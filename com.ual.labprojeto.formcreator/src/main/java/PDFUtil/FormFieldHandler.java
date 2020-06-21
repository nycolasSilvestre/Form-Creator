package PDFUtil;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDComboBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.IOException;
import java.util.Iterator;

public class FormFieldHandler {
    public FormFieldHandler() {}

    public void updateTextField(PDDocument document, String fieldName, String value) throws IOException {
        PDField field = document.getDocumentCatalog().getAcroForm().getField(fieldName);
        if (!value.isEmpty())
            if(field.getFieldType()!="Ch")
                field.setValue(value);
            else
                updateComboBoxField(document,fieldName,value);
    }
    public void updateComboBoxField(PDDocument document, String fieldName, String value) throws IOException {
        PDComboBox field = (PDComboBox)document.getDocumentCatalog().getAcroForm().getField(fieldName);
        if (!value.isEmpty())
            field.setValue(value);
    }
    public void clearAllFields(PDDocument document) throws IOException{
        Iterator<PDField> fields = document.getDocumentCatalog().getAcroForm().getFieldIterator();
        while (fields.hasNext()){
            PDField field = fields.next();
            if(field.getFieldType()=="Tx") {
                field.setValue("");
            }
        }
    }

}