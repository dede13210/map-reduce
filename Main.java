import java.io.FileNotFoundException;

public class Main {

    public static void main(String args[]){
        String txt;
        try {
            txt = Coordinateur.read("bible.txt");
            System.out.println(txt);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
