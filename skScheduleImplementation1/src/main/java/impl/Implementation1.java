package impl;

import Specifikacija.SpecifikacijaRasporeda;
import Termin.Room;
import Termin.Termin;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Implementation1 extends SpecifikacijaRasporeda {

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
    public void addTermin(String podaci) {
        String start;
        String end;
        String roomNaziv;
        Map<String, String> additional = new HashMap<>();

        List<String> podaciList;
        podaciList = Arrays.asList(podaci.split(";", 4));

        start = StringUtils.substringBefore(podaciList.get(0), " -");
        end = StringUtils.substringAfter(podaciList.get(0), "- ");

        roomNaziv = podaciList.get(1).trim();

        for (int i = 2; i < podaciList.size(); i++) {
            additional.put(StringUtils.substringBefore(podaciList.get(i).trim(), ":"), StringUtils.substringAfter(podaciList.get(i).trim(), ":"));
        }


        dodajNoviTermin(start, end, roomNaziv, additional);
    }

    //  https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap
    public void dodajNoviTermin(String start, String end, String roomNaziv, Map<String, String> additional) {
        Termin tmpTermin = new Termin();
        Room tmpRoom = new Room(roomNaziv);
        // provera da li postoji termin sa istim dataTime range i nazivom prostorije
        tmpTermin.setStart(LocalDateTime.parse(start, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        tmpTermin.setEnd(LocalDateTime.parse(end, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
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
                //System.out.println("U rasporedu postoji termin: " + terminIzRasporeda);
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
                    csvPrinter.printRecord(
                            termin.getStart().toString(),
                            termin.getEnd().toString(),
                            termin.getRoom().getNaziv()
                    );
//                    for (Map.Entry<String, String> oneAdditional : termin.getAdditional().entrySet()) {
//                        csvPrinter.print(oneAdditional.getValue());
//                    }
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


            //System.out.println(new String(data));
        }
        return true;
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

    private boolean menjajTermine(String startOdZaBrisanje,
                                  String endOdZaBrisanje,
                                  String roomNazivOdZaBrisanje,
                                  String startOdZeljeniNovi,
                                  String endOdZeljeniNovi,
                                  String roomNazivOdZeljeniNovi) {
        Termin tmpTerminNovi = new Termin();
        Room tmpRoomNovi = new Room(roomNazivOdZeljeniNovi);
        tmpTerminNovi.setStart(LocalDateTime.parse(startOdZeljeniNovi, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        tmpTerminNovi.setEnd(LocalDateTime.parse(endOdZeljeniNovi, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
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

        for (Room room :
                getSveSobe()) {
            if (room.equals(tmpRoomzaBrisanje))
                tmpTerminZaBrisanje.setRoom(room);
        }

        //additional se prebacuje sa starog termina
        Map<String, String> additionalOdStarog = new HashMap<>();
        for (Termin terminIzRasporeda :
                getRaspored()) {
            additionalOdStarog = terminIzRasporeda.getAdditional();
        }
        System.out.println("MENJANJE\nMenja se: \n" + tmpTerminZaBrisanje + "\nza: \n" + tmpTerminNovi);

        deleteTermin(startOdZaBrisanje, endOdZaBrisanje, roomNazivOdZaBrisanje);
        dodajNoviTermin(startOdZeljeniNovi, endOdZeljeniNovi, roomNazivOdZeljeniNovi, additionalOdStarog);

        return true;
    }

    @Override
    public List<Termin> pretragaTermina(String kriterijum, boolean zauzetost) {
        return null;
    }

    @Override
    public List<Termin> pretragaTermina(String start, String end, String roomName, Map<String, String> additional, String dayOfTheWeek) {
        List<Termin> presekOdgovarajucihTermina = new ArrayList<>();
        List<Termin> terminiOdgovarajuStart = new ArrayList<>();
        List<Termin> terminiOdgovarajuEnd = new ArrayList<>();
        List<Termin> terminiOdgovarajuRoomName = new ArrayList<>();
        List<Termin> terminiOdgovarajuAdditional = new ArrayList<>();
        List<Termin> terminiOdgovarajuDayOfTheWeek = new ArrayList<>();

        for (Termin termin :
                getRaspored()) {
            if (start != null &&
                    !terminiOdgovarajuStart.contains(termin) &&
                    termin.getStart().equals(LocalDateTime.parse(start, getFormatDatuma()))) {
                terminiOdgovarajuStart.add(termin);
            }
            if (end != null &&
                    !terminiOdgovarajuEnd.contains(termin) &&
                    termin.getEnd().equals(LocalDateTime.parse(end, getFormatDatuma()))) {
                terminiOdgovarajuEnd.add(termin);
            }
            if (roomName != null &&
                    !terminiOdgovarajuRoomName.contains(termin) &&
                    termin.getRoom().getNaziv().equals(roomName)) {
                terminiOdgovarajuRoomName.add(termin);
            }

            if (additional != null && !terminiOdgovarajuAdditional.contains(termin)) {
                boolean allEntriesMatch = true;

                for (Map.Entry<String, String> oneAdditional : additional.entrySet()) {
                    if (!termin.getAdditional().containsKey(oneAdditional.getKey()) ||
                            !termin.getAdditional().get(oneAdditional.getKey()).equals(oneAdditional.getValue())) {
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
            // ne radi,  moram da idem sad, ako budes radila da znas
            if (dayOfTheWeek != null &&
                    !terminiOdgovarajuDayOfTheWeek.contains(termin) &&
                    termin.getRoom().getNaziv().equals(roomName)) {
                terminiOdgovarajuDayOfTheWeek.add(termin);
            }
        }


        List<List<Termin>> listsToIntersect = Arrays.asList(
                terminiOdgovarajuStart,
                terminiOdgovarajuEnd,
                terminiOdgovarajuRoomName,
                terminiOdgovarajuAdditional,
                terminiOdgovarajuDayOfTheWeek
        );

        // Remove null lists
        List<List<Termin>> nonNullLists = new ArrayList<>();
        for (List<Termin> list : listsToIntersect) {
            if (!list.isEmpty()) {
                nonNullLists.add(list);
            }
        }

        // Perform intersection only if there are non-null lists
        if (!nonNullLists.isEmpty()) {
            presekOdgovarajucihTermina.addAll(nonNullLists.get(0));
            nonNullLists.subList(1, nonNullLists.size()).forEach(presekOdgovarajucihTermina::retainAll);
        }

        return presekOdgovarajucihTermina;
    }


    @Override
    public boolean provaraZauzetosti(String kriterijum) {
        return false;
    }


//    @Override
//    public boolean loadData(String path, String configPath) {
//        try {
//            loadApache(path, configPath);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return true;
//    }
//    @Override
//    public void addRoom(String podaci) {
//        List<String> podaciList;
//        podaciList = Arrays.asList(podaci.split(" ", 2));
//        String naziv = podaciList.get(0);
//        Map<String, String> equipmentMapa = new HashMap<>();
//
//        for (String equipment : podaciList) {
//            if (!equipment.contains(naziv))
//                equipmentMapa.put(StringUtils.substringBefore(equipment, ":"), StringUtils.substringAfter(equipment, ":"));
//        }
//        dodajSobu(naziv, equipmentMapa);
//    }

//    private void dodajSobu(String naziv, Map<String, String> equipment) {
//        for (Room room : getSveSobe()) {
//            if (room.getNaziv().equals(naziv)) {
//                System.out.println("Soba pod tim imeno vec postoji. ");
//                return;
//            }
//        }
//        Room newRoom = new Room();
//        newRoom.setNaziv(naziv);
//        newRoom.setEquipment(equipment);
//        getSveSobe().add(newRoom);
//        System.out.println(getSveSobe());
//    }
}

