package id.ac.ukdw.fti.rpl.theartificier.modal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import id.ac.ukdw.fti.rpl.theartificier.database.Database;
import javafx.scene.chart.PieChart.Data;

public class OlahDataMaps {
    Database db = new Database();
    private HashMap<String,DataMaps> listEvent = new HashMap<>();
    private Set<String> listVerses = new HashSet<>();


    private void addToEvent(String id, String duration, String title){
        DataMaps data = db.ambilNamaPlace(id);
        // listEvent.put(data.getNamaLocation(), new DataMaps(data.getLatitude(), data.getLongitude(), duration, title));
        // if(data.getLatitude() > 0.0 && data.getLongitude() > 0.0){
        //     listEvent.put(data.getNamaLocation(), new DataMaps(data.getLatitude(), data.getLongitude(), duration, title));
        // }

        if(data.getLatitude() > 0.0 && data.getLongitude() > 0.0){
            try {
                listEvent.put(data.getNamaLocation(), listEvent.get(data.getNamaLocation())).setTitleDuration(title, duration);;
            } catch (Exception e) {
                //TODO: handle exception
                listEvent.put(data.getNamaLocation(), new DataMaps(data.getLatitude(), data.getLongitude(), duration, title));
            }
        }
        
        
    }

    public void olahData(ArrayList<DataMaps> dataMaps){
        for (DataMaps data : dataMaps){

            try{
                String[] splitTempat = data.getLocations().split(",");
                for(String tmpt : splitTempat){
                    addToEvent(tmpt,data.getDuration(), data.getTitle());
                } 
            }
            catch(Exception e){
                addToEvent(data.getLocations(), data.getDuration(), data.getTitle());
            }

            try {
                String[] splitAyat = data.getVerses().split(",");
                for (String ayat : splitAyat){
                    listVerses.add(ayat);
                }

            } catch (Exception e) {
                listVerses.add(data.getVerses());
            }

        }
    }
    
    public Set<String> getListVerses() {
        return listVerses;
    }

    public HashMap<String, DataMaps> getListEvent() {
        return listEvent;
    }

    

}




