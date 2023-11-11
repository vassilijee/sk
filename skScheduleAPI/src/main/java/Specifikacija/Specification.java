package Specifikacija;

import Termin.Termin;

import java.util.List;

public interface Specification {
    //kreiranje rasporeda
    void initRaspored(String path);

    void addRoom(String podaci);

    void addTermin(String podaci);

    void deleteTermin(String podaci);

    void moveTermin(String podaci);

    //pretrazivanje rasporeda
    List<Termin> pretragaTermina(String kriterijum, boolean zauzetost);

    boolean provaraZauzetosti(String kriterijum);

    //ucitavanje i snimanje rasporeda
    boolean loadData(String path, String configPath);

    boolean exportData(String path);
}
