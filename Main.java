import java.io.FileNotFoundException;
public class Main {
    public static int nbMap = 231;
    public static void main(String[] args){
        String txt;
        try {
            txt = Coordinateur.read("bible.txt");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        String[] blocks = Coordinateur.split(txt, nbMap);
    }
}
