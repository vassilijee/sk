import Specifikacija.SpecifikacijaRasporeda;
import Specifikacija.SpecifikacijaRasporedaManager;
import Termin.Room;
import Termin.Termin;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Implementation1 extends SpecifikacijaRasporeda {

    static {
        SpecifikacijaRasporedaManager.registerExporter(new Implementation1());
    }

    public Implementation1() {
        super();
    }


    @Override
    public void addTermin(String start, String end, String roomNaziv, Map<String, String> additional) {
        dodajNoviTermin(start, end, roomNaziv, additional);
    }

    //  https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap
    public void dodajNoviTermin(String start, String end, String roomNaziv, Map<String, String> additional) {
        LocalDateTime startDateTime = LocalDateTime.parse(start, getFormatDatuma());
        LocalDateTime endDateTime = LocalDateTime.parse(end, getFormatDatuma());
        boolean flagPostojiSoba = true;

        for (Room room : getSveSobe()) {
            if (room.getNaziv().equals(roomNaziv)) {
                flagPostojiSoba = false;
            }
        }

        if (getNeradniDani().contains(startDateTime.toLocalDate()) || getNeradniDani().contains(endDateTime.toLocalDate()) || startDateTime.toLocalTime().isAfter(getRadnoVremeDo()) || endDateTime.toLocalTime().isAfter(getRadnoVremeDo()) || startDateTime.toLocalTime().isBefore(getRadnoVremeOd()) || flagPostojiSoba) {
            System.out.println("Termin je u nedozvoljeno vreme ili u nepostojecoj sobi. ");
            System.out.println(flagPostojiSoba);

        } else {
            Termin tmpTermin = new Termin();
            Room tmpRoom = new Room(roomNaziv);
            // provera da li postoji termin sa istim dataTime range i nazivom prostorije
            tmpTermin.setStart(LocalDateTime.parse(start, getFormatDatuma()));
            tmpTermin.setEnd(LocalDateTime.parse(end, getFormatDatuma()));
            tmpTermin.setRoom(tmpRoom);

            if (proveraPreklapanjaTermina(tmpTermin) != null) {
                System.out.println("U rasporedu postoji termin: " + proveraPreklapanjaTermina(tmpTermin));
            }

            proveraPostojiLiZadataSoba(roomNaziv);
            // zamena soba, postavljanje prave instance sobe pod takvim imenom (zbog equipmenta)
            for (Room room : getSveSobe()) {
                if (room.equals(tmpRoom)) tmpTermin.setRoom(room);
            }

            tmpTermin.setAdditional(additional);
            getRaspored().add(tmpTermin);

            System.out.println("Dodat je novi termin: \n" + tmpTermin);
        }
    }

    private boolean proveraPostojiLiZadataSoba(String roomNaziv) {
        boolean flag = false;
        for (Room room : getSveSobe()) {
            if (room.getNaziv().equals(roomNaziv)) {
                return true;
            }
        }
        if (!flag) {
            System.out.println("Prostor pod takvim nazivom ne postoji. ");
            return false;
        }
        return true;
    }

    private Termin proveraPreklapanjaTermina(Termin termin) {
        LocalDateTime maxOdStarta;
        LocalDateTime minOdEnda;
        for (Termin terminIzRasporeda : getRaspored()) {
            maxOdStarta = termin.getStart().isAfter(terminIzRasporeda.getStart()) ? termin.getStart() : terminIzRasporeda.getStart();
            minOdEnda = termin.getEnd().isBefore(terminIzRasporeda.getEnd()) ? termin.getEnd() : terminIzRasporeda.getEnd();
            if (maxOdStarta.isBefore(minOdEnda) && terminIzRasporeda.getRoom().getNaziv().equals(termin.getRoom().getNaziv())) {
                return terminIzRasporeda;
            }
        }
        return null;
    }

    @Override
    public boolean exportData(String s) {
        if (s.endsWith(".txt")) {
            File txtFile = new File(s);
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(txtFile);
                for (Termin termin : getRaspored()) {
                    StringBuilder line = new StringBuilder();
                    line.append(termin);
                    fileWriter.write(line.toString());
                }
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (s.endsWith(".csv")) {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(s);
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);

                for (Termin termin : getRaspored()) {
                    csvPrinter.printRecord(termin.getStart().toString(), termin.getEnd().toString(), termin.getRoom().getNaziv());
                }
                csvPrinter.close();
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (s.endsWith(".json")) {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                FileWriter fileWriter = new FileWriter(s);

                mapper.registerModule(new JavaTimeModule());
                mapper.writeValue(out, getRaspored());


                byte[] data = out.toByteArray();
                fileWriter.write(new String(data));
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (s.endsWith(".pdf")) {
//            try {
//                PDDocument document = new PDDocument();
//                PDPage page = new PDPage();
//                document.addPage(page);
//                PDPageContentStream contentStream = new PDPageContentStream(document, page);
//                contentStream.beginText();
//                contentStream.setFont(PDType1Font.HELVETICA, 12);
//                contentStream.setLeading(14.5f);
//                contentStream.newLineAtOffset(50, 750); // Set the position
//                for (Termin termin : getRaspored()) {
//                    contentStream.showText(pdf(termin));
//                    contentStream.newLine();
//                }
//                contentStream.endText();
//                contentStream.close();
//                document.save(s);
//                document.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return true;
    }

    public String pdf(Termin termin) {
        return termin.getStart().format(DateTimeFormatter.ofPattern("HH:mm")) + "-" + termin.getEnd().format(DateTimeFormatter.ofPattern("HH:mm")) + ", " + termin.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ", " + termin.getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ", " + termin.getRoom().getNaziv() + ", " + ispisiAdditional(termin.getAdditional());
    }

    public String ispisiAdditional(Map<String, String> additional) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : additional.entrySet()) {
            sb.append(entry.getValue() + ", ");
        }
        if (sb.length() == 0) return "";
        else return sb.substring(0, sb.length() - 2);
    }

    @Override
    public void moveTermin(String podaci) {
        String terminZaBrisanje = StringUtils.substringBefore(podaci, "|");
        String startOdZaBrisanje;
        String endOdZaBrisanje;
        String roomNazivOdZaBrisanje;

        List<String> podaciListZaBrisanje;
        podaciListZaBrisanje = Arrays.asList(terminZaBrisanje.split(";", 4));
        startOdZaBrisanje = StringUtils.substringBefore(podaciListZaBrisanje.get(0), " -");
        endOdZaBrisanje = StringUtils.substringAfter(podaciListZaBrisanje.get(0), "- ");
        roomNazivOdZaBrisanje = podaciListZaBrisanje.get(1).trim();

        String terminZeljeniNovi = StringUtils.substringAfter(podaci, "|");
        String startOdZeljeniNovi;
        String endOdZeljeniNovi;
        String roomNazivOdZeljeniNovi;

        List<String> podaciListZaNovi;
        podaciListZaNovi = Arrays.asList(terminZeljeniNovi.split(";", 4));
        startOdZeljeniNovi = StringUtils.substringBefore(podaciListZaNovi.get(0), " -");
        endOdZeljeniNovi = StringUtils.substringAfter(podaciListZaNovi.get(0), "- ");
        roomNazivOdZeljeniNovi = podaciListZaNovi.get(1).trim();

        menjajTermine(startOdZaBrisanje, endOdZaBrisanje, roomNazivOdZaBrisanje, startOdZeljeniNovi, endOdZeljeniNovi, roomNazivOdZeljeniNovi);
    }

    private boolean menjajTermine(String startOdZaBrisanje, String endOdZaBrisanje, String roomNazivOdZaBrisanje, String startOdZeljeniNovi, String endOdZeljeniNovi, String roomNazivOdZeljeniNovi) {
        Termin tmpTerminNovi = new Termin();
        Room tmpRoomNovi = new Room(roomNazivOdZeljeniNovi);
        tmpTerminNovi.setStart(LocalDateTime.parse(startOdZeljeniNovi, getFormatDatuma()));
        tmpTerminNovi.setEnd(LocalDateTime.parse(endOdZeljeniNovi, getFormatDatuma()));
        tmpTerminNovi.setRoom(tmpRoomNovi);

        if (proveraPreklapanjaTermina(tmpTerminNovi) != null) {
            System.out.println("U rasporedu ec postoji termin: " + proveraPreklapanjaTermina(tmpTerminNovi));
            return false;
        }

        if (proveraPostojiLiZadataSoba(roomNazivOdZeljeniNovi)) {
            System.out.println("Ne postoji zadata soba za novi termin. ");
        }

        Termin tmpTerminZaBrisanje = new Termin();
        Room tmpRoomzaBrisanje = new Room(roomNazivOdZaBrisanje);
        tmpTerminZaBrisanje.setStart(LocalDateTime.parse(startOdZaBrisanje, getFormatDatuma()));
        tmpTerminZaBrisanje.setEnd(LocalDateTime.parse(endOdZaBrisanje, getFormatDatuma()));
        tmpTerminZaBrisanje.setRoom(tmpRoomNovi);

        for (Room room : getSveSobe()) {
            if (room.equals(tmpRoomzaBrisanje)) tmpTerminZaBrisanje.setRoom(room);
        }

        //additional se prebacuje sa starog termina
        Map<String, String> additionalOdStarog = new HashMap<>();
        for (Termin terminIzRasporeda : getRaspored()) {
            additionalOdStarog = terminIzRasporeda.getAdditional();
        }
        System.out.println("MENJANJE\nMenja se: \n" + tmpTerminZaBrisanje + "\nza: \n" + tmpTerminNovi);

        deleteTermin(startOdZaBrisanje, endOdZaBrisanje, roomNazivOdZaBrisanje);
        dodajNoviTermin(startOdZeljeniNovi, endOdZeljeniNovi, roomNazivOdZeljeniNovi, additionalOdStarog);

        return true;
    }

    @Override
    public List<Termin> pretragaTermina(String startDate, String endDate, String startTime, String endTime, String roomName, Map<String, String> additional, String dayOfTheWeek) {
        DateTimeFormatter vremeFormat = DateTimeFormatter.ofPattern(StringUtils.substringAfter(getFormatDatumaAsString(), " "));
        DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern(StringUtils.substringBefore(getFormatDatumaAsString(), " "));

        LocalDate startD = null, endD = null;
        LocalTime startT = null, endT = null;

        if (startDate != null) startD = LocalDate.parse(startDate, datumFormat);
        if (endDate != null) endD = LocalDate.parse(endDate, datumFormat);
        if (startTime != null) startT = LocalTime.parse(startTime, vremeFormat);
        if (endTime != null) endT = LocalTime.parse(endTime, vremeFormat);

        List<Termin> presekOdgovarajucihTermina = new ArrayList<>();
        List<Termin> terminiOdgovarajuStartDate = null;
        List<Termin> terminiOdgovarajuEndDate = null;
        List<Termin> terminiOdgovarajuStartTime = null;
        List<Termin> terminiOdgovarajuEndTime = null;
        List<Termin> terminiOdgovarajuRoomName = null;
        List<Termin> terminiOdgovarajuAdditional = null;
        List<Termin> terminiOdgovarajuDayOfTheWeek = null;

        // pravi se lista lista, u koju se smestaju samo liste za koje smo prosledili parametar razlicit od null
        // ne mozemo da dodamo u listsToIntersect sve liste jer kada bi neki parametar bio null, ne bi postojala vracena vrednost za taj parametar pa bi ceo presek bio null
        // moraju se dodati liste za koje smo prosledili neki parametar, jer ukoliko u pretrazi dodje do toga da ne postoji ni jedan termin sa tim parametrom, to jest lista za taj parametar ostane prazna, onda je citav presek prazan
        List<List<Termin>> listsToIntersect = new ArrayList<>();

        if (startDate != null) {
            terminiOdgovarajuStartDate = new ArrayList<>();
            listsToIntersect.add(terminiOdgovarajuStartDate);
        }
        if (endDate != null) {
            terminiOdgovarajuEndDate = new ArrayList<>();
            listsToIntersect.add(terminiOdgovarajuEndDate);
        }
        if (startTime != null) {
            terminiOdgovarajuStartTime = new ArrayList<>();
            listsToIntersect.add(terminiOdgovarajuStartTime);
        }
        if (endTime != null) {
            terminiOdgovarajuEndTime = new ArrayList<>();
            listsToIntersect.add(terminiOdgovarajuEndTime);
        }
        if (roomName != null) {
            terminiOdgovarajuRoomName = new ArrayList<>();
            listsToIntersect.add(terminiOdgovarajuRoomName);
        }
        if (additional != null) {
            terminiOdgovarajuAdditional = new ArrayList<>();
            listsToIntersect.add(terminiOdgovarajuAdditional);
        }
        if (dayOfTheWeek != null) {
            terminiOdgovarajuDayOfTheWeek = new ArrayList<>();
            listsToIntersect.add(terminiOdgovarajuDayOfTheWeek);
        }


        for (Termin termin : getRaspored()) {
            if (startDate != null && !terminiOdgovarajuStartDate.contains(termin) && termin.getStart().toLocalDate().equals(startD)) {
                terminiOdgovarajuStartDate.add(termin);
            }
            if (endDate != null && !terminiOdgovarajuEndDate.contains(termin) && termin.getEnd().toLocalDate().equals(endD)) {
                terminiOdgovarajuEndDate.add(termin);
            }
            if (startTime != null && !terminiOdgovarajuStartTime.contains(termin) && termin.getStart().toLocalTime().equals(startT)) {
                terminiOdgovarajuStartTime.add(termin);
            }
            if (endTime != null && !terminiOdgovarajuEndTime.contains(termin) && termin.getEnd().toLocalTime().equals(endT)) {
                terminiOdgovarajuEndTime.add(termin);
            }
            if (roomName != null && !terminiOdgovarajuRoomName.contains(termin) && termin.getRoom().getNaziv().equals(roomName)) {
                terminiOdgovarajuRoomName.add(termin);
            }
            if (additional != null && !terminiOdgovarajuAdditional.contains(termin)) {
                boolean allEntriesMatch = true;
                // prolazi kroz listu prosledjenih additionala, jedan po jedan additional
                for (Map.Entry<String, String> oneAdditional : additional.entrySet()) {
                    // ako u tom terminu ne postoji additional sa takvim kljucem
                    // ili
                    // ne postoji kljuc koji ima takvu vrednost
                    if (!termin.getAdditional().containsKey(oneAdditional.getKey()) || !termin.getAdditional().get(oneAdditional.getKey()).equals(oneAdditional.getValue())) {
                        // If any entry doesn't match, set the flag to false
                        allEntriesMatch = false;
                        break;
                    }
                }
                // If all entries in the additional map match, add the termin to the list
                if (allEntriesMatch) {
                    terminiOdgovarajuAdditional.add(termin);
                }
            }
            if (dayOfTheWeek != null && !terminiOdgovarajuDayOfTheWeek.contains(termin) && nadjiDan(termin.getStart().getDayOfWeek().toString()).equals(dayOfTheWeek)) {
                terminiOdgovarajuDayOfTheWeek.add(termin);
            }
        }


        // Perform intersection only if there are non-null lists
        if (!listsToIntersect.isEmpty()) {
            // rucno dodajemo prvu listu iz liste lista
            // ovo moramo da uradimo da bi kasnije funkcija retainAll mogla da radi
            // retainAll u listi nad kojom se zove, ostavlja samo elemente koji postoje i u listi koju smo prosledili kao parametar
            // ako bismo pozvali retainAll nad praznom listom, nista ne bi moglo da se doda, jer bi presek uvek bio prazans skup
            presekOdgovarajucihTermina.addAll(listsToIntersect.get(0));
            for (List<Termin> termins : listsToIntersect.subList(1, listsToIntersect.size())) {
                presekOdgovarajucihTermina.retainAll(termins);
            }
        }

        return presekOdgovarajucihTermina;
    }

    public String nadjiDan(String dan) {
        if (dan.equalsIgnoreCase("monday")) {
            return "ponedeljak";
        } else if (dan.equalsIgnoreCase("tuesday")) {
            return "utorak";
        } else if (dan.equalsIgnoreCase("wednesday")) {
            return "sreda";
        } else if (dan.equalsIgnoreCase("thursday")) {
            return "cetvrtak";
        } else if (dan.equalsIgnoreCase("friday")) {
            return "petak";
        } else if (dan.equalsIgnoreCase("saturday")) {
            return "subota";
        } else {
            return "nedelja";
        }
    }

    @Override
    public boolean provaraZauzetostiUcionice(String naziv, String start, String end) {
        LocalDateTime startd = LocalDateTime.parse(start, getFormatDatuma());
        LocalDateTime endd = LocalDateTime.parse(end, getFormatDatuma());
        for (Termin t : getRaspored()) {
            if (t.getRoom().getNaziv().equalsIgnoreCase(naziv) && t.getStart().equals(startd) && t.getEnd().equals(endd))
                return true;
            if (t.getRoom().getNaziv().equalsIgnoreCase(naziv) && t.getEnd().isAfter(startd) && t.getEnd().isBefore(endd))
                return true;
            if (t.getRoom().getNaziv().equalsIgnoreCase(naziv) && t.getStart().isBefore(endd) && t.getStart().isAfter(startd))
                return true;
            if (t.getRoom().getNaziv().equalsIgnoreCase(naziv) && t.getStart().isAfter(startd) && t.getEnd().isBefore(endd))
                return true;
            if (t.getRoom().getNaziv().equalsIgnoreCase(naziv) && t.getStart().isBefore(startd) && t.getEnd().isAfter(endd))
                return true;
        }
        return false;
    }
}

