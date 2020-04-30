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
import persistence.FileImporter;
import persistence.FileType;

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
    TableView<PDField> tbForms;
    @FXML
    TableColumn<PDField, String> colFieldName,colFieldType,colFieldDesc,colFieldOptions,colFieldValue,
            colFieldDefaultValue;
    private PDDocument pdDocument;

    @FXML
    public void initialize(){
        lbVersion.setText("Version: "+version);
        colFieldName.setCellValueFactory(new PropertyValueFactory<PDField,String>("FullyQualifiedName"));
        colFieldType.setCellValueFactory(new PropertyValueFactory<PDField,String>("FieldType"));
        colFieldDesc.setCellValueFactory(new PropertyValueFactory<PDField,String>("AlternateFieldName"));
        colFieldOptions.setCellValueFactory(new PropertyValueFactory<PDField,String>("AlternateFieldName"));
        colFieldValue.setCellValueFactory(new PropertyValueFactory<PDField,String>("Value"));
        colFieldDefaultValue.setCellValueFactory(new PropertyValueFactory<PDField,String>("DefaultValue"));

    }
    public void importUrlFile(){
        //Implementar download de ficheiro
        System.out.println("Importing URL file...");
    }
    public void importLocalPDFFile(){
        FileImporter fileImporter = new FileImporter(FileType.FDF);
        txtFilePath.setText(fileImporter.getImportedFilePath());
        pdDocument = fileImporter.importFile();
        tbForms.setItems(getFields());
        if(pdDocument != null)
            tabConfDados.setDisable(false);
    }
    public void confirm(){

        tabOutConfig.setDisable(false);
    }

    public ObservableList<PDField> getFields(){
        ObservableList<PDField> fields = FXCollections.observableArrayList();
        Iterator<PDField> temp = pdDocument.getDocumentCatalog().getAcroForm().getFieldIterator();
        while (temp.hasNext()){
            PDField tempField = temp.next();
            if(tempField.getFieldType()!=null){
                fields.add(tempField);
            }
        }
        return fields;
    }
}
