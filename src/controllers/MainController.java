package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import pdfaux.ObservablePdfFields;
import persistence.FileImporter;
import persistence.FileType;
import persistence.PDFieldToObservable;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class MainController {
    String version = "0.0.1";
    @FXML
    Label lbVersion;

    @FXML
    Tab tabImportFiles,tabConfDados,tabOutConfig;
    
    @FXML
    TextField txtFilePath;

    @FXML
    Button btnConfirm;

    @FXML
    TableView<ObservablePdfFields> tbForms;
    @FXML
    TableColumn<ObservablePdfFields, String> colFieldName,colFieldType,colFieldDesc,colFieldOptions,colFieldValue,
            colFieldDefaultValue;
    private PDDocument pdDocument;

    @FXML
    public void initialize(){
        lbVersion.setText("Version: "+version);
        colFieldName.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("name"));
        colFieldType.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("type"));
        colFieldDesc.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("description"));
        colFieldOptions.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("optionsStr"));
        colFieldValue.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("value"));
        colFieldDefaultValue.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("defaultValue"));
    }
    public void importUrlFile(){
        //Implementar download de ficheiro
        System.out.println("Importing URL file...");
    }
    public void importLocalPDFFile(){
        FileImporter fileImporter = new FileImporter(FileType.FDF);
        pdDocument = fileImporter.importFile();
        if(fileImporter.getImportedFilePath()!="" && fileImporter.getImportedFilePath() != null) {
            if (pdDocument != null)
                tbForms.setItems(getFields());
                tabConfDados.setDisable(false);
                txtFilePath.setText(fileImporter.getImportedFilePath());
        }
    }
    public void confirm(){

        tabOutConfig.setDisable(false);
    }

    public ObservableList<ObservablePdfFields> getFields(){
        ObservableList<ObservablePdfFields> fields = FXCollections.observableArrayList();
        Iterator<PDField> temp = pdDocument.getDocumentCatalog().getAcroForm().getFieldIterator();
        while (temp.hasNext()){
            PDField tempField = temp.next();
            if(tempField.getFieldType()!=null){
                fields.add(PDFieldToObservable.pdfieldToObsPdfField(tempField));
            }
        }
        return fields;
    }
}
