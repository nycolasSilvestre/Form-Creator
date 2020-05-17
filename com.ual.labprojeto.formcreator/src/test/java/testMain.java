import java.util.HashMap;

public class testMain {
    public static void main(String[] args) {
//        csv a = new csv();
//        a.readCSV();
        fillFileds();
    }
    public static void fillFileds(){
        HashMap<String,String> test = new HashMap<>();
        test.put("tbCurso","Eng.Informática");
        test.put("tbAno","2020");
        for ( String s: test.keySet()) {
            System.out.println(test.get(s));

        }
    }
}
