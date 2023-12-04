import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Coordinateur {
    public static void main(String args[]){
        try{
            // Le fichier d'entrée
            FileInputStream file = new FileInputStream("bible.txt");
            Scanner scanner = new Scanner(file);
            System.out.println(scanner.toString());

            scanner.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}

