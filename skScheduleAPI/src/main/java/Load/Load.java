package Load;

import Specifikacija.SpecifikacijaRasporeda;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Load {
    public abstract void load(String path, String configpath, SpecifikacijaRasporeda specifikacijaRaspored) throws IOException;

    public static List<ConfigLoad> readConfig(String filePath) throws FileNotFoundException {
        List<ConfigLoad> mappings = new ArrayList<>();

        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splitLine = line.split(" ", 3);

            mappings.add(new ConfigLoad(Integer.valueOf(splitLine[0]), splitLine[1], splitLine[2]));
        }

        scanner.close();

        return mappings;
    }

}
