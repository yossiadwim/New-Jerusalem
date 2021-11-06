
package id.ac.ukdw.fti.rpl.theartificier.modal;

public class EventHandle{
    private String title;
    private int startDate;
    private String duration;
    private String verses;

    public EventHandle(String title, int starDate, String duration, String verses){
        this.title = title;
        this.startDate = starDate;
        this.duration = duration;
        this.verses = verses;

    }
    
    public String getTitle(){
        return title;
        
    }
    public int getStartDate(){
        return startDate;
    }
    public String getDuration(){
        return duration;
    }
    public String getVerses(){
        return verses;

    }
}