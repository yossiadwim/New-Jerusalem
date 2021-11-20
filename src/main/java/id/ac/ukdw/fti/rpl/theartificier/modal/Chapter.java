
package id.ac.ukdw.fti.rpl.theartificier.modal;

public class Chapter {
    private String chapterName;
    private int verseCount;

    public Chapter(String chapterName, int verseCount){
        this.chapterName = chapterName;
        this.verseCount = verseCount;
    }
    public String getChapterName() {
        return chapterName;
    }
    public int getVerseCount() {
        return verseCount;
    }
}