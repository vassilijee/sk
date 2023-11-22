package Specifikacija;

import Termin.Termin;

import java.util.List;
import java.util.Map;

public interface Specification {
    //kreiranje rasporeda
    void initRaspored(String path);

    int addRoom(String naziv, Map<String, String> equipment);

    int addTermin(String start, String end, String ucionica, Map<String, String> additional);

    int deleteTermin(String start, String end, String ucionica);

    int moveTermin(String podaci);

    //pretrazivanje rasporeda

    List<Termin> pretragaTermina(String start, String end, String vremeod, String vremedo, String roomName, Map<String, String> additional, String dayOfTheWeek);

    boolean provaraZauzetostiUcionice(String naziv, String start, String end);

    //ucitavanje i snimanje rasporeda
    boolean loadData(String path, String configPath);

    boolean exportData(String path);
}
