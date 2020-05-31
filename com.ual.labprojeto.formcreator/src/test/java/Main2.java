import PDFUtil.PDFieldToObservable;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDChoice;
import org.apache.pdfbox.pdmodel.interactive.form.PDComboBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Main2 {
    public static void main(String[] args) throws IOException {
        PDDocument pdDocument;
        pdDocument = PDDocument.load(new File("D:\\cb.pdf"));



//        List<Integer> li = new  ArrayList<>();
//        li.add(0);
//        field.setSelectedOptionsIndex(li);
        //        for (String opt: field.getOptions()) {
//            System.out.println(opt);
//        }

//        pdDocument.save("D:\\PJ_1_DGSS.pdf");
        //pdDocument.close();
        Iterator<PDField> temp = pdDocument.getDocumentCatalog().getAcroForm().getFieldIterator();
//        while (temp.hasNext()) {
//            PDField tempField = temp.next();
//            if (tempField.getFieldType() != null) {
//                System.out.println(tempField.getFullyQualifiedName());
//            }
//        }
        PDComboBox field = (PDComboBox) pdDocument.getDocumentCatalog().getAcroForm().getField("cbBox");
        System.out.println(field.getOptions());
        field.setValue("2");
        pdDocument.save("D:\\cb.pdf");
        pdDocument.close();


    }
}
