package dk.gruppea3moro.moroa3.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventDTO implements Serializable {
    String title, subtext, eventLink, imageLink, startTime, endTime, startDate, endDate, zone;
    double price;
    AddressDTO addressDTO;
    ArrayList<String> types, moods;

    //zones
    public static final String ZONE_NORREBRO ="NØRREBRO";
    public static final String ZONE_OSTERBRO ="ØSTERBRO";
    public static final String ZONE_ISLANDS_BRYGGE ="ISLANDS BRYGGE";
    public static final String ZONE_INDRE_BY ="INDRE BY";
    public static final String ZONE_NORDVEST ="NORDVEST";
    public static final String ZONE_VALBY ="VALBY";
    public static final String ZONE_BRONSHOJ_OG_HUSUM ="BRØSHØJ & HUSUM";
    public static final String ZONE_AMAGER ="AMAGER";
    public static final String ZONE_VANLOSE ="VANLØSE";
    public static final String ZONE_CHRISTIANSHAVN ="CHRISTIANSHAVN";
    public static final String ZONE_REFSHALEOEN ="REFSHALEØEN";
    //types
    public static final String TYPE_KONCERT ="KONCERT";
    public static final String TYPE_UDSTILLING_OG_KUNST ="UDSTILLING & KUNST";
    public static final String TYPE_LITTERATUR ="LITTERATUR";
    public static final String TYPE_FILM ="FILM";
    public static final String TYPE_COMEDY ="COMEDY";
    public static final String TYPE_TALK ="TALK";
    public static final String TYPE_TEATER_OG_FORESTILLINGER ="TEATER & FORESTILLINGER";
    public static final String TYPE_FEST ="FEST";
    public static final String TYPE_GRATIS ="GRATIS";
    public static final String TYPE_SPORT_OG_SPIL ="SPORT & SPIL";
    public static final String TYPE_MAD_OG_DRIKKE ="MAD & DRIKKE";
    public static final String TYPE_MODE ="MODE";
    //stemninger
    public static final String STEMNING_IKKE_HJEM ="DU IKKE VIL HJEM, MEN VIDERE";
    public static final String STEMNING_UD_I_DET_BLA ="DU VIL UD I DET BLÅ";
    public static final String STEMNING_TOMMERMAND ="DU HAR TØMMERMÆND";
    public static final String STEMNING_UDVIDE_HORISONT ="DU VIL UDVIDE DIN HORISONT";
    public static final String STEMNING_TOMME_LOMMER ="DU HAR TOMME LOMMER";
    public static final String STEMNING_MAD_GLAD ="MAD GØR DIG GLAD";
    public static final String STEMNING_FORKALE_DIG_SELV ="DU VIL FORKÆLE DIG SELV";
    public static final String STEMNING_IMPONERE_DATE ="DU VIL IMPONERE DIN DATE";
    public static final String STEMNING_DE_GAMLE_BESOG ="DE GAMLE KOMMER PÅ BESØG";
    public static final String STEMNING_GANG_I_KROPPEN ="DU VIL HAVE GANG I KROPPEN";





    public EventDTO(String title, String subtext, String eventLink, String startDate, String endDate, double price, String imageLink) {
        this.title = title;
        this.subtext = subtext;
        this.eventLink = eventLink;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.imageLink = imageLink;
    }

    public EventDTO() {

    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public ArrayList<String> getMoods() {
        return moods;
    }

    public void setMoods(ArrayList<String> moods) {
        this.moods = moods;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "title='" + title + '\'' +
                ", subtext='" + subtext + '\'' +
                ", eventLink='" + eventLink + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", addressDTO=" + addressDTO +
                ", types=" + types +
                ", moods=" + moods +
                '}';
    }


    public String getStartTime() {
        return startTime;

    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getSQLendDate(){
        String result = endDate.substring(6,10) + "/" + endDate.substring(3,5) +"/"+ endDate.substring(0,2)
                + " " +endTime + ":00";
        return result;
    }

    public String getSQLstartDate(){
        String result = startDate.substring(6,10) + "/" + startDate.substring(3,5) +"/"+ startDate.substring(0,2)
                + " " +startTime + ":00";
        return result;
    }

    public void setDateFields(String sqlStartDate, String sqlEndDate){
        startTime = sqlStartDate.substring(11,16);
        startDate = sqlStartDate.substring(8,10) + "/"+sqlStartDate.substring(5,7) + "/" +sqlStartDate.substring(0,5);
        endTime = sqlEndDate.substring(11,16);
        endDate = sqlEndDate.substring(8,10) + "/"+sqlEndDate.substring(5,7) + "/" +sqlEndDate.substring(0,5);
    }


}
