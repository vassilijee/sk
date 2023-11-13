package org.sk.impl2.implementacija;

import Specifikacija.SpecifikacijaRasporeda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Implementation2 extends SpecifikacijaRasporeda {
   /* @Override
    public void addTermin(String start, String end, String ucionica, Map<String, String> additional) {
        Termin termin = new Termin(LocalDateTime.parse(start, DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")),
                LocalDateTime.parse(end, DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")), nadjisobupoImenu(ucionica), additional);
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


    @Override
    public List<Termin> pretragaTermina(String kriterijum, boolean zauzetost) {
        return super.pretragaTermina(kriterijum, zauzetost);
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
                ", Od=" + termin.getStart().format(DateTimeFormatter.ofPattern("MM/dd/YYYY")) +
                ", Do=" + termin.getEnd().format(DateTimeFormatter.ofPattern("MM/dd/YYYY")) +
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
*/
}
