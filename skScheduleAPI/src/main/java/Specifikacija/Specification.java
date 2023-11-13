package Specifikacija;

import Termin.Termin;

import java.util.List;
import java.util.Map;

public interface Specification {
    //kreiranje rasporeda
    void initRaspored(String path);

    void addRoom(String naziv, Map<String, String> equipment);

    void addTermin(String podaci);

    void deleteTermin(String start, String end, String ucionica);

    void moveTermin(String podaci);

    //pretrazivanje rasporeda
    List<Termin> pretragaTermina(String kriterijum, boolean zauzetost);
    List<Termin> pretragaTermina(String start, String end, String roomName, Map<String, String> additional);

    boolean provaraZauzetosti(String kriterijum);

    //ucitavanje i snimanje rasporeda
    boolean loadData(String path, String configPath);

    boolean exportData(String path);
}
