import Specifikacija.SpecifikacijaRasporeda;
import Specifikacija.SpecifikacijaRasporedaManager;
import Termin.Room;
import Termin.Termin;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Implementation2 extends SpecifikacijaRasporeda {
    static {
        SpecifikacijaRasporedaManager.registerExporter(new Implementation2());
    }

    public Implementation2() {
        super();
    }

    @Override
    public void addTermin(String start, String end, String ucionica, Map<String, String> additional) {
        Termin termin = new Termin(LocalDateTime.parse(start, getFormatDatuma()),
                LocalDateTime.parse(end, getFormatDatuma()), nadiSobuPoImenu(ucionica), additional);
        boolean flag = termin.getStart().getHour() >= getRadnoVremeOd().getHour() && termin.getEnd().getHour() <= getRadnoVremeDo().getHour() && getSveSobe().contains(termin.getRoom())
                && !getNeradniDani().contains(termin.getStart().toLocalDate()) && !getNeradniDani().contains(termin.getEnd().toLocalDate());
        /*else{
            for (Termin t:
                    getRaspored()) {
                if( ((termin.getStart().isBefore(t.getEnd()) && !termin.getEnd().isBefore(t.getStart())) || (termin.getEnd().isAfter(t.getStart()) && !termin.getStart().isAfter(t.getEnd())))
                        && termin.getAdditional().get("Dan").equalsIgnoreCase(t.getAdditional().get("Dan"))
                        && termin.getRoom().equals(t.getRoom()) ){
                    if(t.getStart().getHour() == termin.getStart().getHour() && t.getStart().getMinute() == termin.getStart().getMinute()
                            && t.getEnd().getHour() == termin.getEnd().getHour() && t.getEnd().getMinute() == termin.getEnd().getMinute())
                        flag = false;
                    if(t.getEnd().getHour() >= termin.getStart().getHour() && t.getEnd().getMinute() >= termin.getStart().getMinute())
                        flag = false;
                    if(t.getStart().getHour() <= termin.getEnd().getHour() && t.getStart().getMinute() <= termin.getEnd().getMinute())
                        flag = false;
                }
            }
        }*/
        if(flag && provera(termin, null)){
            getRaspored().add(termin);
        }
    }

    public boolean provera(Termin termin, Termin termin2){
        for (Termin t:
                getRaspored()) {
            if(termin2 != null && termin2.equals(t))
                continue;
            if( ((termin.getStart().isBefore(t.getEnd()) && !termin.getEnd().isBefore(t.getStart())) || (termin.getEnd().isAfter(t.getStart()) && !termin.getStart().isAfter(t.getEnd())))
                    && termin.getAdditional().get("Dan").equalsIgnoreCase(t.getAdditional().get("Dan"))
                    && termin.getRoom().equals(t.getRoom()) ){
                if(t.getStart().getHour() ==  termin.getStart().getHour() && t.getEnd().getHour() == termin.getEnd().getHour() )
                    return false;
                if(t.getStart().getHour() <  termin.getStart().getHour() && t.getEnd().getHour() > termin.getEnd().getHour())
                    return false;
                if(t.getEnd().getHour() >  termin.getStart().getHour() && t.getEnd().getHour() < termin.getEnd().getHour() )
                    return false;
                if(t.getStart().getHour() < termin.getEnd().getHour() && t.getStart().getHour() >  termin.getStart().getHour())
                    return false;
                if(t.getStart().getHour() >  termin.getStart().getHour() && t.getEnd().getHour() < termin.getEnd().getHour())
                    return false;
                if(t.getStart().getHour() == termin.getEnd().getHour() && t.getStart().getMinute() < termin.getEnd().getMinute())
                    return false;
                if(t.getEnd().getHour() ==  termin.getStart().getHour() && t.getEnd().getMinute() < termin.getEnd().getMinute())
                    return false;
            }
        }
        return  true;
    }

    @Override
    public void moveTermin(String podaci) {
        String terminZaBrisanje = StringUtils.substringBefore(podaci, "|");
        String oldstart;
        String oldend;
        String oldroom;
        String olddan;

        List<String> podaciListZaBrisanje;
        podaciListZaBrisanje = Arrays.asList(terminZaBrisanje.split(";", 5));
        oldstart = StringUtils.substringBefore(podaciListZaBrisanje.get(0), " -");
        oldend = StringUtils.substringAfter(podaciListZaBrisanje.get(0), "- ");
        oldroom = podaciListZaBrisanje.get(1).trim();
        olddan = podaciListZaBrisanje.get(2).trim();

        String terminZeljeniNovi = StringUtils.substringAfter(podaci, "|");
        String newstart;
        String newend;
        String newroom;
        String newdan;

        List<String> podaciListZaNovi;
        podaciListZaNovi = Arrays.asList(terminZeljeniNovi.split(";", 4));
        newstart = StringUtils.substringBefore(podaciListZaNovi.get(0), " -");
        newend = StringUtils.substringAfter(podaciListZaNovi.get(0), "- ");
        newroom = podaciListZaNovi.get(1).trim();
        newdan = podaciListZaNovi.get(2).trim();
        if(move(oldstart, oldend, oldroom, olddan, newstart, newend, newroom, newdan))
            System.out.println("Uspesno");
        else
            System.out.println("Neuspesno");

    }

    public boolean move(String oldstart, String oldend, String oldroom, String olddan, String newstart, String newend, String newroom, String newdan){
        Termin tmpTerminNovi = new Termin();
        Room tmpRoomNovi = nadiSobuPoImenu(newroom);
        tmpTerminNovi.setStart(LocalDateTime.parse(newstart, DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")));
        tmpTerminNovi.setEnd(LocalDateTime.parse(newend, DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")));
        tmpTerminNovi.setRoom(tmpRoomNovi);
        tmpTerminNovi.getAdditional().put("Dan", newdan);

        Termin tmpTerminStari = nadjiTermin(oldstart,oldend,oldroom);
        if(!provera(tmpTerminNovi,tmpTerminStari))
            return false;
        for (Map.Entry<String, String> entry:
             tmpTerminStari.getAdditional().entrySet()) {
            if(!entry.getKey().equals("Dan"))
                tmpTerminNovi.getAdditional().put(entry.getKey(), entry.getValue());
        }
        deleteTermin(oldstart,oldend,oldroom);
        //addTermin(newstart,newend,newroom,tmpTerminNovi.getAdditional());
        getRaspored().add(tmpTerminNovi);
        return true;
    }

    @Override
    public List<Termin> pretragaTermina(String start, String end, String vremeod, String vremedo, String roomName, Map<String, String> additional, String dayOfTheWeek) {
        List<Termin> termini = new ArrayList<>();
        LocalDate startd=null, endd=null;
        LocalTime  pocetak=null, kraj=null;
        if(start!=null)
            startd = LocalDate.parse(start,DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        if(end!=null)
            endd = LocalDate.parse(end,DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        if(vremeod!=null)
            pocetak = LocalTime.parse(vremeod, DateTimeFormatter.ofPattern("HH:mm"));
        if(vremedo!=null)
            kraj = LocalTime.parse(vremedo, DateTimeFormatter.ofPattern("HH:mm"));
        Room room = nadiSobuPoImenu(roomName);
        if(getNeradniDani().contains(startd) || getNeradniDani().contains(endd))
            return termini;
        for (Termin t:
                getRaspored()) {
            if( ((start==null && end == null) || ( (startd!=null) && t.getStart().isBefore(startd.atTime(21,0)) && t.getEnd().isAfter(startd.atTime(9,0)) && t.getAdditional().get("Dan").equalsIgnoreCase(nadjiDan(startd.getDayOfWeek().toString()))))
                    && ( (pocetak == null && kraj==null) || ( (pocetak!=null && kraj!=null) && t.getStart().getHour() == pocetak.getHour() && t.getStart().getMinute() == pocetak.getMinute()  && t.getEnd().getHour() == kraj.getHour() && t.getEnd().getMinute() == kraj.getMinute())
                     || ((pocetak!=null && kraj==null )&& t.getStart().getHour() == pocetak.getHour() && t.getStart().getMinute() == pocetak.getMinute())  || ((pocetak==null && kraj!=null) && t.getEnd().getHour() == kraj.getHour() && t.getEnd().getMinute() == kraj.getMinute()) )
                    && (roomName == null || (t.getRoom().equals(room)))
                    && (additional==null || (ispitajAdditional(t, additional)))
                    && dayOfTheWeek == null){
                if(termini.contains(t))
                    continue;
                termini.add(t);
            }
            if( ((start==null && end == null) || ( (startd!=null && endd!=null) && ((t.getStart().isBefore(startd.atStartOfDay()) && t.getEnd().isAfter(startd.atStartOfDay())) || (t.getStart().isBefore(endd.atStartOfDay()) && t.getEnd().isAfter(endd.atStartOfDay())))))
                    && (dayOfTheWeek == null || dayOfTheWeek.equalsIgnoreCase(t.getAdditional().get("Dan")))
                    && ( (pocetak == null && kraj==null) || ( (pocetak!=null && kraj!=null) && t.getStart().getHour() == pocetak.getHour() && t.getStart().getMinute() == pocetak.getMinute()  && t.getEnd().getHour() == kraj.getHour() && t.getEnd().getMinute() == kraj.getMinute())
                    || ((pocetak!=null && kraj==null )&& t.getStart().getHour() == pocetak.getHour() && t.getStart().getMinute() == pocetak.getMinute())  || ((pocetak==null && kraj!=null) && t.getEnd().getHour() == kraj.getHour() && t.getEnd().getMinute() == kraj.getMinute()) )
                    && (roomName == null || (t.getRoom().equals(room)))
                    && (additional==null || (ispitajAdditional(t, additional)))  ){
                if(termini.contains(t))
                    continue;
                termini.add(t);
            }
        }
        return termini;
    }

    @Override
    public boolean provaraZauzetostiUcionice(String naziv, String start, String end) {
        LocalDateTime startd = LocalDateTime.parse(start,DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
        LocalDateTime endd = LocalDateTime.parse(end,DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
        String dan = nadjiDan(startd.getDayOfWeek().toString());
        for (Termin t:
                getRaspored()) {
            if(t.getRoom().getNaziv().equalsIgnoreCase(naziv)) {
               if(t.getStart().isBefore(startd) && t.getEnd().isAfter(startd) && t.getAdditional().get("Dan").equalsIgnoreCase(dan)){
                   if(t.getStart().getHour() == startd.getHour() && t.getEnd().getHour() == endd.getHour() )
                       return true;
                   if(t.getStart().getHour() < startd.getHour() && t.getEnd().getHour() > endd.getHour())
                       return true;
                   if(t.getEnd().getHour() > startd.getHour() && t.getEnd().getHour() < endd.getHour() )
                       return true;
                   if(t.getStart().getHour() < endd.getHour() && t.getStart().getHour() > startd.getHour())
                       return true;
                   if(t.getStart().getHour() > startd.getHour() && t.getEnd().getHour() < endd.getHour())
                       return true;
                   if(t.getStart().getHour() == endd.getHour() && t.getStart().getMinute() < endd.getMinute())
                       return true;
                   if(t.getEnd().getHour() == startd.getHour() && t.getEnd().getMinute() < endd.getMinute())
                       return true;
               }
            }
        }
        return false;
    }

    @Override
    public boolean exportData(String s) {
        if (s.endsWith(".pdf")) {
            try {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750); // Set the position
                for (Termin termin : getRaspored()) {
                    contentStream.showText(pdf(termin));
                    contentStream.newLine();
                }
                contentStream.endText();

                contentStream.close();
                document.save(s);
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (s.endsWith(".csv")) {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(s);
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
                List<String> heder = new ArrayList<>();
                /*heder.add("Dan");
                heder.add("Vreme");
                heder.add("Od datuma");
                heder.add("Do datuma");
                heder.add("Prosotrija");
                heder.add("Prodesor");
                heder.add("Predmet");*/
                csvPrinter.printRecord(heder);
                for (Termin termin : getRaspored()) {
                    csvPrinter.printRecord(
                            termin.getAdditional().get("Dan"),
                            termin.getStart().format(DateTimeFormatter.ofPattern("HH:mm")) + "-" +
                                    termin.getEnd().format(DateTimeFormatter.ofPattern("HH:mm")),
                            termin.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                            termin.getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                            termin.getRoom().getNaziv(),
                            termin.getAdditional().get("Profesor"),
                            termin.getAdditional().get("Predmet")
                            //termin.getRoom().getEquipment().get("racunar")
                    );

                }
                csvPrinter.close();
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
       return  true;
    }

    public  String pdf(Termin termin){
        return  termin.getAdditional().get("Dan") + ", " +
                termin.getStart().format(DateTimeFormatter.ofPattern("HH:mm")) + "-" +
                termin.getEnd().format(DateTimeFormatter.ofPattern("HH:mm")) + ", " +
                termin.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ", " +
                termin.getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ", " +
                termin.getRoom().getNaziv() + ", " +
                ispisiAdditional(termin.getAdditional(), false);
    }

    public String terminString(Termin termin){
        return "Termin " +
                "Dan=" + termin.getAdditional().get("Dan") +
                ", Vreme=" + termin.getStart().format(DateTimeFormatter.ofPattern("HH:mm")) + "-" +
                termin.getEnd().format(DateTimeFormatter.ofPattern("HH:mm")) +
                ", Od=" + termin.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
                ", Do=" + termin.getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
                ", Room=" + ispisiRoom(termin.getRoom()) + " " +
                ispisiAdditional(termin.getAdditional(), true);

    }
    @Override
    public String ispisRasporeda(List<Termin> raspored) {
        StringBuilder sb = new StringBuilder();
        for (Termin t:
                raspored) {
            sb.append(terminString(t) + "\n");

        }
        return sb.toString();
    }


    public String ispisiAdditional(Map<String,String> additional, boolean uslov){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry:
                additional.entrySet()) {
            if(entry.getKey().equalsIgnoreCase("dan"))
                continue;
            if(uslov)
                sb.append(entry.getKey() + "=" + entry.getValue() + ", ");
            else
                sb.append(entry.getValue() + ", ");
        }
        return sb.substring(0,sb.length()-2);
    }
    public String ispisiRoom(Room room){
        StringBuilder sb = new StringBuilder();
        sb.append(room.getNaziv()+ ", ");
        for (Map.Entry<String, String> entry:
                room.getEquipment().entrySet()) {
            if(entry.getKey().equalsIgnoreCase("dan"))
                continue;
            sb.append(entry.getKey() + "=" + entry.getValue() + ", ");
        }
        return sb.toString();
    }
    public String nadjiDan(String dan){
        if(dan.equalsIgnoreCase("monday")){
            return "ponedeljak";
        } else if(dan.equalsIgnoreCase("tuesday")){
            return "utorak";
        } else if(dan.equalsIgnoreCase("wednesday")){
            return "sreda";
        } else if(dan.equalsIgnoreCase("thursday")){
            return "cetvrtak";
        } else if(dan.equalsIgnoreCase("friday")){
            return "petak";
        } else if(dan.equalsIgnoreCase("saturday")){
            return "subota";
        } else {
            return "nedelja";
        }

    }


    public boolean ispitajAdditional(Termin t, Map<String, String> k){
        boolean flag = false;
        for (Map.Entry<String, String> s:
            k.entrySet()) {
            if(t.getAdditional().containsKey(s.getKey())){
                flag = t.getAdditional().get(s.getKey()).equalsIgnoreCase(s.getValue());
            }
            if(!flag)
                break;
        }
        return flag;
    }
}
