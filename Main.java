import java.io.FileNotFoundException;
import java.util.Map;

public class Main {

    public static void main(String[] args){
        try {
            Map<String, Integer> result = new Coordinateur(10, 10).mapReduce("bible.txt");
            System.out.println("Final result ("+ result.size() +" words) :" + result);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
