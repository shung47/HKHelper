package com.example.shung47.hkhelper;
import java.util.HashMap;
import java.util.Map;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Exclude;

@IgnoreExtraProperties
public class UserInfo {
    public String roomNumber;

    public Map<String, String> stars = new HashMap<>();

    public UserInfo(){

    }

    public UserInfo(String roomNumber){
        this.roomNumber=roomNumber;
    }


    @Exclude
    public  Map<String, Object> ToMap() {
        HashMap<String, Object> rooms = new HashMap<>();
        rooms.put("roomNumber",roomNumber);

        return rooms;
    }
}

