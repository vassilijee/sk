package Termin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Getter
@Setter
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

    @Override
    public String toString() {
        return "Room{" +
                "naziv='" + naziv + '\'' +
                ", equipment=" + equipment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return Objects.equals(naziv, room.naziv);
    }

    @Override
    public int hashCode() {
        return naziv != null ? naziv.hashCode() : 0;
    }
}
