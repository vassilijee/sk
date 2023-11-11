package impl.csv;

import Specifikacija.SpecifikacijaRasporeda;
import Termin.Room;
import Termin.Termin;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ScheduleImportExportCSV extends SpecifikacijaRasporeda {

    @Override
    public void initRaspored(String path) {
        System.out.println("CITANJE METAPODATAKA" + '\n');
        try {
            readMeta(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addRoom(String naziv, Map<String, String> equipment) {
        dodajSobu(naziv, equipment);
    }

    private void dodajSobu(String naziv, Map<String, String> equipment) {
        for (Room room : getSveSobe()) {
            if (room.getNaziv().equals(naziv) ){
                System.out.println("Soba pod tim imeno vec postoji. ");
                return;
            }
        }
        Room newRoom = new Room();
        newRoom.setNaziv(naziv);
        newRoom.setEquipment(equipment);
        getSveSobe().add(newRoom);
        System.out.println(getSveSobe());
    }

    @Override
    public void addTermin(String podaci) {
        for (Room room : getSveSobe()) {
            if (room.getNaziv().equals("ASDASD")) {
                //termin.setRoom(room);
            }
        }
    }

    @Override
    public void deleteTermin(String podaci) {

    }

    @Override
    public List<Termin> pretragaTermina(String kriterijum, boolean zauzetost) {
        return null;
    }

    @Override
    public boolean provaraZauzetosti(String kriterijum) {
        return false;
    }

    @Override
    public boolean loadData(String path, String configPath) {
        try {
            loadApache(path, configPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean exportData(String s) {
        return false;
    }

    @Override
    public void moveTermin(String podaci) {
        super.moveTermin(podaci);
    }

    public void loadApache(String filePath, String configPath) throws IOException {
        List<ConfigLoad> columnLoading = readConfig(configPath);
        Map<Integer, String> mappings = new HashMap<>();
        for (ConfigLoad configMapping : columnLoading) {
            mappings.put(configMapping.getIndex(), configMapping.getPoSpec());
        }

        System.out.println("Config mapiranje: ");
        System.out.println(columnLoading);
        System.out.println(mappings);

        FileReader fileReader = new FileReader(filePath);
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(mappings.get(-1));

        for (CSVRecord record : parser) {
            Termin termin = new Termin();
            for (ConfigLoad entry : columnLoading) {
                int columnIndex = entry.getIndex();

                if (columnIndex == -1) continue;

                String columnName = entry.getPoHead();

                switch (mappings.get(columnIndex)) {
                    case "place":
                        for (Room room : getSveSobe()) {
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
            getRaspored().add(termin);
        }
    }

    private static List<ConfigLoad> readConfig(String filePath) throws FileNotFoundException {
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

    private void readMeta(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("TERMIN_RADNO_VREME")) {
                line = StringUtils.substringAfter(line, "TERMIN_RADNO_VREME ");
                // morao sam da sklonim static iz readMeta zbog setRadnoVreme od?
                setRadnoVremeOd(LocalTime.parse(StringUtils.substringBefore(line, " -")));
                setRadnoVremeDo(LocalTime.parse(StringUtils.substringAfter(line, "- ")));
            } else if (line.startsWith("ROOM_SVE")) {
                line = StringUtils.substringAfter(line, "ROOM_SVE");
                List<String> roomList = List.of(line.split(", "));
                for (String roomName : roomList) {
                    getSveSobe().add(new Room(roomName.trim()));
                }
            } else if (line.startsWith("TERMIN_VAZI_OD_DO")) {
                line = StringUtils.substringAfter(line, "TERMIN_VAZI_OD_DO ");
                setVaziOd(LocalDate.parse(StringUtils.substringBefore(line, " -").trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                setVaziDo(LocalDate.parse(StringUtils.substringAfter(line, "- ").trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            } else if (line.startsWith("TERMIN_NERADNI_DANI")) {
                line = StringUtils.substringAfter(line, "TERMIN_NERADNI_DANI");
                List<String> neradniList = List.of(line.split(", "));
                for (String neradniDateStr : neradniList) {
                    getNeradniDani().add(LocalDate.parse(neradniDateStr.trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                }
            } else if (line.startsWith("ROOM_EQUIPMENT_BEGINNING")) {
                Room tmpRoom = new Room();
                int index = 0;
                String equipmentName = "";
                String equipmentVal = "";
                line = scanner.nextLine();
                while (!line.equals("ROOM_EQUIPMENT_ENDING")) {
                    tmpRoom.setNaziv(StringUtils.substringBetween(line, "-", "="));
                    line = StringUtils.substringAfter(line, "= ");
                    List<String> roomEquipment = Arrays.asList(line.split(";"));
                    for (Room room : getSveSobe()) {
                        if (room.equals(tmpRoom)) {
                            index = getSveSobe().indexOf(room);
                        }
                    }
                    for (String equipment : roomEquipment) {
                        equipmentName = StringUtils.substringBefore(equipment, ":");
                        equipmentVal = StringUtils.substringAfter(equipment, ":");
                        getSveSobe().get(index).getEquipment().put(equipmentName, equipmentVal);
                    }
                    line = scanner.nextLine();
                }
            }
        }
        scanner.close();
    }
}

