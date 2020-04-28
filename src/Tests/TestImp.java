package Tests;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDChoice;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TestImp {
   public static void main(String[] args) {
        try {
            PDDocument pDDocument = PDDocument.load(new File("D:\\AlunoTest2009s.pdf"));
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            Iterator<PDField> fields = pDAcroForm.getFieldIterator();
//            fields.forEachRemaining(f-> System.out.printf("Nome do campo %s, tipo %s%n",f.getFullyQualifiedName(),f.getFieldType()));
//            fields.forEachRemaining(f-> System.out.printf("PartialName %s, Tostring %s%n",f.getPartialName(),f.toString()));
            while (fields.hasNext()){
                PDField temp = fields.next();
                //System.out.println(temp.getAlternateFieldName();
                if(temp.getFieldType()=="Ch"){
                    PDChoice choice = (PDChoice)  temp;
                    choice.setValue("Eng. s");
//                    choice.setDefaultValue();
//                    System.out.println(choice.setValue(););
                }
            }
          //  PDChoice field =(PDChoice) pDAcroForm.getField("topmostSubform[0].Page1[0].Distrito[0]");
//            PDChoice choice = (PDChoice) pDAcroForm.getField("topmostSubform[0].Page1[0].Distrito[0]");


//           System.out.println(field.getOptionsDisplayValues().toString());
            pDDocument.save("D:\\AlunoTest2009s.pdf");
            pDDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
