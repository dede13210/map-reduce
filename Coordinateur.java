import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Coordinateur {
    public static String read(String filepath) throws FileNotFoundException {
        try {
            return new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {
            throw new FileNotFoundException("Le fichier \"" + filepath + "\" n'existe pas");
        }
    }
}

