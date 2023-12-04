import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Coordinateur {
    public static String read(String filepath) throws FileNotFoundException {
        try {
            return new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {
            throw new FileNotFoundException("Le fichier \"" + filepath + "\" n'existe pas");
        }
    }

    public static String[] split(String txt, int nbBlocks) {
        String[] lines = txt.split("\n");
        int linesPerBlock = (int)Math.ceil((double)lines.length / nbBlocks);
        String[] result = new String[nbBlocks];

        for (int i = 0; i < nbBlocks; i++) {
            int startIdx = i * linesPerBlock;
            int endIdx = (i == nbBlocks - 1) ? lines.length : (i + 1) * linesPerBlock;
            result[i] = String.join("\n", Arrays.copyOfRange(lines, startIdx, endIdx));
        }

        return result;
    }

    public static List<List<Map<String, Integer>>> maps(String[] blocks, int nbReducer) {
        List<List<Map<String, Integer>>> res = new ArrayList<>();
        for (String block : blocks) {
            System.out.println(CalculMap.countWord(block,nbReducer));
            res.add(CalculMap.countWord(block,nbReducer));
        }
        return res;
    }
}

