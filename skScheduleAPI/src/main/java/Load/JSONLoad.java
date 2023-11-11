package Load;

import Specifikacija.SpecifikacijaRasporeda;
import Termin.Termin;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONLoad extends Load {
    @Override
    public void load(String path, String configPath, SpecifikacijaRasporeda specifikacijaRaspored) throws IOException {
        List<ConfigLoad> columnLoading = readConfig(configPath);
//        Map<Integer, String> mappings = new HashMap<>();
//        for (ConfigLoad configMapping : columnLoading) {
//            mappings.put(configMapping.getIndex(), configMapping.getPoSpec());
//        }

        // mora custom deserializer da se smisli

        ObjectMapper objectMapper = new ObjectMapper();

        //TypeReference<Termin> listType = new TypeReference<List<Termin>>() {};
        //List<Person> list = mapper.readValue(jsonString, listType);



        System.out.println(columnLoading);
    }
}

