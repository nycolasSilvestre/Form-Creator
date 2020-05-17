package datafileshandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CsvDataHandler {
    private String filePath;
    ArrayList<String[]>csvLines = new ArrayList<>();
    private String[] header,values;
    private HashMap<String,String> dataLinker;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<String[]> getCsvLines() {
        return csvLines;
    }

    public void setCsvLines(ArrayList<String[]> csvLines) {
        this.csvLines = csvLines;
    }

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public HashMap<String, String> getDataLinker() {
        return dataLinker;
    }

    public void setDataLinker(HashMap<String, String> dataLinker) {
        this.dataLinker = dataLinker;
    }

    public CsvDataHandler(File file){
        filePath=file.getPath();
        readCsv();
        setFieldMaps();
    }

    public void readCsv(){
        String line;
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((line=br.readLine())!=null){
                csvLines.add(line.split(","));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void setFieldMaps(){
        header = csvLines.get(0);
        values = csvLines.get(1);
        for (int i = 0; i < header.length;i++){
            String temp="";
            if(i<=values.length){
                temp=values[i];
            }
            dataLinker.put(header[i],temp);
        }
    }


}
