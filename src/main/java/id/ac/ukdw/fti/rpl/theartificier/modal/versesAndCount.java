package id.ac.ukdw.fti.rpl.theartificier.modal;

public class versesAndCount {
    private String verses;
    private int verseCount;
    public versesAndCount(String verses, int verseCount){
        this.verses = verses;
        this.verseCount = verseCount;
    }
    public String getVerses(){
        return this.verses;
    }
    public int getVerseCount(){
        return verseCount;
    }
}
