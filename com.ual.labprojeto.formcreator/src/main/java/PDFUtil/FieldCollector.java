package PDFUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.util.Iterator;

public class FieldCollector {

    public ObservableList<ObservablePdfFields> getFields( PDDocument pdDocument){
        ObservableList<ObservablePdfFields> fields = FXCollections.observableArrayList();
        try {
            Iterator<PDField> temp = pdDocument.getDocumentCatalog().getAcroForm().getFieldIterator();
            while (temp.hasNext()) {
                PDField tempField = temp.next();
                if (tempField.getFieldType() != null) {
                    fields.add(PDFieldToObservable.pdfieldToObsPdfField(tempField));
                }
            }

        }
        catch (Exception e){
            MessageBox msgBox = new MessageBox();
            msgBox.error("Erro!","Erro ao importar o ficheiro.",
                    "Verfique o formato do ficheiro.");
        }
        finally {
            if (fields.size() == 0)
                return null;
            else
                return fields;
        }
    }
}
