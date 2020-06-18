import java.io.*;

public class csv {
    private String fileName="D:\\Book1.csv";


    public void readCSV(){
        String line;
        String[] csvHeader=null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            if((line=br.readLine())!=null){
                csvHeader = line.split(",");
            }
            System.out.println("header :");
            for (String s:csvHeader) {
                System.out.print(s+" ");
            }
            System.out.println();
            while ((line=br.readLine())!=null){
                String[] test = line.split(",");
                for (String s: test) {
                    System.out.println(s);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


}
