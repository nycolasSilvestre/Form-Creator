import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class test {
    public static void main(String[] args) throws IOException {
        String line;
        String[] csvHeader=null;
        String filePath = "D:\\data-new.csv";
        Path path = Paths.get(filePath);
//        ctc();
        try (
                BufferedWriter writer = Files.newBufferedWriter(path);

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL
                    .withHeader("ID", "Name", "Designation", "Company"));
        ) {
            csvPrinter.printRecord("1", "Sundar Pichai ♥", "CEO", "Google");
            csvPrinter.printRecord("2", "Satya Nadella", "CEO", "Microsoft");
            csvPrinter.printRecord("3", "Tim cook", "CEO", "Apple");

            csvPrinter.printRecord(Arrays.asList("4", "Mark Zuckerberg", "CEO", "Facebook"));

            csvPrinter.flush();
        }
    }

    public static void csvParser(Path filePath) throws IOException {
        CSVParser csvParser = CSVParser.parse(filePath, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader("Nome Campo",
                "Valor"));
        csvParser.forEach(record -> {
            System.out.println(record.toMap());
        });
    }
    public static void cs2(Path filePath) throws IOException {
        CSVParser csvParser = CSVParser.parse(filePath, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader("Nome Campo",
                "Valor"));
        Stream<CSVRecord> csvRecordStream = StreamSupport.stream(csvParser.spliterator(),false);
        csvRecordStream
                .skip(1)
                .forEach(record -> {
                    System.out.println(record.toMap());
                });

    }
    public static void ctc() throws IOException {
        Reader in = new FileReader("D:\\data.csv");
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        for (CSVRecord record : records) {
            System.out.println(record.get(1).trim());
        }
    }

}
