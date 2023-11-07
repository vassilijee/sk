package Specifikacija;

import Termin.Room;
import Termin.Termin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface Specification {
    //kreiranje rasporeda
    boolean initRaspored();

    void addRoom();

    void addRoom(String naziv);

    void addRoom(String naziv, Map<String, String> equipment);

    void addTermin();

    void addTermin(LocalDateTime start, LocalDateTime end, Room room);

    void addTermin(LocalDateTime start, LocalDateTime end, Room room, Map<String, String> additional);

    void deleteTermin(Termin termin);

    void moveTermin(Termin oldTermin, LocalDateTime start, LocalDateTime end, Room room);

    //pretrazivanje rasporeda
    List<Termin> pretragaTermina(String kriterijum, boolean zauzetost);

    //List<Termin> pretragaZauzeto(String kriterijum);
    boolean provaraZauzetosti(String kriterijum);

    //ucitavanje i snimanje rasporeda
    boolean loadData(String path, String configPath);
    boolean exportData(String path);
}
