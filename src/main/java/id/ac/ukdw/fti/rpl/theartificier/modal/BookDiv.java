package id.ac.ukdw.fti.rpl.theartificier.modal;

public class BookDiv {
    private String bookDiv;
    private int verseTotal;
    private String testament;

    public BookDiv(String bookDiv, int verseTotal, String testament){
        this.bookDiv = bookDiv;
        this.verseTotal = verseTotal;
        this.testament = testament;
    }
    public String getBookDiv() {
        return bookDiv;
    }
    public int getVerseTotal() {
        return verseTotal;
    }
    public String getTestament(){
        return testament;
    }
}

