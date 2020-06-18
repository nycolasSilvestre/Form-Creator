package util;

import PDFUtil.FileType;
import datafileshandler.CsvDataHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.fdf.FDFDocument;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FileImporter {

//  FileChooser
    private FileChooser fileChooser = new FileChooser();
    private File file = null;
    private String importedFilePath;
    private FileType fileType;
    private PDDocument pdDocument = null;
    public FileImporter(FileType fileType) {
        this.fileType = fileType;
    }

   //File Types
    public void pickFile() {
        File tempFile;
        FileChooser.ExtensionFilter fileFilter;
        switch (fileType){
            case PDF:
                fileFilter = new FileChooser.ExtensionFilter("PDF files (*.PDF)", "*.pdf");
                break;
            case FDF:
                fileFilter = new FileChooser.ExtensionFilter("FDF files (*.FDF)", "*.fdf");
                break;
            case CSV:
                fileFilter = new FileChooser.ExtensionFilter("CSV files (*.CSV)", "*.csv");
                break;
            case XFDF:
                fileFilter = new FileChooser.ExtensionFilter("XFDF files (*.XFDF)", "*.xfdf");
                break;
            case XML:
                fileFilter = new FileChooser.ExtensionFilter("XML files (*.XML)", "*.xml");
                break;
            default:
                fileFilter = null;
        }
        if(fileFilter != null)
            fileChooser.getExtensionFilters().add(fileFilter);
        tempFile = fileChooser.showOpenDialog(new Stage());
        if(tempFile == null){
            System.out.println("Null file");
        }
        else{
            file = tempFile;
            importedFilePath= file.getPath();
        }

    }
    //Import PDF Form File
    public PDDocument importPDFFile(){
        pickFile();
        if(file != null){
            try{
                pdDocument = PDDocument.load(file);
            }
            catch (IOException ex){
                System.err.println("Erro ao importar o arquivo selecionado.");
            }
        }
        return pdDocument;
    }
    public File importFile(){
        pickFile();
       return file;
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public void setFileChooser(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public PDDocument getPdDocument() {
        return pdDocument;
    }

    public void setPdDocument(PDDocument pdDocument) {
        this.pdDocument = pdDocument;
    }

    //  Getters & Setters
    public String getImportedFilePath() {
        return importedFilePath;
    }

    public void setImportedFilePath(String importedFilePath) {
        this.importedFilePath = importedFilePath;
    }

}
