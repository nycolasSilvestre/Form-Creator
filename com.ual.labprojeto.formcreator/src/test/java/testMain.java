import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdfparser.FDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.fdf.FDFCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class testMain {
    public static void main(String[] args) throws IOException {
 //       String filePath = "D:\\datasss-new.csv";
        PDDocument pdDocument;
        pdDocument = PDDocument.load(new File("D:\\pass.pdf"));
//        FDFDocument fdf = FDFDocument.load("D:\\fichaDoAluno.fdf");
//        COSDocument doc = fdf.getDocument();
//        FDFCatalog cat = fdf.getCatalog();
        PDAcroForm p = pdDocument.getDocumentCatalog().getAcroForm();
        Iterator<PDField> fields = p.getFieldIterator();
        File newCsv = new File("D:\\datasss-new - Copy.csv");
//        String[]header = getHeaderMapping(fields);
////        System.out.println(header);
//        Arrays.stream(header).forEach(s-> System.out.println(s));
//        System.out.println();
//
//        while (fields.hasNext()){
//            System.out.println(fields.next().getFullyQualifiedName());
//        }
//        importCsvData(fields,filePath);
//        p.exportFDF().save("D:\\fichaDoAluno_s.fdf");
////        p.importFDF();
        exportCsvData(pdDocument,Paths.get(newCsv.getPath()));

    }

    public static String[] getHeaderMapping(Iterator<PDField> fields){
        String[]header=null;
        String  s="";
        while (fields.hasNext()){
            PDField field = fields.next();
            s+=(field.getFullyQualifiedName()+",");
        }
        header = s.split(",");
        return header;
    }
    public static void importCsvData(Iterator<PDField>fields,String filePath) throws IOException {
        String[] header =getHeaderMapping(fields);
        CSVParser csvParser = CSVParser.parse(Paths.get(filePath), Charset.defaultCharset(),
                CSVFormat.DEFAULT.withHeader(header));
        Stream<CSVRecord> csvRecordStream = StreamSupport.stream(csvParser.spliterator(),false);
        csvRecordStream
                .skip(1)
                .forEach(record -> {
                    for (String s: header) {
                        System.out.print(s+" : "+record.get(s));
                    }
                    System.out.println("");
                });
    }
    public static void exportCsvData(PDDocument document, Path csvPath) throws IOException {
        String[] header =getHeaderMapping(document.getDocumentCatalog().getAcroForm().getFieldIterator());
            try {
                BufferedWriter writer = Files.newBufferedWriter(csvPath);

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL
                        .withHeader(header));

                csvPrinter.printRecord(getFieldRecords(document));
                csvPrinter.flush();
            }
            catch (Exception exception){
                exception.printStackTrace();
            }
    }

    public static String[] getFieldRecords(PDDocument pdDocument){
        Iterator<PDField> fields = pdDocument.getDocumentCatalog().getAcroForm().getFieldIterator();
        String  s="";
        while (fields.hasNext()){
            PDField field = fields.next();
            s+=(field.getValueAsString()+",");
        }
        return s.split(",");
    }
}
