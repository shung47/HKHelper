package com.example.shung47.hkhelper;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class TaskActivity extends AppCompatActivity {
    private  static final String TAG ="TaskActivity";
    private FirebaseAuth.AuthStateListener mAuthListener;
    ArrayList<String>list = new ArrayList<>();
    ArrayList<Map>roomList=new ArrayList<>();
    //List<String>userRoomNumList= new ArrayList<>();
    String[] userRoomNumList = new String[]{};
    String[] a = {"1","2"};

    ListView listView;
    ArrayAdapter<String> adapter;
    private  Button buttonSetup;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef,userRef;
    private String userID;
    private Adapter myAdapter;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);



        listView =  findViewById(R.id.roomNumList);
        //CustomAdapter customAdapter = new CustomAdapter();
        //listView.setAdapter();


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference().child("rooms");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        userRef= mFirebaseDatabase.getReference().child("users").child(userID);

        myAdapter = new BaseAdapter() {
            @Override
            public int getCount() {

                return userRoomNumList.length;
                //return a.length;

            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = getLayoutInflater().inflate(R.layout.customlayout,null);
                TextView textName = view.findViewById(R.id.textView);
                TextView textRoomStatus = view.findViewById(R.id.room_status);


                //textName.setText(userRoomNumList[i]);
                textName.setText(userRoomNumList[i]);
                //textRoomStatus.setText(roomList<>[i]);



                return view;
            }
        };


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Log.d(TAG,"onAuthStateChange:signed in:"+user.getUid());


                }else{
                    Log.d(TAG, "OnAuthStateChanged:signed_out");
                }
            }
        };

       mRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

                readData(dataSnapshot);

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

       userRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {


                   UserInfo user = dataSnapshot.getValue(UserInfo.class);
                   String userTask = user.roomNumber;
                   //userRoomNumList = Arrays.asList(userTask.split(",")).toArray(new String[0]);
                   userRoomNumList = userTask.split(",");
                   Log.d(TAG, "msgHere" + userRoomNumList.length);





           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });



        buttonSetup = findViewById(R.id.setting);
        buttonSetup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(TaskActivity.this,LoginActivity.class);
                        startActivity(intent);

                        finish();


                    }
                }
        );
/*
        final Button startBtn = view.findViewById(R.id.startButton);
        final Button finishBtn = view.findViewById(R.id.finishButton);
        finishBtn.setEnabled(false);
        Log.d(TAG, "msgHereeeeee");
        startBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finishBtn.setEnabled(true);
                startBtn.setEnabled(false);


                notifyDataSetChanged();
            }
        });
        finishBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                notifyDataSetChanged();
            }
        });*/
    }


    private void readData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            Room room = ds.getValue(Room.class);
            //room.roomId=ds.child("room").child("ID").getValue(Room.class);

            Map<String, Object> roomData= room.toMap();
            roomList.add(roomData);


        }
    }
    /*
    class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {

            return userRoomNumList.length;
            //return a.length;

        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout,null);
            TextView textName = view.findViewById(R.id.textView);
            TextView textRoomStatus = view.findViewById(R.id.room_status);


            //textName.setText(userRoomNumList[i]);
            textName.setText(userRoomNumList[i]);
            //textRoomStatus.setText(roomList<>[i]);



            return view;
        }

    }
*/

    @Override
    public void onStart(){
        super.onStart();

    }
    @Override
    public void onStop(){
        super.onStop();
    }
}
