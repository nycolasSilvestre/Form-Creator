package datafileshandler;

import PDFUtil.FileType;
import PDFUtil.FormFieldHandler;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import util.FileImporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CsvDataHandler {
    private Path csvPath;
    private FormFieldHandler formFieldHandler = new FormFieldHandler();
    private FileImporter fileImporter = new FileImporter(FileType.CSV);
    public CsvDataHandler(File csvFile) {
        this.csvPath = Paths.get(csvFile.getPath());
    }
    public CsvDataHandler(){
        super();
    }

    public String[] getHeaderMapping(Iterator<PDField> fields){
        String[]header=null;
        String  s="";
        while (fields.hasNext()){
            PDField field = fields.next();
            s+=(field.getFullyQualifiedName()+",");
        }
        header = s.split(",");
        return header;
    }
    public void importCsvData(PDDocument pdDocument, String fileDirectory,  String filePrefix) throws IOException {
        Iterator<PDField> fields = pdDocument.getDocumentCatalog().getAcroForm().getFieldIterator();
        String[] header =getHeaderMapping(fields);
        CSVParser csvParser = CSVParser.parse(csvPath, Charset.defaultCharset(),
                CSVFormat.DEFAULT.withHeader(header));
        Stream<CSVRecord> csvRecordStream = StreamSupport.stream(csvParser.spliterator(),false);
        AtomicInteger counter = new AtomicInteger();
        csvRecordStream
                .skip(1)
                .forEach(record -> {
                    for (String s: header) {
                        try {
                             formFieldHandler.updateTextField(pdDocument,s,record.get(s).trim());
                        } catch (IOException e) {e.printStackTrace();}
                    }
                    try {
                        counter.getAndIncrement();
                        Path fileDir = Paths.get(fileDirectory,filePrefix+"_"+counter+".pdf");
                        File f = new File(fileDir.toString());
                        while(f.exists() && ! f.isDirectory()){

                        }
                        pdDocument.save(fileDir.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
    public void exportCsv(PDDocument document) throws IOException {
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
//    public void importCsvData(PDDocument document) throws IOException {
//        CSVParser csvParser = CSVParser.parse(csvPath, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader("Nome Campo",
//                "Valor"));
//        Stream<CSVRecord> csvRecordStream = StreamSupport.stream(csvParser.spliterator(),false);
//        csvRecordStream
//                .skip(1)
//                .forEach(record -> {
//                    try {
//                        formFieldHandler.updateTextField(document,record.get(0),record.get(1).trim());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//    }
//    public void exportCsv(PDDocument pdDocument){
//        Iterator<PDField> temp = pdDocument.getDocumentCatalog().getAcroForm().getFieldIterator();
//        try {
//            BufferedWriter writer = Files.newBufferedWriter(csvPath);
//
//            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL
//                    .withHeader("Nome Campo","Valor"));
//            while (temp.hasNext()){
//                PDField field = temp.next();
//                csvPrinter.printRecord(field.getFullyQualifiedName(),field.getValueAsString());
//            }
//            csvPrinter.flush();
//        }
//        catch (Exception exception){
//            exception.printStackTrace();
//        }
//
//    }

}
