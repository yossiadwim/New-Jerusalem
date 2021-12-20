package id.ac.ukdw.fti.rpl.theartificier.database;

import id.ac.ukdw.fti.rpl.theartificier.modal.BookChapterNum;
import id.ac.ukdw.fti.rpl.theartificier.modal.BookDiv;
import id.ac.ukdw.fti.rpl.theartificier.modal.Chapter;
import id.ac.ukdw.fti.rpl.theartificier.modal.DataMaps;
import id.ac.ukdw.fti.rpl.theartificier.modal.EventHandle;
import id.ac.ukdw.fti.rpl.theartificier.modal.VersesAndCount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author ACER
 */
public class Database {
    // private final String url = "jdbc:sqlite:vizbible.sqlite";
    
    private final String url = "jdbc:sqlite:vizbible.sqlite";
    private void connect() {  
        Connection conn = null;  
        try {  
            // db parameters  
            // create a connection to the database  
            conn = DriverManager.getConnection(url);  
              
            System.out.println("Connection to SQLite has been established.");  
              
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } 
    }  
    /** 
     * @param args the command line arguments 
     */  
    public ArrayList viewDataPlace(String title){

        String query = "SELECT verses, verseCount from places WHERE displayTitle = '"+title+"'";
        
        ArrayList<VersesAndCount> hasil = new ArrayList<VersesAndCount>();
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            
            // loop through the result set
            while (rs.next()) {
                hasil.add(new VersesAndCount(rs.getString("Verses"), rs.getInt("verseCount")));
            }
            return hasil;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hasil;
    }

    public ArrayList viewDataEvents(String title){
        // ArrayList<versesAndCount> hasil = new ArrayList<versesAndCount>();
        String query = "SELECT events.title, events.startDate, events.duration, events.verses from events WHERE title like '%"+title+"%'"; 
        

        ArrayList <EventHandle> hasil = new ArrayList<EventHandle>();
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(query)){
    
            while (rs.next()) {
                if(rs.getString("title").toUpperCase().contains(title.toUpperCase())){
                    hasil.add(new EventHandle (rs.getString("title"),rs.getInt("startDate"),rs.getString("duration"),rs.getString("verses")));
                }
                else{
                    String[] splitTitle = rs.getString("title").split(" ");
                    for (String judul : splitTitle) {
                        if(judul.equalsIgnoreCase(title)){
                            hasil.add(new EventHandle (rs.getString("title"),rs.getInt("startDate"),rs.getString("duration"),rs.getString("verses")));
                            break;
                        }
                    }
                }
            }
            return hasil;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hasil;
    }
    
