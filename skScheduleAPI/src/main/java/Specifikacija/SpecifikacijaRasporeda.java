package Specifikacija;

import Termin.Room;
import Termin.Termin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public abstract class SpecifikacijaRasporeda implements Specification{
    @Override
    public boolean initRaspored() {
        return false;
    }

    @Override
    public void addRoom() {

    }

    @Override
    public void addRoom(String naziv) {

    }

    @Override
    public void addRoom(String naziv, Map<String, String> equipment) {

    }

    @Override
    public void addTermin() {

    }

    @Override
    public void addTermin(LocalDateTime start, LocalDateTime end, Room room) {

    }

    @Override
    public void addTermin(LocalDateTime start, LocalDateTime end, Room room, Map<String, String> additional) {

    }

    @Override
    public void deleteTermin(Termin termin) {

    }

    @Override
    public void moveTermin(Termin oldTermin, LocalDateTime start, LocalDateTime end, Room room) {

    }
    /**
     * Pretraga termina
     *
     * @param kriterijum Kriterijum asdasdsa
     * @param zauzetost Zauzetost asdasdasd
     * @return boolean if action is valid
     */
    @Override
    public List<Termin> pretragaTermina(String kriterijum, boolean zauzetost) {
        return null;
    }

    @Override
    public boolean provaraZauzetosti(String kriterijum) {
        return false;
    }

    @Override
    public boolean loadData(String path, String configPath) {
        return false;
    }

    @Override
    public boolean exportData(String path) {
        return false;
    }
}
