package impl.csv;

import Termin.Room;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MetaLoad {
    // Ova polja predstavljaju obavezne metapodatke? dodatni?
    private LocalTime vremeOd;
    private LocalTime vremeDo;
    private List<Room> sveSobe;
    private List<LocalDate> neradniDani;

    public MetaLoad(LocalTime vremeOd, LocalTime vremeDo, List<Room> sveSobe, List<LocalDate> neradniDani) {
        this.vremeOd = vremeOd;
        this.vremeDo = vremeDo;
        this.sveSobe = sveSobe;
        this.neradniDani = neradniDani;
    }

    @Override
    public String toString() {
        return "vremeOd=" + vremeOd + ", vremeDo=" + vremeDo + ", sveSobe=" + sveSobe + ", neradniDani=" + neradniDani + '}';
    }
}
