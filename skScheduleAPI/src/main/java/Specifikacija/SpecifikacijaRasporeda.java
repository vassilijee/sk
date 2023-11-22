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
    protected String implementationName;
    private List<Termin> raspored = new ArrayList<>();
    private LocalTime radnoVremeOd;
    private LocalTime radnoVremeDo;
    private List<Room> sveSobe = new ArrayList<>();
    private LocalDate vaziOd;
    private LocalDate vaziDo;
    private List<LocalDate> neradniDani = new ArrayList<>();
    private DateTimeFormatter formatDatuma;
    private String formatDatumaAsString;

    public SpecifikacijaRasporeda() {
    }

//    public List<Termin> getRaspored() {
//        if (raspored == null) raspored = new ArrayList<>();
//        return raspored;
//    }


    /**
     * initRaspored sluzi za inicijalizaciju rasporeda i popunjavanje osnovnih podataka.
     * Dodacemo path ka metapodacima, gde cemo odrediti vremenska ogranicenja naseg rasporeda, koje prostorije postoje, neradni dani itd.
     * Ova metoda se mora pozvati pre bilo kakve manipulacije sa rasporedom.
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
     * addRoom sluzi da se dodaju prostori u metapodatke i u samu listu svih prostorija
     *
     * @param naziv     Naziv prostorije.
     * @param equipment Mapa dodataka vezano za prosorije gde je kljuc opisuje naziv tog dodatka a vrednost je vrednost tog dodatka
     * @return Ukoliko se vec postoji prostorija sa tim nazivom vraca -1 a ukoliko jeste 1
     */
    @Override
    public int addRoom(String naziv, Map<String, String> equipment) {
        Room room = new Room(naziv, equipment);
        if(!sveSobe.contains(room))
            return -1;
        sveSobe.add(room);
        return 1;
    }


    /**
     * addTermin sluzi za dodavanje novog termina u raspored.
     *
     * @param start      Podaci o pocetku termina koji se dodaje u formatu zadatom u config fajlu.
     * @param end        Podaci o kraju termina koji se dodaje u formatu zadatom u config fajli.
     * @param ucionica   Naziv prostorije termina koji se dodaje.
     * @param additional Mapa dodataka vezano za prosorije gde je kljuc opisuje naziv tog dodatka a vrednost je vrednost tog dodatka
     * @return Ukoliko se termin nije dodao zbog nekog preklapanja ili nedozvoljenog datuma ili nepostojece ucuionice vraca -1 a ukolio se sve lepo dodalo vraca 1
     */
    @Override
    public int addTermin(String start, String end, String ucionica, Map<String, String> additional) {
        return -1;
    }

    /**
     * deleteTermin sluzi za brisanje zadatog termina iz rasporeda
     *
     * @param start    Podaci o pocetku termina koji ce se obrisati u formatu zadatom iz config fajla.
     * @param end      Podaci o kraju termina koji ce se obrisati u formatu zadatom iz config fajla.
     * @param ucionica Naziv prostorije termina koji ce se obrisati.
     * @return Vraca -1 ukoliko nije obrisao a 1 ukoliko jeste
     */
    @Override
    public int deleteTermin(String start, String end, String ucionica) {
        if(nadjiTermin(start, end, ucionica) == null)
            return -1;
        raspored.remove(nadjiTermin(start, end, ucionica));
        return 1;
    }

    /**
     * moveTermin sluzi za pomeranje termina.
     *
     * @param podaci Podaci o terminima koje zelimo da promenimo u formatu 10/01/2023 13:15 - 12/01/2023 15:00; RAF3; petak(ukoliko je grupisajuci raspored uniste dan)|10/01/2023 11:15 - 12/01/2023 13:00; RAF6; sreda(ukoliko je grupisajuci raspored uniste dan).
     * @return Vraca -1 ukoliko termin nije pomeren a 1 ukoliko jeste
     */
    @Override
    public int moveTermin(String podaci) {
        return -1;
    }

    /**
     * pretragaTermina sluzi da pretrazimo raspored po zadatom kriterijumu za slobodne ili zauzete termine.
     * @param start Datum pocetka termina u formatu zadatom u config fajlu
     * @param end Datum pocetka zeljenog termina u formatu zadatom u config fajlu
     * @param vremeod Vremenski pocetak zeljenog termina u formatu zadatom u config fajlu
     * @param vremedo Vremenski kraj zeljenog termina u formatu zadatom u config fajlu
     * @param roomName Naziv prostorije u kojoj se odrzava zeljeni termin
     * @param additional Mapa dodataka vezano za termin gde je kljuc opisuje naziv tog dodatka a vrednost je vrednost tog dodatka po kojima zelite da potrazite odredjeni termin
     * @param dayOfTheWeek Dan zeljenog termina
     * @return lista termina koji ispunjavaju zadati kriterijum
     */

    @Override
    public List<Termin> pretragaTermina(String start, String end, String vremeod, String vremedo, String roomName, Map<String, String> additional, String dayOfTheWeek) {
        return null;
    }

    /**
     * proveraZauzetostiUcionice sluzi da proverimo da li je zadata ucionica zauzeta odredjenog datuma u odredjenom terminu.
     *
     * @param naziv Naziv trazene ucinice
     * @param start Datum i vreme pocetka zeljenog termina u fortamtu zadatom u config fajlu
     * @param end   Datum i vreme zavrsetka zeljenog termina u formtatu zadatom u config fajlu
     * @return true ako je zauzeta, false ako je slobodna
     */
    @Override
    public boolean provaraZauzetostiUcionice(String naziv, String start, String end) {
        return true;
    }

    /**
     * loadData sluzi za ucitavanje vec postojeceg rasporeda.
     *
     * @param path       Putanja ka fajlu
     * @param configPath Putanja ka konfiguracionom fajlu
     * @return Vraca true ako se fajl uspesno ucitao i false ukoliko nije
     */
    @Override
    public boolean loadData(String path, String configPath) {
        //mora u load jos meta da ide ili da se uvek pozove init pre load
        //initRaspored("meta.txt");
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
     * @return Vraca true ako se fajl uspesno sacuvao i false ukoliko nije
     */
    @Override
    public boolean exportData(String path) {
        return false;
    }

    @Override
    public String toString() {
        return "SpecifikacijaRasporeda{" + "radnoVremeOd=" + radnoVremeOd + ", radnoVremeDo=" + radnoVremeDo + ", sveSobe=" + sveSobe + ", vaziOd=" + vaziOd + ", vaziDo=" + vaziDo + ", neradniDani=" + neradniDani + '}';
    }
    /**
     * readMeta sluzi za ucitavanje osnovnih podataka vezano za raspored kao sto je vremensko
     * trajanje rasporeda, za koje prostorije vazi, kakve dodtake imaju prostorije i u principu
     * svi podaci bez kojih raspored ne bi mogao da bude napravljen.
     *
     * @param filePath Putanja ka destinicaji iz koje zelimo da ucitamo podatke
     */
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
    /**
     * nadiSobuPoImenu sluzi za nalazenje tacnog objeka Room klase koji ima takav naziv
     *
     * @param naziv Naziv trazene prostorije
     * @return Room objekat ukoliko ga je pronasao ili null ukoliko nije
     */
    public Room nadiSobuPoImenu(String naziv) {
        for (Room room : sveSobe) {
            if (room.getNaziv().equalsIgnoreCase(naziv)) return room;
        }
        return null;
    }
    /**
     * ispisRasporeda sluzi za ispisivanje liste termina u string
     *
     * @param raspored Lista termina koju zelimo da ispisemo
     * @return Vraca podatke pretvorene u string
     */
    public String ispisRasporeda(List<Termin> raspored) {
        return raspored.toString();
    }
    /**
     * nadjiTermin sluzi za nalazenje tacnog objeka Termin klase koji ima zadati pocetak, kraj i prostoriju
     *
     * @param start Datum i vreme kada pocinje termin
     * @param end Datum i vreme kada se zavrsava termin
     * @param room Naziv prostorje u kojoj se odrzava termin
     * @return Vraca pronadjen termin ukoliko postoji ili null ukoliko ne postoji
     */
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
