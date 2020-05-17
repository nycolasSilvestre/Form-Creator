package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import pdfaux.*;

import java.io.IOException;
import java.util.HashMap;

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
    @FXML
    Label txtDataFilePath;

    private PDDocument pdDocument;
    private MessageBox msgBox = new MessageBox();
    @FXML
    public void initialize(){

        lbVersion.setText("Version: "+version);
        initializeTableOne();
    }

    //Import File tab controls
    public void importUrlFile(){
        //Implementar download de ficheiro
        System.out.println("Importing URL file...");
    }
    public void importLocalPDFFile(){
        FileImporter fileImporter = new FileImporter(FileType.FDF);
        FieldCollector fieldCollector = new FieldCollector();
        pdDocument = fileImporter.importPDFFile();
        if(fileImporter.getImportedFilePath()!="" && fileImporter.getImportedFilePath() != null) {
            if (pdDocument != null && fieldCollector.getFields(pdDocument) !=null){
                tbForms.setItems((fieldCollector.getFields(pdDocument)));
                txtFilePath.setText(fileImporter.getImportedFilePath());
            }
        }
    }
    public void confirm(){
        tabOutConfig.setDisable(false);
    }


    public void initializeTableOne(){
        colFieldName.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("name"));
        colFieldType.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("type"));
        colFieldDesc.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("description"));
        colFieldOptions.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("optionsStr"));
        colFieldValue.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("value"));
        colFieldDefaultValue.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("defaultValue"));
    }
    public void importCSV(){
        msgBox.warning("Atenção!","AVISO","Ficheiros CSV devem conter um cabeçalho com os nomes dos " +
                "campos contidos no formulário!");
        FileImporter fileImporter = new FileImporter(FileType.CSV);
    }
    public void importFPDF() throws IOException {fillFileds();}
    public void importXFDF(){}
    public void importXML(){}

    public void fillFileds() throws IOException {
        PDAcroForm pdAcroForm= pdDocument.getDocumentCatalog().getAcroForm();
        PDField field = null;
        HashMap<String,String> test = new HashMap<>();
        test.put("tbNome","Ederson");
        test.put("tbAno","Birigui");
        test.put("tbCurso","Eng.da Pesca");
        test.put("tbAno","2020");
        for ( String s: test.keySet()) {
            field = pdAcroForm.getField(s);
            field.setValue(test.get(s));
            pdDocument.save("D:\\preenchimentoLista.pdf");
        }
        pdDocument.close();
    }
}
