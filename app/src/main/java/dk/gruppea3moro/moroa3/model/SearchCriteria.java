package dk.gruppea3moro.moroa3.model;

import java.util.ArrayList;
import java.util.Date;

public class SearchCriteria {
    private Date fromDate, toDate;
    public ArrayList<String> areas, types, moods;

    public SearchCriteria() {
        areas = new ArrayList<String>();
        types = new ArrayList<String>();
        moods = new ArrayList<String>();
    }

    public void addArea(String addArea) {
        areas.add(addArea);
    }

    public void addType(String addType) {
        types.add(addType);
    }

    public void addMood(String addMood) {
        moods.add(addMood);
    }

    public void removeArea(String removeArea) {
        areas.remove(removeArea);
    }

    public void removeType(String removeType) {
        types.remove(removeType);
    }

    public void removeMood(String removeMood) {
        moods.remove(removeMood);
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public ArrayList<String> getAreas() {
        return areas;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public ArrayList<String> getMoods() {
        return moods;
    }

}
