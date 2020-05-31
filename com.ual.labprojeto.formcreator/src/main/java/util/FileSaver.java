package util;

import PDFUtil.FileType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileSaver {
    private FileChooser fileChooser = new FileChooser();
    private File file = null;
    private String savedFilePath;
    private FileType fileType;

    public void saveFile(Stage s){
        fileChooser.showSaveDialog(s);
    }
}
