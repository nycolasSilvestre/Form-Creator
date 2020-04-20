package pdfaux;

import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;
import java.io.IOException;

public class ImportFiles {
   public static PDDocument importPDF(File pdfFile){
       PDDocument pdDocument = null;
       try {
              pdDocument = PDDocument.load(pdfFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdDocument;
   }

}
