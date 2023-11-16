package org.sk.impl2.implementacija;

import Specifikacija.SpecifikacijaRasporeda;
import Termin.Termin;
import Termin.Room;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Implementation2 extends SpecifikacijaRasporeda {
    @Override
    public void addTermin(String start, String end, String ucionica, Map<String, String> additional) {
        Termin termin = new Termin(LocalDateTime.parse(start, getFormatDatuma()),
                LocalDateTime.parse(end, getFormatDatuma()), nadiSobuPoImenu(ucionica), additional);
        boolean flag = true;
        if(termin.getStart().getHour() < getRadnoVremeOd().getHour() || termin.getEnd().getHour() > getRadnoVremeDo().getHour())
            flag = false;
        else{
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
        }
        if(flag){
            getRaspored().add(termin);
        }
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
                   if(t.getStart().getHour() >= startd.getHour() && t.getStart().getMinute() >= startd.getMinute()
                           && t.getEnd().getHour() >= endd.getHour() && t.getEnd().getMinute() >= endd.getMinute())
                       return true;
                   if(t.getEnd().getHour() > startd.getHour() && t.getEnd().getMinute() > startd.getMinute()
                           && t.getEnd().getHour() < endd.getHour() && t.getEnd().getMinute() < endd.getMinute())
                       return true;
                   if(t.getStart().getHour()<endd.getHour() && t.getStart().getMinute() < endd.getMinute()
                           && t.getStart().getHour() > startd.getHour() && t.getStart().getMinute() > startd.getMinute())
                       return true;
                   if(t.getStart().getHour() > startd.getHour() && t.getStart().getMinute() > startd.getMinute()
                           && t.getEnd().getHour() < endd.getHour() && t.getEnd().getMinute() < endd.getMinute())
                       return true;
               }
            }
        }
        return false;
    }

    @Override
    public boolean exportData(String s) {
        if (s.endsWith(".txt")) {
            File txtFile = new File(s);
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(txtFile);
                for (Termin termin : getRaspored()) {
                    StringBuilder line = new StringBuilder();
                    line.append(termin);
                    fileWriter.write(line.toString());
                }
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (s.endsWith(".csv")) {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(s);
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
                List<String> heder = new ArrayList<>();
                heder.add("Dan");
                heder.add("Vreme");
                heder.add("Od datuma");
                heder.add("Do datuma");
                heder.add("Prosotrija");
                heder.add("Prodesor");
                heder.add("Predmet");
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
                    );
//                    for (Map.Entry<String, String> oneAdditional : termin.getAdditional().entrySet()) {
//                        csvPrinter.print(oneAdditional.getValue());
//                    }
                }
                csvPrinter.close();
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (s.endsWith(".json")) {
           /* try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                FileWriter fileWriter = new FileWriter(s);

                mapper.registerModule(new JavaTimeModule());
                mapper.writeValue(out, getRaspored());


                byte[] data = out.toByteArray();
                fileWriter.write(new String(data));
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            //System.out.println(new String(data));*/
        }
       return  true;
    }


    public String terminString(Termin termin){
        return "Termin " +
                "Dan=" + termin.getAdditional().get("Dan") +
                ", Vreme=" + termin.getStart().format(DateTimeFormatter.ofPattern("HH:mm")) + "-" +
                termin.getEnd().format(DateTimeFormatter.ofPattern("HH:mm")) +
                ", Od=" + termin.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
                ", Do=" + termin.getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
                ", Room=" + ispisiRoom(termin.getRoom()) +
                ispisiAdditional(termin.getAdditional())+
                '\n';

    }
    public String ispisRasporeda(List<Termin> raspored) {
        StringBuilder sb = new StringBuilder();
        for (Termin t:
                raspored) {
            sb.append(terminString(t));
        }
        return sb.toString();
    }

    public String ispisiAdditional(Map<String,String> additional){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry:
                additional.entrySet()) {
            if(entry.getKey().equalsIgnoreCase("dan"))
                continue;
            sb.append(entry.getKey() + "=" + entry.getValue() + ", ");
        }
        return sb.toString();
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
