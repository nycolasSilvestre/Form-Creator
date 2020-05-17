package pdfaux;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

public class MessageBox{
   public Alert msgBox;
   public MessageBox(){  }
   public void error(String title,String header, String context){
       msgBox = new Alert(Alert.AlertType.ERROR);
       msgBox.setTitle(title);
       msgBox.setHeaderText(header);
       msgBox.setContentText(context);
       msgBox.showAndWait();
   }
    public void warning(String title,String header, String context){
        msgBox = new Alert(Alert.AlertType.WARNING);
        msgBox.setTitle(title);
        msgBox.setHeaderText(header);
        msgBox.setContentText(context);
        msgBox.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        msgBox.showAndWait();
    }
}
