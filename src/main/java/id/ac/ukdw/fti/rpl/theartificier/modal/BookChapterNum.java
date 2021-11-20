package id.ac.ukdw.fti.rpl.theartificier.modal;

import java.util.ArrayList;

public class BookChapterNum {
    
    private String book;
    private String chapter;
    private int num;
    private String verse;
    private String osisRef;

    private BookDiv bookDiv;
    private String shortName;
    private ArrayList<Chapter> chapterList = new ArrayList<Chapter>();
    private int chapterCount;
    private int verseCount;
    private int countBookDiv;
    private String osisName;

    public int getCountBookDiv(){
        return countBookDiv;
    }

    public BookDiv getBookDiv() {
        return bookDiv;
    }
    public String getShortName() {
        return shortName;
    }
    public ArrayList<Chapter> getChapterList() {
        return chapterList;
    }
    public int getChapterCount() {
        return chapterCount;
    }
    public int getVerseCount() {
        return verseCount;
    }
    public String getOsisName(){
        return osisName;
    }
    // public BookChapterNum(String bookName, BookDiv bookDiv, String shortName, ArrayList<Chapter> chapter, int chapterCount, int verseCount){
    //     this.book = bookName;
    //     this.bookDiv = bookDiv;
    //     this.shortName = shortName;
    //     this.chapterList = chapter;
    //     this.chapterCount = chapterCount;
    //     this.verseCount = verseCount;
    // }

    public BookChapterNum(String bookName, BookDiv bookDiv, String shortName, int chapterCount, int verseCount, int countBookDiv, String osisName){
        this.book = bookName;
        this.bookDiv = bookDiv;
        this.shortName = shortName;
        this.chapterCount = chapterCount;
        this.verseCount = verseCount;
        this.countBookDiv = countBookDiv;
        this.osisName = osisName;
    }
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
