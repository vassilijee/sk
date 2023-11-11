package Specifikacija;

import Termin.Room;
import Termin.Termin;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public abstract class SpecifikacijaRasporeda implements Specification {
    // KONSTRUKTOR ?
    private List<Termin> raspored = new ArrayList<>();
    private LocalTime radnoVremeOd;
    private LocalTime radnoVremeDo;
    private List<Room> sveSobe = new ArrayList<>();
    private LocalDate vaziOd;
    private LocalDate vaziDo;
    private List<LocalDate> neradniDani = new ArrayList<>();

    /**
     * initRaspored sluzi za inicijalizaciju rasporeda.
     * Dodacemo path ka metapodacima, gde cemo odrediti vremenska ogranicenja naseg rasporeda.
     *
     * @param path Path ka metapodaci fajlu
     */
    @Override
    public void initRaspored(String path) {

    }

    /**
     * addRoom sluzi da se dodaju prostori u metapodatke
     *
     * @param naziv     Podaci o prostoru koji se dodaje.
     * @param equipment Podaci o prostoru koji se dodaje.
     *
     */
    @Override
    public void addRoom(String naziv, Map<String, String> equipment) {

    }

    /**
     * addTermin sluzi za dodavanje novog termina u raspored.
     *
     * @param podaci Podaci o temrinu koji se dodaje.
     */
    @Override
    public void addTermin(String podaci) {

    }

    /**
     * deleteTermin sluzi za brisanje zadatog termina iz rasporeda
     *
     * @param podaci Podaci o terminu koji zelimo da obrisemo iz rasporeda.
     */
    @Override
    public void deleteTermin(String podaci) {

    }

    /**
     * moveTermin sluzi za pomeranje termina.
     *
     * @param podaci Podaci o terminima koje zelimo da zamenimo.
     */
    @Override
    public void moveTermin(String podaci) {

    }

    /**
     * pretragaTermina sluzi da pretrazimo raspored po zadatom kriterijumu za slobodne ili zauzete termine.
     *
     * @param kriterijum kriterijum po kome zelimo da nadjemo termine
     * @param zauzetost  Zauzetost(slobodno ili zauzeto)
     * @return lista termina koji ispunjavaju zadati kriterijum
     */
    @Override
    public List<Termin> pretragaTermina(String kriterijum, boolean zauzetost) {
        return null;
    }

    /**
     * proveraZauzetosti sluzi da proverimo da li zadati termin zauzet.
     *
     * @param kriterijum Podaci u terminu
     * @return true ako je slobodan, false ako je zauzet
     */
    @Override
    public boolean provaraZauzetosti(String kriterijum) {
        return false;
    }

    /**
     * loadData sluzi za ucitavanje vec postojeceg rasporeda.
     *
     * @param path       Putanja ka fajlu
     * @param configPath Putanja ka konfiguracionom fajlu
     * @return ako je true vraca sadrzaj fajla, ako false ispisuje gresku
     */
    @Override
    public boolean loadData(String path, String configPath) {
        return false;
    }

    /**
     * exportData sluzi za exportovanje rasporeda na zeljenu putanju.
     *
     * @param path Putanja ka destinicaji na koju zelimo da exportujemo raspored
     * @return ako je true vraca upisano, ako false ispisuje gresku
     */
    @Override
    public boolean exportData(String path) {
        return false;
    }

    @Override
    public String toString() {
        return "SpecifikacijaRasporeda{" + "radnoVremeOd=" + radnoVremeOd + ", radnoVremeDo=" + radnoVremeDo + ", sveSobe=" + sveSobe + ", vaziOd=" + vaziOd + ", vaziDo=" + vaziDo + ", neradniDani=" + neradniDani + '}';
    }
}
