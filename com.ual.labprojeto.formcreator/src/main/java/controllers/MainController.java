package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
import PDFUtil.*;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import util.FileImporter;
import util.FileSaver;

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
    Button btnSaveAsPDF,btnExportFDF,btnExportXFDF,btnExportCSV,btnExportJSON;

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
    private FieldEditor fieldEditor = new FieldEditor();
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

    public void importXFDF(){
        Window stage  = tabPane.getScene().getWindow();
        FileChooser f = new FileChooser();
        f.showSaveDialog(stage);
    }
    public void importXML() throws IOException {}

    public void importFDF(ActionEvent actionEvent) throws IOException {
        FileImporter fileImporter = new FileImporter(FileType.FDF);
        pdDocument.getDocumentCatalog().getAcroForm()
                .importFDF(FDFDocument.load(fileImporter.importFDF()));
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
                    try {
                        getFormNewValue(temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
    public void getFormNewValue(ObservablePdfFields field) throws IOException {
        TextInputDialog dialog = new TextInputDialog(field.getValue());
        dialog.setTitle("Editar Campo");
        dialog.setHeaderText("Editar valor do campo "+field.getName());
        dialog.setContentText("Por favor, inserir novo valor:");
        Optional<String> result = dialog.showAndWait();
        PDField temp = pdDocument.getDocumentCatalog().getAcroForm().getField(field.getName());
        if(!result.toString().isEmpty())
            temp.setValue(result.get());
        refreshTable();

    }

// old
//    public void fillFileds() throws IOException {
//        PDAcroForm pdAcroForm= pdDocument.getDocumentCatalog().getAcroForm();
//        PDField field = null;
//        HashMap<String,String> test = new HashMap<>();
//        test.put("tbNome","Ederson");
//        test.put("tbAno","Birigui");
//        test.put("tbCurso","Eng.da Pesca");
//        test.put("tbAno","2020");
//        for ( String s: test.keySet()) {
//            field = pdAcroForm.getField(s);
//            field.setValue(test.get(s));
//            pdDocument.save("D:\\preenchimentoLista.pdf");
//        }
//        pdDocument.close();
//    }

}
