package Load;

import Termin.Termin;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

public class JSONDeserializer extends StdDeserializer<Termin> {
    public JSONDeserializer() {
        this(null);
    }

    public JSONDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Termin deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        String start = node.get("Ucionica").asText();
        String end = node.get("Pocetak").asText();
        String room = node.get("Kraj").asText();

        return new Termin(start, end, room);
    }
}
