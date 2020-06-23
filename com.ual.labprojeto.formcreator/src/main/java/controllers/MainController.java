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
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
import PDFUtil.*;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.tools.ImportXFDF;
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
    @FXML
    TableView<ObservablePdfFields> tbForms;
    @FXML
    TableColumn<ObservablePdfFields, String> colFieldName,colFieldType,colFieldDesc,colFieldOptions,colFieldValue,
            colFieldDefaultValue;
    @FXML
    MenuButton menuExport,menuImport;

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
    public void importLocalPDFFile() throws IOException {
        FileImporter fileImporter = new FileImporter(FileType.PDF);
        checkIfIsClosed();
        pdDocument = fileImporter.importPDFFile();
        if(fileImporter.getImportedFilePath()!="" && fileImporter.getImportedFilePath() != null) {
            txtFilePath.setText(fileImporter.getImportedFilePath());
            refreshTable();
            menuExport.setDisable(false);
            menuImport.setDisable(false);
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

    public void importXFDF() throws IOException {
        FileImporter fileImporter = new FileImporter(FileType.XFDF);
        PDDocumentCatalog docCatalog = pdDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        acroForm.setCacheFields(true);
        FDFDocument fdfDocument = FDFDocument.loadXFDF(fileImporter.importFile());
        acroForm.importFDF(fdfDocument);
        refreshTable();
    }
    public void importFDF(ActionEvent actionEvent) throws IOException {
        FileImporter fileImporter = new FileImporter(FileType.FDF);
        pdDocument.getDocumentCatalog().getAcroForm()
                .importFDF(FDFDocument.load(fileImporter.importFile()));
        refreshTable();
    }
    public void importCSV(ActionEvent actionEvent) throws IOException {
        msgBox.warning("Atenção!","AVISO","Ficheiros CSV devem estar no formato indicado nas " +
                "intruções de uso.");
        FileImporter fileImporter = new FileImporter(FileType.CSV);
        CsvDataHandler csvDataHandler = new CsvDataHandler(fileImporter.importFile());
        csvDataHandler.importCsvData(pdDocument);
        refreshTable();
    }
    public void clearFields() throws IOException {
        formFieldHandler.clearAllFields(pdDocument);
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
                    if(temp.isCombo()){
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
        //if(!result.isEmpty())
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
        //if(!result.isEmpty())
        formFieldHandler.updateComboBoxField(pdDocument,field.getName(),result.get());
        refreshTable();

    }

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
        FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("CSV files (*.CSV)", "*.csv");
        FileChooser f = new FileChooser();
        f.getExtensionFilters().add(fileFilter);
        f.setTitle("Exportar CSV");

        File save = f.showSaveDialog(stage);
        if (save != null) {
            CsvDataHandler csvDataHandler = new CsvDataHandler(save);
            csvDataHandler.exportCsv(pdDocument);
        }
    }
    public void exportFDF() throws IOException {
        Window stage  = tabPane.getScene().getWindow();
        FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("FDF files (*.FDF)", "*.fdf");
        FileChooser f = new FileChooser();
        f.getExtensionFilters().add(fileFilter);
        f.setTitle("Exportar FDF");

        File save = f.showSaveDialog(stage);
        if (save != null) {
            pdDocument.getDocumentCatalog().getAcroForm().exportFDF().save(save);
        }
    }
    public void exportXFDF() throws IOException {
        Window stage  = tabPane.getScene().getWindow();
        FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("FDF files (*.XFDF)", "*.xfdf");
        FileChooser f = new FileChooser();
        f.getExtensionFilters().add(fileFilter);
        f.setTitle("Exportar XFDF");
        File save = f.showSaveDialog(stage);
        if (save != null) {
            pdDocument.getDocumentCatalog().getAcroForm().exportFDF().saveXFDF(save);
        }
    }
    public void checkIfIsClosed() throws IOException {
        if(pdDocument != null)
            pdDocument.close();
    }
}
