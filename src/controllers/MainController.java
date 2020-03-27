package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    String version = "0.0.1";
    @FXML
    Label lbVersion;

    @FXML
    public void initialize(){
        lbVersion.setText("Version: "+version);
    }
}
