package Load;

import Specifikacija.SpecifikacijaRasporeda;
import Termin.Termin;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONLoad extends Load {
    @Override
    public void load(String path, String configPath, SpecifikacijaRasporeda specifikacijaRaspored) throws IOException {
        List<ConfigLoad> columnLoading = readConfig(configPath);


        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(Termin.class, new JSONDeserializer(columnLoading, specifikacijaRaspored.getSveSobe()));
        mapper.registerModule(module);

        specifikacijaRaspored.getRaspored().addAll(mapper.readValue(file, new TypeReference<List<Termin>>() {
        }));
    }
}

