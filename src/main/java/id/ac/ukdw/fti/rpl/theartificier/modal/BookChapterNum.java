package id.ac.ukdw.fti.rpl.theartificier.modal;

public class BookChapterNum {
    
    private String book;
    private String chapter;
    private int num;
    private String verse;
    private String osisRef;

    public BookChapterNum(String book){
        this.book = book;
    }

    public BookChapterNum(String book, String chapter){
        this.book = book;
        this.chapter = chapter;
    }

    public BookChapterNum(String book, int num){
        this.book = book;
        this.num = num;
    }

    public BookChapterNum(int num, String verse){
        this.num = num;
        this.verse = verse;
    }

    public BookChapterNum(String osisRef, int num , String verse){
        this.osisRef = osisRef;
        this.num = num;
        this.verse = verse;
    }

    public String getBook() {
        return book;
    }


    public String getChapter() {
        return chapter;
    }


    public int getNum() {
        return num;
    }

    public String getVerse(){
        return verse;
    }
    
    public String getOsisRef(){
        return osisRef;
    }

}
