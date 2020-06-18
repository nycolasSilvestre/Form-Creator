import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class test {
    public static void main(String[] args) throws IOException {
        String line;
        String[] csvHeader=null;
        String filePath = "D:\\data.csv";
        Path path = Paths.get(filePath);
        ctc();
//        try{
//            BufferedReader br = new BufferedReader(new FileReader(filePath));
//            if((line=br.readLine())!=null){
//                csvHeader = line.split(",");
//            }
//            System.out.println();
//            while ((line=br.readLine())!=null){
//                String[] test = line.split(",");
//                for (String s: test) {
//                    System.out.println(s);
//                }
//            }
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
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
            System.out.println(record.get(1));
        }
    }

}
