package dk.gruppea3moro.moroa3.model;

import java.util.ArrayList;
import java.util.Date;

public class SearchCriteria {
    private Date fromDate, toDate;
    private ArrayList<String> areas, types, moods;

    public SearchCriteria() {
        areas = new ArrayList<String>();
        types = new ArrayList<String>();
        moods = new ArrayList<String>();
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
