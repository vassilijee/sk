package Load;

import Termin.Room;
import Termin.Termin;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONDeserializer extends StdDeserializer<Termin> {
    List<ConfigLoad> configLoadList = new ArrayList<>();
    List<Room> sveSobe = new ArrayList<>();

//    public JSONDeserializer() {
//        this(null);
//    }

    public JSONDeserializer(List<ConfigLoad> configLoadList, List<Room> sveSobe) {
        this((Class<?>) null);
        this.configLoadList = configLoadList;
        this.sveSobe = sveSobe;
    }

    public JSONDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Termin deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        DateTimeFormatter formatter = null;
        String roomName = "";
        LocalDateTime start = null;
        LocalDateTime end = null;
        Map<String, String> additional = new HashMap<>();

        for (ConfigLoad configloadElement : configLoadList) {
            if (configloadElement.getIndex() == -1) {
                // format
                formatter = DateTimeFormatter.ofPattern(configloadElement.getPoSpec());
            } else if (configloadElement.getIndex() == 0) {
                // room
                roomName = node.get(configloadElement.getPoHead()).asText();
            } else if (configloadElement.getIndex() == 1) {
                // start (mora u prvom redu u cfg da bude -1 ili nece raditi.
                // Bolja opcija je da se izbaci formatter van ovog fora, da bi sigurno imao format pre nego sto krene u ovaj for)
                start = LocalDateTime.parse(node.get(configloadElement.getPoHead()).asText(), formatter);
            } else if (configloadElement.getIndex() == 2) {
                // end
                end = LocalDateTime.parse(node.get(configloadElement.getPoHead()).asText(), formatter);
            } else if (configloadElement.getIndex() > 2) {
                // additionals
                additional.put(configloadElement.getPoHead(), node.get(configloadElement.getPoHead()).asText());
            }
        }

        Termin termin = new Termin();

        for (Room room : sveSobe) {
            if (room.getNaziv().equals(roomName)) {
                termin.setRoom(room);
            }
            // mozda neki else ako se ucitava soba koja ne postoji u listi svih soba dodatih iz meta.txt?
        }

        termin.setStart(start);
        termin.setEnd(end);
        termin.setAdditional(additional);

        return termin;
    }
}
