package Load;

import Specifikacija.SpecifikacijaRasporeda;
import Termin.Room;
import Termin.Termin;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVLoad extends Load {

    @Override
    public void load(String path, String configPath, SpecifikacijaRasporeda specifikacijaRaspored) throws IOException {
        List<ConfigLoad> columnLoading = readConfig(configPath);
        Map<Integer, String> mappings = new HashMap<>();
        for (ConfigLoad configMapping : columnLoading) {
            mappings.put(configMapping.getIndex(), configMapping.getPoSpec());
        }
        System.out.println(columnLoading);
        System.out.println(mappings);
        FileReader fileReader = new FileReader(path);
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(mappings.get(-1));
        specifikacijaRaspored.setFormatDatuma(formatter);

        for (CSVRecord record : parser) {
            Termin termin = new Termin();
            for (ConfigLoad entry : columnLoading) {
                int columnIndex = entry.getIndex();
                if (columnIndex == -1) continue;

                String columnName = entry.getPoHead();
                switch (mappings.get(columnIndex)) {
                    case "place":
                        for (Room room : specifikacijaRaspored.getSveSobe()) {
                            if (room.getNaziv().equals(record.get(columnIndex))) {
                                termin.setRoom(room);
                            }
                        }
                        break;
                    case "start":
                        LocalDateTime startDateTime = LocalDateTime.parse(record.get(columnIndex), formatter);
                        termin.setStart(startDateTime);
                        break;
                    case "end":
                        LocalDateTime endDateTime = LocalDateTime.parse(record.get(columnIndex), formatter);
                        termin.setEnd(endDateTime);
                        break;
                    case "additional":
                        termin.getAdditional().put(columnName, record.get(columnIndex));
                        break;
                }
            }
            specifikacijaRaspored.getRaspored().add(termin);
        }
    }
}
