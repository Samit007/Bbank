package Model;

public class Event {
    private String  eventid,eventname,eventaddress,eventstarttime,eventendtime,eventdate;

    public Event(String eventid, String eventname, String eventaddress, String eventstarttime, String eventendtime, String eventdate) {
        this.eventid = eventid;
        this.eventname = eventname;
        this.eventaddress = eventaddress;
        this.eventstarttime = eventstarttime;
        this.eventendtime = eventendtime;
        this.eventdate = eventdate;
    }

    public Event(String eventid){
        this.eventid=eventid;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventaddress() {
        return eventaddress;
    }

    public void setEventaddress(String eventaddress) {
        this.eventaddress = eventaddress;
    }

    public String getEventstarttime() {
        return eventstarttime;
    }

    public void setEventstarttime(String eventstarttime) {
        this.eventstarttime = eventstarttime;
    }

    public String getEventendtime() {
        return eventendtime;
    }

    public void setEventendtime(String eventendtime) {
        this.eventendtime = eventendtime;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }
}
