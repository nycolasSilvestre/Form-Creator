package datafileshandler;

import PDFUtil.FileType;
import PDFUtil.FormFieldHandler;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.pdfbox.pdmodel.PDDocument;
import util.FileImporter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CsvDataHandler {
    private Path csvPath;
    private FormFieldHandler formFieldHandler = new FormFieldHandler();
    private FileImporter fileImporter = new FileImporter(FileType.CSV);
    public CsvDataHandler(File csvFile) {
        this.csvPath = Paths.get(csvFile.getPath());
    }

    public void importCsvData(PDDocument document) throws IOException {
        CSVParser csvParser = CSVParser.parse(csvPath, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader("Nome Campo",
                "Valor"));
        Stream<CSVRecord> csvRecordStream = StreamSupport.stream(csvParser.spliterator(),false);
        csvRecordStream
                .skip(1)
                .forEach(record -> {
                    try {
                        formFieldHandler.updateTextField(document,record.get(0),record.get(1));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }

}
