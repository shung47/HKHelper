package com.example.shung47.hkhelper;
import java.util.HashMap;
import java.util.Map;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Exclude;




@IgnoreExtraProperties
public class Room {
    public String ID;
    public String status;
    //private String time;
    public Map<String, String> stars = new HashMap<>();


    public Room(){

    }

    public Room(String ID, String status){
        this.ID=ID;
        this.status=status;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ID", ID);
        result.put("status", status);


        return result;
    }

}
