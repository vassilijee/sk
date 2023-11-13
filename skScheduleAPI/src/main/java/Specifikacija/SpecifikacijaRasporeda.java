package Specifikacija;

import Load.CSVLoad;
import Load.JSONLoad;
import Load.Load;
import Termin.Room;
import Termin.Termin;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Getter
@Setter
public abstract class SpecifikacijaRasporeda implements Specification {
    // KONSTRUKTOR ?
    private List<Termin> raspored = new ArrayList<>();
    private LocalTime radnoVremeOd;
    private LocalTime radnoVremeDo;
    private List<Room> sveSobe = new ArrayList<>();
    private LocalDate vaziOd;
    private LocalDate vaziDo;
    private List<LocalDate> neradniDani = new ArrayList<>();
    private DateTimeFormatter formatDatuma;

    /**
     * initRaspored sluzi za inicijalizaciju rasporeda.
     * Dodacemo path ka metapodacima, gde cemo odrediti vremenska ogranicenja naseg rasporeda.
     *
     * @param path Path ka metapodaci fajlu
     */
    @Override
    public void initRaspored(String path) {
        try {
            readMeta(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * addRoom sluzi da se dodaju prostori u metapodatke
     *
     * @param naziv     Naziv prostorije.
     * @param equipment Neki dodatci prostorije
     */
    @Override
    public void addRoom(String naziv, Map<String, String> equipment) {
        Room room = new Room(naziv, equipment);
        sveSobe.add(room);
    }

    /**
     * addTermin sluzi za dodavanje novog termina u raspored.
     *
     * @param podaci Podaci o temrinu koji se dodaje.
     */
    @Override
    public void addTermin(String podaci) {

    }

    /**
     * deleteTermin sluzi za brisanje zadatog termina iz rasporeda
     *
     * @param start    Podaci o pocetku termina koji ce se obrisati.
     * @param end      Podaci o kraju termina koji ce se obrisati.
     * @param ucionica Podaci o ucionici termina koji ce se obrisati.
     */

    @Override
    public void deleteTermin(String start, String end, String ucionica) {
        raspored.remove(nadjiTermin(start, end, ucionica));
    }

    /**
     * moveTermin sluzi za pomeranje termina.
     *
     * @param podaci Podaci o terminima koje zelimo da zamenimo.
     */
    @Override
    public void moveTermin(String podaci) {

    }

    /**
     * pretragaTermina sluzi da pretrazimo raspored po zadatom kriterijumu za slobodne ili zauzete termine.
     *
     * @param kriterijum kriterijum po kome zelimo da nadjemo termine
     * @param zauzetost  Zauzetost(slobodno ili zauzeto)
     * @return lista termina koji ispunjavaju zadati kriterijum
     */
    @Override
    public List<Termin> pretragaTermina(String kriterijum, boolean zauzetost) {
        return null;
    }

    /**
     * proveraZauzetosti sluzi da proverimo da li zadati termin zauzet.
     *
     * @param kriterijum Podaci u terminu
     * @return true ako je slobodan, false ako je zauzet
     */
    @Override
    public boolean provaraZauzetosti(String kriterijum) {
        return false;
    }

    /**
     * loadData sluzi za ucitavanje vec postojeceg rasporeda.
     *
     * @param path       Putanja ka fajlu
     * @param configPath Putanja ka konfiguracionom fajlu
     * @return ako je true vraca sadrzaj fajla, ako false ispisuje gresku
     */
    @Override
    public boolean loadData(String path, String configPath) {
        initRaspored("meta.txt");
        if (path.substring(path.indexOf('.') + 1).equals("csv")) {
            Load loader = new CSVLoad();
            try {
                loader.load(path, configPath, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else if (path.substring(path.indexOf('.') + 1).equals("json")) {
            Load loader = new JSONLoad();
            try {
                loader.load(path, configPath, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }

    /**
     * exportData sluzi za exportovanje rasporeda na zeljenu putanju.
     *
     * @param path Putanja ka destinicaji na koju zelimo da exportujemo raspored
     * @return ako je true vraca upisano, ako false ispisuje gresku
     */
    @Override
    public boolean exportData(String path) {
        return false;
    }

    @Override
    public String toString() {
        return "SpecifikacijaRasporeda{" + "radnoVremeOd=" + radnoVremeOd + ", radnoVremeDo=" + radnoVremeDo + ", sveSobe=" + sveSobe + ", vaziOd=" + vaziOd + ", vaziDo=" + vaziDo + ", neradniDani=" + neradniDani + '}';
    }

    public void readMeta(String filePath) throws FileNotFoundException {
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
                    String[] roomEquipment = line.split(";");
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

    @Override
    public List<Termin> pretragaTermina(String start, String end, String roomName, Map<String, String> additional,  String dayOfTheWeek) {
        return null;
    }

    public Room nadiSobuPoImenu(String naziv) {
        for (Room room : sveSobe) {
            if (room.getNaziv().equalsIgnoreCase(naziv)) return room;
        }
        return null;
    }

    public Termin nadjiTermin(String start, String end, String room) {
        for (Termin termin : raspored) {
            if (termin.getStart().equals(LocalDateTime.parse(start, formatDatuma))
                    && termin.getEnd().equals(LocalDateTime.parse(end, formatDatuma))
                    && termin.getRoom().equals(nadiSobuPoImenu(room)))
                return termin;
        }
        return null;
    }
}
