package com.elite.firebaseyt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    TextInputEditText name, url;
//    Button submit;
//    ArrayList<String> abc = new ArrayList<String>();
//
//    public static ArrayList<String> key = new ArrayList<String>();
//    public static ArrayList<String> value1 = new ArrayList<String>();
//    public static ArrayList<String> value2 = new ArrayList<String>();
//
//
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference, databaseReference2;
//    Data db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }























//        name = findViewById(R.id.name);
//        url = findViewById(R.id.url);
//        submit = findViewById(R.id.submit);
//
//
//        FirebaseApp.initializeApp(this);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("Data");
//        databaseReference2 = databaseReference.child("Data");
//        db = new Data(name, url);

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insert();
//                read();
//                startActivity(new Intent(MainActivity.this, DetailList.class));
//            }
//        });


//    private void getValues() {
//        db.setName(name.getText().toString());
//        db.setUrl(url.getText().toString());
//    }
//
//    public void insert() {
//        String id = databaseReference.push().getKey();
//        getValues();
//        databaseReference.child(id).setValue(db);
//        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
//    }

//    public void read() {
//        key.clear();
//        value1.clear();
//        value2.clear();
//        databaseReference.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                key.clear();
//                value1.clear();
//                value2.clear();
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    Log.i("data", "Key " + child.getKey());
//                    Log.i("data", "Value1 " + child.child("name").getValue());
//                    Log.i("data", "name " + child.child("url").getValue());
//
//                    key.add(child.getKey());
//                    value1.add(String.valueOf(child.child("name").getValue()));
//                    value2.add(String.valueOf(child.child("url").getValue()));
//                }
//
//                Log.i("DataMain", "key " + key);
//                Log.i("DataMain", "value1 " + value1);
//                Log.i("DataMain", "value2 " + value2);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//        });
//    }

//    public void update() {
//        databaseReference.child("-LWkE59nOOtTSj_d-8sk").child("id").setValue("56");
//        databaseReference.child("-LWkE59nOOtTSj_d-8sk").removeValue();
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        key.clear();
//        value1.clear();
//        value2.clear();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        key.clear();
//        value1.clear();
//        value2.clear();
//    }
}
