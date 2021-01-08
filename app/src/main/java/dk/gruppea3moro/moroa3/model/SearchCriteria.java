package dk.gruppea3moro.moroa3.model;

import java.util.ArrayList;
import java.util.Date;

public class SearchCriteria {
    private DateTime fromDate, toDate;
    private ArrayList<String> zone, types, moods;

    public SearchCriteria() {
        zone = new ArrayList<>();
        types = new ArrayList<>();
        moods = new ArrayList<>();
    }

    public void addZone(String addArea) {
        if (!zone.contains(addArea)) {
            zone.add(addArea);
        }
    }

    public void addType(String addType) {
        if (!types.contains(addType)) {
            types.add(addType);
        }
    }

    public void addMood(String addMood) {
        if (!moods.contains(addMood)) {
            moods.add(addMood);
        }
    }

    public void removeZone(String removeArea) {
        zone.remove(removeArea);
    }

    public void removeType(String removeType) {
        types.remove(removeType);
    }

    public void removeMood(String removeMood) {
        moods.remove(removeMood);
    }

    public DateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(DateTime fromDate) {
        this.fromDate = fromDate;
    }

    public DateTime getToDate() {
        return toDate;
    }

    public void setToDate(DateTime toDate) {
        this.toDate = toDate;
    }

    public ArrayList<String> getZone() {
        return zone;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public ArrayList<String> getMoods() {
        return moods;
    }
/*
    @Override
    public String toString() {
        return "SearchCriteria{" +
                "fromDate=" + fromDate.getTime() + fromDate.getDate() + "/" + fromDate.getMonth() + 1 + "/" + (fromDate.getYear() + 1900) +
                ", toDate=" + toDate.getTime() + toDate.getDate() + "/" + toDate.getMonth() + 1 + "/" + (toDate.getYear() + 1900) +
                ", zone=" + zone +
                ", types=" + types +
                ", moods=" + moods +
                '}';
    }

 */
}
