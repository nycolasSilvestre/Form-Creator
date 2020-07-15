import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class test {
    public static void main(String[] args) throws IOException {
        String line;
        String[] csvHeader={"Name", "Designation", "Company"};
//        ArrayList<String> csvHeader = new ArrayList<>();
//        csvHeader.add("Name");
//        csvHeader.add("Designation");
//        csvHeader.add("Company");
        String filePath = "D:\\datasss-new.csv";
        Path path = Paths.get(filePath);
//        ctc();
        try (
                BufferedWriter writer = Files.newBufferedWriter(path);

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL
                    .withHeader(csvHeader));
        ) {
            csvPrinter.printRecord("Sundar dedededed ♥", "CEO", "Google");
            csvPrinter.printRecord("Satya Nadddededeella", "CEOeeee", "Microsoft");
            csvPrinter.printRecord("Timeedededededd cook", "CEdddO", "Apple");

            csvPrinter.printRecord(Arrays.asList("Mark Zuckerberg", "CEO", "Facebook"));

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
