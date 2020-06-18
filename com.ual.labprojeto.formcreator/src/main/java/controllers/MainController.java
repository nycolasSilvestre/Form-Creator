package controllers;

import datafileshandler.CsvDataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
import PDFUtil.*;
import org.apache.pdfbox.pdmodel.interactive.form.PDComboBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import util.FileImporter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class MainController {
    String version = "0.0.1";

    @FXML
    TabPane tabPane;

    @FXML
    Label lbVersion;

    @FXML
    Tab tabImportFiles,tabOutConfig;

    @FXML
    TextField txtFilePath;

//    @FXML
//    Button btnSaveAsPDF,btnExportFDF,btnExportXFDF,btnExportCSV,btnExportJSON;

    @FXML
    TableView<ObservablePdfFields> tbForms;
    @FXML
    TableColumn<ObservablePdfFields, String> colFieldName,colFieldType,colFieldDesc,colFieldOptions,colFieldValue,
            colFieldDefaultValue;
    @FXML
    Label txtDataFilePath;

    private PDDocument pdDocument;
    private MessageBox msgBox = new MessageBox();
    private FieldCollector fieldCollector = new FieldCollector();
    private FormFieldHandler formFieldHandler = new FormFieldHandler();
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
        FileImporter fileImporter = new FileImporter(FileType.PDF);
        pdDocument = fileImporter.importPDFFile();
        if(fileImporter.getImportedFilePath()!="" && fileImporter.getImportedFilePath() != null) {
            txtFilePath.setText(fileImporter.getImportedFilePath());
            refreshTable();
        }
    }

    public void initializeTableOne(){
        colFieldName.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("name"));
        colFieldType.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("type"));
        colFieldDesc.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("description"));
        colFieldOptions.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("optionsStr"));
        colFieldValue.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("value"));
        colFieldDefaultValue.setCellValueFactory(new PropertyValueFactory<ObservablePdfFields,String>("defaultValue"));
        rowClickHandler();
    }
    public void importCSV(){
        msgBox.warning("Atenção!","AVISO","Ficheiros CSV devem conter um cabeçalho com os nomes dos " +
                "campos contidos no formulário!");
        FileImporter fileImporter = new FileImporter(FileType.CSV);
    }

    public void importXFDF() throws IOException {}
    public void importXML() throws IOException {}

    public void importFDF(ActionEvent actionEvent) throws IOException {
        FileImporter fileImporter = new FileImporter(FileType.FDF);
        pdDocument.getDocumentCatalog().getAcroForm()
                .importFDF(FDFDocument.load(fileImporter.importFile()));
        refreshTable();
    }
    public void importCSV(ActionEvent actionEvent) throws IOException {
        FileImporter fileImporter = new FileImporter(FileType.CSV);
        CsvDataHandler csvDataHandler = new CsvDataHandler(fileImporter.importFile());
        csvDataHandler.importCsvData(pdDocument);
        refreshTable();
    }

    public void refreshTable(){
        if (pdDocument != null && fieldCollector.getFields(pdDocument) !=null) {
            tbForms.setItems((fieldCollector.getFields(pdDocument)));
        }
    }
    public void rowClickHandler(){
        tbForms.setRowFactory(tb -> {
            TableRow<ObservablePdfFields> row = new TableRow<>();
            row.setOnMouseClicked(event ->{
                if(!row.isEmpty() && event.getButton()== MouseButton.PRIMARY && event.getClickCount()==2){
                    ObservablePdfFields temp = row.getItem();
                    if(temp.getType()=="ComboBox"){
                        try {
                            updateComboBoxFormValue(temp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                        else{
                        try {
                            updateTextFormValue(temp);
                        } catch (IOException e) {}
                    }
                }
            });
            return row;
        });
    }
    public void updateTextFormValue(ObservablePdfFields field) throws IOException {
        TextInputDialog dialog = new TextInputDialog(field.getValue());
        dialog.setTitle("Editar Campo");
        dialog.setHeaderText("Editar valor do campo "+field.getName());
        dialog.setContentText("Por favor, inserir novo valor:");
        Optional<String> result = dialog.showAndWait();
        formFieldHandler.updateTextField(pdDocument,field.getName(),result.get());
        refreshTable();
    }
    public void updateComboBoxFormValue(ObservablePdfFields field) throws IOException {
        List<String> options = field.getOptions();
        ChoiceDialog<String> dialog = new ChoiceDialog<>(field.getValue(),options);
        dialog.setTitle("Editar Campo");
        dialog.setHeaderText("Editar valor do campo "+field.getName());
        dialog.setContentText("Por favor, escolher novo valor:");
        Optional<String> result = dialog.showAndWait();
        formFieldHandler.updateComboBoxField(pdDocument,field.getName(),result.get());
        refreshTable();

    }
    public void exportFDF(){}
    public void exportXFDF(){}
    public void exportJSON(){}

    public void savePDF(){
        Window stage  = tabPane.getScene().getWindow();
        FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("PDF files (*.PDF)", "*.pdf");
        FileChooser f = new FileChooser();
        f.getExtensionFilters().add(fileFilter);
        f.setTitle("Salvar Ficheiro");

        File save = f.showSaveDialog(stage);
        if (save != null) {
            try {
                pdDocument.save(save.getPath());
            } catch (IOException ex) {
            }
        }
    }
    public void exportCsv(){
        Window stage  = tabPane.getScene().getWindow();
        FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("PDF files (*.PDF)", "*.pdf");
        FileChooser f = new FileChooser();
        f.getExtensionFilters().add(fileFilter);
        f.setTitle("Salvar Ficheiro");

        File save = f.showSaveDialog(stage);
        if (save != null) {
            try {
                pdDocument.save(save.getPath());
            } catch (IOException ex) {
            }
        }
    }
}
