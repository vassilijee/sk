package Specifikacija;

public class SpecifikacijaRasporedaManager {
    private static SpecifikacijaRasporeda specifikacijaRasporeda;

    public static void registerExporter(SpecifikacijaRasporeda specExp) {
        specifikacijaRasporeda = specExp;
    }

    public static SpecifikacijaRasporeda getExporter() {
        specifikacijaRasporeda.getRaspored();
        return specifikacijaRasporeda;
    }
}
