package Termin;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private String naziv;
    private Map<String, String> equipment;

    public Room(){
        this.equipment = new HashMap<>();
    }

    public Room(String naziv){
        this.naziv = naziv;
        this.equipment = new HashMap<>();
    }

    public Room(String naziv, Map<String,String> equipment) {
        this.naziv = naziv;
        this.equipment = equipment;
    }
}
