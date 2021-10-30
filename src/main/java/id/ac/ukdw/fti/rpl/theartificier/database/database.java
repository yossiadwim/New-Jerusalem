package id.ac.ukdw.fti.rpl.theartificier.database;

import id.ac.ukdw.fti.rpl.theartificier.modal.eventHandle;
import id.ac.ukdw.fti.rpl.theartificier.modal.versesAndCount;
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
public class database {
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
//        String sql = "select * from places limit 1;";
        String query = "SELECT verses, verseCount from places WHERE displayTitle = '"+title+"'";
        System.out.println(query);
        ArrayList<versesAndCount> hasil = new ArrayList<versesAndCount>();
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            
            // loop through the result set
            while (rs.next()) {
//                subHasil.add(rs.getString("verses"));
//                subHasil.add(rs.getInt("verseCount"));
                hasil.add(new versesAndCount(rs.getString("Verses"), rs.getInt("verseCount")));
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
        System.out.println(query);

        ArrayList <eventHandle> hasil = new ArrayList<eventHandle>();
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(query)){
    
            while (rs.next()) {
                if(rs.getString("title").toUpperCase().contains(title.toUpperCase())){
                    hasil.add(new eventHandle (rs.getString("title"),rs.getInt("startDate"),rs.getString("duration"),rs.getString("verses")));
                }
                else{
                    String[] splitTitle = rs.getString("title").split(" ");
                    for (String judul : splitTitle) {
                        if(judul.equalsIgnoreCase(title)){
                            hasil.add(new eventHandle (rs.getString("title"),rs.getInt("startDate"),rs.getString("duration"),rs.getString("verses")));
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

    public versesAndCount viewVisualisasiUtama(String ayat){
        String query = "select people, peopleCount, places, placesCount from verses where osisRef = '" + ayat +"'";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            // while (rs.next()) {
            //     hasil.add(new versesAndCount(rs.getString("people"), rs.getInt("peopleCount"), rs.getString("places"), rs.getInt("placesCount")));
            // }
            return new versesAndCount(rs.getString("people"), rs.getInt("peopleCount"), rs.getString("places"), rs.getInt("placesCount"));
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
            return rs.getString("verseText");
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
            return rs.getString("verseText");
        } 
        catch (SQLException e) {
            return e.getMessage();
        }
    }
//    public static void main(String[] args){
//        dbnya a = new dbnya();
//        a.connect();
//    }
}
