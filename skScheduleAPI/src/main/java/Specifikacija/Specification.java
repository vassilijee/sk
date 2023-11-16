package Specifikacija;

import Termin.Termin;

import java.util.List;
import java.util.Map;

public interface Specification {
    //kreiranje rasporeda
    void initRaspored(String path);

    void addRoom(String naziv, Map<String, String> equipment);

    void addTermin(String podaci);

    void addTermin(String start, String end, String ucionica, Map<String, String> additional);

    void deleteTermin(String start, String end, String ucionica);

    void moveTermin(String podaci);

    //pretrazivanje rasporeda
    List<Termin> pretragaTermina(String kriterijum, boolean zauzetost);

    List<Termin> pretragaTermina(String start, String end, String roomName, Map<String, String> additional, String dayOfTheWeek);

    List<Termin> pretragaTermina(String start, String end, String vremeod, String vremedo, String roomName, Map<String, String> additional, String dayOfTheWeek);

    boolean provaraZauzetosti(String kriterijum);

    boolean provaraZauzetostiUcionice(String naziv, String start, String end);

    //ucitavanje i snimanje rasporeda
    boolean loadData(String path, String configPath);

    boolean exportData(String path);
}
