package impl;

import Specifikacija.SpecifikacijaRasporeda;
import Termin.Room;
import Termin.Termin;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        File csvFile = new File(s);
        try {
            FileWriter fileWriter = new FileWriter(csvFile);
            for (Termin termin : getRaspored()) {
                StringBuilder line = new StringBuilder();
                line.append(termin);
                fileWriter.write(line.toString());

            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        tmpTerminZaBrisanje.setStart(LocalDateTime.parse(startOdZaBrisanje, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        tmpTerminZaBrisanje.setEnd(LocalDateTime.parse(endOdZaBrisanje, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
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

