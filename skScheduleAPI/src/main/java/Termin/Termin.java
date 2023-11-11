package Termin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Termin {

    private LocalDateTime start;
    private LocalDateTime end;
    private Room room;
    private Map<String, String> additional;

    public Termin() {
        this.additional = new HashMap<>();
    }

    public Termin(LocalDateTime start, LocalDateTime end, Room room) {
        this.start = start;
        this.end = end;
        this.room = room;
        this.additional = new HashMap<>();
    }

    public Termin(LocalDateTime start, LocalDateTime end, Room room, Map<String, String> additional) {
        this.start = start;
        this.end = end;
        this.room = room;
        this.additional = additional;
    }

    @Override
    public String toString() {
        return "Termin{" + "start=" + start + ", end=" + end + ", room=" + room + ", additional=" + additional + '}' + '\n';
    }


}
