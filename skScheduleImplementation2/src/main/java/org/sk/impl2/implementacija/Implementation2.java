package org.sk.impl2.implementacija;

import Specifikacija.SpecifikacijaRasporeda;
import Termin.Termin;
import Termin.Room;

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
        for (Termin t:
                getRaspored()) {
            if(termin.getStart().isBefore(t.getEnd()) &&
                    termin.getAdditional().get("Dan").equalsIgnoreCase(t.getAdditional().get("Dan")) &&
                    termin.getRoom().equals(t.getRoom())){
                flag = false;
            }
        }
        if(flag){
            getRaspored().add(termin);
        }
    }


   /* @Override
    public List<Termin> pretragaTermina(String kriterijum, boolean zauzetost) {
        return super.pretragaTermina(kriterijum, zauzetost);
    }*/

    /*@Override
    public List<Termin> pretragaTermina(String start, String end, String roomName, Map<String, String> additional, String dayOfTheWeek) {
        List<Termin> termini = new ArrayList<>();
        for (Termin t:
             getRaspored()) {

        }
        return termini;
    }*/

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
            if(((start==null && end == null) || ( (startd!=null) && t.getStart().isBefore(startd.atStartOfDay()) && t.getEnd().isAfter(startd.atStartOfDay()) && t.getAdditional().get("Dan").equalsIgnoreCase(nadjiDan(startd.getDayOfWeek().toString()))))
                    && ((pocetak == null && kraj==null) || ( (pocetak!=null && kraj!=null) && t.getStart().getHour() == pocetak.getHour() && t.getStart().getMinute() == pocetak.getMinute()  && t.getEnd().getHour() == kraj.getHour() && t.getEnd().getMinute() == kraj.getMinute()))
                    && (roomName == null || (t.getRoom().equals(room)))
                    && (additional==null || (ispitajAdditional(t, additional))) ){
                termini.add(t);
            } else if( ((start==null && end == null) || ( (startd!=null && endd!=null) && t.getStart().isBefore(startd.atStartOfDay()) && t.getEnd().isAfter(endd.atStartOfDay())))
                    && (dayOfTheWeek == null || dayOfTheWeek.equalsIgnoreCase(t.getAdditional().get("Dan")))
                    && (pocetak == null || ( (pocetak!=null && kraj!=null) && t.getStart().getHour() == pocetak.getHour() && t.getStart().getMinute() == pocetak.getMinute()  && t.getEnd().getHour() == kraj.getHour() && t.getEnd().getMinute() == kraj.getMinute()))
                    && (roomName == null || (t.getRoom().equals(room)))
                    && (additional==null || (ispitajAdditional(t, additional))) ){
                termini.add(t);
            }

        }
        return termini;
    }

    @Override
    public boolean provaraZauzetosti(String kriterijum) {
        return super.provaraZauzetosti(kriterijum);
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
