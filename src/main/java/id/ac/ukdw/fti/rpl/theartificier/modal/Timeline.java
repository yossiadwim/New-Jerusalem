package id.ac.ukdw.fti.rpl.theartificier.modal;


// select title, predecessor, lag, partOf, verses from events where title like "birth%" and partOf like "lifetime%" and lag is not null

// select * from events where partOf like "lifetime%" and title like "death%"  and predecessor = "Creation of Adam and Eve"

import java.util.HashMap;

import id.ac.ukdw.fti.rpl.theartificier.database.Database;

public class Timeline {
    private String predecessor, lagBirth, lagDeath, versesBirth, versesDeath;
    private HashMap<String, String> versesBirthHashMap = new HashMap<String, String>();
    private HashMap<String, String> versesDeathMap = new HashMap<String, String>();
    private Database db = new Database();
    private double birth;
    private double death;

    public Timeline(String predecessor, String lagBirth, String lagDeath, String versesBirth, String versesDeath){
        this.predecessor = predecessor;
        String[] birthSp = lagBirth.split("Y");
        String[] deathSp = lagDeath.split("Y");  
        this.birth = Double.parseDouble(birthSp[0]);
        this.death = Double.parseDouble(deathSp[0]) - this.birth;

        try {
            String[] splitVersesBirth = versesBirth.split(",");
            for(String verse: splitVersesBirth){
                versesBirthHashMap.put(verse, db.viewAyat(verse));
            }
        } catch (Exception e) {
            versesBirthHashMap.put(versesBirth, db.viewAyat(versesBirth));
        }

        try {
            String[] splitVersesDeathMap = versesDeath.split(",");
            for(String verse: splitVersesDeathMap){
                versesDeathMap.put(verse, db.viewAyat(verse));
            }
        } catch (Exception e) {
            versesDeathMap.put(versesDeath, db.viewAyat(versesDeath));
        }
    }

    public String getPredecessor() {
        return predecessor;
    }

    public String getLagBirth() {
        return lagBirth;
    }

    public String getLagDeath() {
        return lagDeath;
    }

    public String getVersesBirth() {
        return versesBirth;
    }

    public String getVersesDeath() {
        return versesDeath;
    }

    public HashMap<String, String> getVersesBirthHashMap() {
        return versesBirthHashMap;
    }

    public HashMap<String, String> getVersesDeathMap() {
        return versesDeathMap;
    }

    public double getBirth() {
        return birth;
    }
    public double getDeath() {
        return death;
    }
}
