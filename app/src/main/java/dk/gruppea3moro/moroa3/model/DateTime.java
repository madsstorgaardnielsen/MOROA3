package dk.gruppea3moro.moroa3.model;


public class DateTime {
    String day, month, year, hour,minute;

    public DateTime() {
    }

    public DateTime(String day, String month, String year, String hour, String minute) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public void adjustStrings() {
        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1){
            month = "0" + month;
        }
        if (year.length() == 2){
            year = "20" + year;
        }
        if (hour.length() ==1){
            hour = "0" + hour;
        }
        if (minute.length() ==1){
            hour = "0" + hour;
        }

        try{
            if (day.length() != 2){
                throw new DateTimeException("Day String must have lenth 2");
            } else if (month.length() != 2){
                throw new DateTimeException("Month String must have lenth 2");
            } else if (year.length() != 4){
                throw new DateTimeException("Year String must have lenth 2");
            } else if (hour.length() != 2){
                throw new DateTimeException("Hour String must have lenth 2");
            } else if (minute.length() != 2){
                throw new DateTimeException("Minute String must have lenth 2");
            }
        } catch (DateTimeException e) {
            e.printStackTrace();
        }


    }

    public String getSqlDateTimeFormat(){
        adjustStrings();
        return year + "/" + month + "/" + day + " " + hour + ":" + minute +":00";
    }

    public String getDanishDateTimeFormat(){
        adjustStrings();
        return day + "/" + month + "/" + year + " " + hour + ":" + minute;
    }

    public String getDanishDayFormat(){
        adjustStrings();
        return day + "/" + month + "/" + year;
    }

    public String getTimeFormat(){
        adjustStrings();
        return hour + ":" + minute;
    }


    class DateTimeException extends Exception{
        public DateTimeException(String message){
            super(message);
        }
    }
}
