package id.ac.ukdw.fti.rpl.theartificier.modal;

public class versesAndCount {
    private String verses;
    private int verseCount;
    private String people;
    private int peopleCount;
    private String places;
    private int placesCount;

    public versesAndCount(String verses, int verseCount){
        this.verses = verses;
        this.verseCount = verseCount;
    }
    public versesAndCount(String people, int peopleCount, String places, int placesCount){
        this.people = people;
        this.peopleCount = peopleCount;
        this.places = places;
        this.placesCount = placesCount;
    }
    public String getVerses(){
        return this.verses;
    }
    public int getVerseCount(){
        return this.verseCount;
    }
    public String getPeople(){
        return this.people;
    }
    public int getPeopleCount() {
        return peopleCount;
    }
    public String getPlaces() {
        return places;
    }
    public int getPlacesCount() {
        return placesCount;
    }
}