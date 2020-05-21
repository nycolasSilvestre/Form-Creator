package PDFUtil;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.HashMap;

public class FieldLinker {
    private ObservableList<ObservablePdfFields> formsList;
    private ObservableList<String>ImportedFieldNames;
    private HashMap<String,String> linkedFields = null;
    private ComboBox<String> sourceFieldNames;

    public FieldLinker(ObservableList<ObservablePdfFields> formsList, ObservableList<String> importedFieldNames) {
        this.formsList = formsList;
        ImportedFieldNames = importedFieldNames;
    }

    public ObservableList<ObservablePdfFields> getFormsList() {
        return formsList;
    }

    public void setFormsList(ObservableList<ObservablePdfFields> formsList) {
        this.formsList = formsList;
    }

    public ObservableList<String> getImportedFieldNames() {
        return ImportedFieldNames;
    }

    public void setImportedFieldNames(ObservableList<String> importedFieldNames) {
        ImportedFieldNames = importedFieldNames;
    }

    public HashMap<String, String> getLinkedFields() {
        return linkedFields;
    }

    public void setLinkedFields(HashMap<String, String> linkedFields) {
        this.linkedFields = linkedFields;
    }
}