    public String viewAyat(String ayat){
        String query = "select verseText from verses where osisRef = '" + ayat +"'";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                return rs.getString("verseText");
            }
            return rs.getString("verseText");
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public VersesAndCount viewVisualisasiUtama(String ayat){
        String query = "select people, peopleCount, places, placesCount from verses where osisRef = '" + ayat +"'";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            // while (rs.next()) {
            //     hasil.add(new versesAndCount(rs.getString("people"), rs.getInt("peopleCount"), rs.getString("places"), rs.getInt("placesCount")));
            // }
            return new VersesAndCount(rs.getString("people"), rs.getInt("peopleCount"), rs.getString("places"), rs.getInt("placesCount"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
     }

     public String ambilNamaPeople(String id){
        String query = "select displayTitle from people where personLookup = '" + id +"'";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                return rs.getString("displayTitle");
            }
            return rs.getString("displayTitle");
        } 
        catch (SQLException e) {
            return e.getMessage();
        }
    }

    
    public String ambilNamaPlaces(String id){
        String query = "select displayTitle from places where placeLookup = '" + id +"'";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                return rs.getString("displayTitle");
            }
            return rs.getString("displayTitle");
        } 
        catch (SQLException e) {
            return e.getMessage();
        }
    }

    public ArrayList ayatHomePage(String book, String pasal, int ayat){
        String query = "SELECT osisRef, verseNum, verseText FROM verses where book = '"+ book+"' AND chapter = '"+ pasal +"'";
        ArrayList<BookChapterNum> hasil = new ArrayList<BookChapterNum>();
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                
                if(rs.getInt("verseNum") >= ayat){ 
                     hasil.add(new BookChapterNum(rs.getString("osisRef"),rs.getInt("verseNum"), rs.getString("verseText")));
                }      
            }
            return hasil; 
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hasil;
    }

    public ArrayList ambilKitab(){
        String query = "SELECT DISTINCT(book) from verses";
        ArrayList<BookChapterNum> book = new ArrayList<BookChapterNum>();

        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt  = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            
            while (rs.next()) {
                book.add(new BookChapterNum(rs.getString("book")));
            }
            return book;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return book;
    }

    public ArrayList ambilPasal(String kitab){
        String query = "SELECT DISTINCT book, chapter from verses WHERE book = '"+ kitab + "'";
        ArrayList<BookChapterNum> chapter = new ArrayList<BookChapterNum>();

        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt  = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            
            while (rs.next()) {
                chapter.add(new BookChapterNum(rs.getString("book"),rs.getString("chapter")));
            }
            return chapter;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return chapter;
    }

    public ArrayList ambilAyat(String pasal){
        String query = "SELECT book, verseNum from verses where chapter = '" + pasal + "'";
        ArrayList<BookChapterNum> num = new ArrayList<BookChapterNum>();

        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt  = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            
            while (rs.next()) {
                num.add(new BookChapterNum(rs.getString("book"),rs.getInt("verseNum")));
            }
            return num;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return num;
    }

    public ArrayList versesFromStart(){
        String query = "SELECT osisRef, verseNum, verseText FROM verses";
        ArrayList<BookChapterNum> verse = new ArrayList<BookChapterNum>();
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt  = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            
            while(rs.next()){
                verse.add(new BookChapterNum(rs.getString("osisRef"),rs.getInt("verseNum"),rs.getString("verseText")));
            }
            return verse;
        }
        catch(SQLException e){
            e.getMessage();
        }
        return verse;
    }

    public ArrayList getAllBooks(){
        String query = "select osisName, bookName, bookDiv, shortName, chapters, chapterCount, verseCount from books";
        ArrayList<BookChapterNum> books = new ArrayList<BookChapterNum>();
        ArrayList<BookChapterNum> temp = new ArrayList<BookChapterNum>();
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt  = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            while(rs.next()){
                // String[] chapterList = rs.getString("chapters").split(",");
                // ArrayList<Chapter> chapters = new ArrayList<Chapter>();
                // for(String ch: chapterList){
                //     chapters.add(new Chapter(ch, getVerseCountChapter(ch)));
                // }
                books.add(new BookChapterNum(rs.getString("bookName"), setBookDiv(rs.getString("bookDiv")), rs.getString("shortName"), rs.getInt("chapterCount"), rs.getInt("verseCount"), getCountBookDiv(rs.getString("bookDiv")), rs.getString("osisName")));
            }
            return books;
        }
        catch(SQLException e){
            e.getMessage();
        }
        return books;
    }

    private int getCountBookDiv(String bookDiv){
        String query = "select count(bookDiv), testament from books where bookDiv = '" + bookDiv + "'";
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt  = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            return rs.getInt("count(bookDiv)");
        }
        catch(SQLException e){
            e.getMessage();
        }
        return 0;
    }
    public BookDiv setBookDiv(String bookDiv){
        String query = "select DISTINCT(bookDiv), testament from books where bookDiv = '" + bookDiv + "'";
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt  = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            return new BookDiv(rs.getString("bookDiv"), getVerseTotalBook(rs.getString("bookDiv")), rs.getString("testament"));
        }
        catch(SQLException e){
            e.getMessage();
        }
        return null;
    }

    public ArrayList getBookDiv(){
        String query = "select DISTINCT(bookDiv), testament from books";
        ArrayList<BookDiv> bookDiv = new ArrayList<BookDiv>();
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt  = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            
            while(rs.next()){
                bookDiv.add(new BookDiv(rs.getString("bookDiv"), getVerseTotalBook(rs.getString("bookDiv")), rs.getString("testament")));
            }
            return bookDiv;
        }
        catch(SQLException e){
            e.getMessage();
        }
        return bookDiv;
    }

    private int getVerseTotalBook(String book){
        String query = "select sum(verseCount) from books where bookDiv = '" + book + "'";
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt  = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            
            return rs.getInt("sum(verseCount)");
        }
        catch(SQLException e){
            e.getMessage();
        }
        return 0;
    }

    private int getVerseCountChapter(String chapter){
        String query = "select count(verseNum) from verses where chapter = '" + chapter + "'";
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt  = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            
            return rs.getInt("count(verseNum)");
        }
        catch(SQLException e){
            e.getMessage();
        }
        return 0;
    }

    public ArrayList dataMaps(){
        String query = "select title, duration, verses, locations from events where locations is not NULL";
        ArrayList<DataMaps> datamap = new ArrayList<DataMaps>();
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt  = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            
            while(rs.next()){
                datamap.add(new DataMaps(rs.getString("title"),rs.getString("duration"),rs.getString("verses"),rs.getString("locations")));
            }
            return datamap;
        }
        catch(SQLException e){
            e.getMessage();
        }
        return datamap;
    }


    public DataMaps ambilNamaPlace(String id){
    
        String query = "select displayTitle, latitude, longitude from places where placeLookup = '" + id +"'";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
                 
            return new DataMaps(rs.getString("displayTitle"),rs.getDouble("latitude"),rs.getDouble("longitude"));
        } 
        catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }


     
}