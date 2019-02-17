package com.elite.firebaseyt.Main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.elite.firebaseyt.Data;
import com.elite.firebaseyt.DetailList;
import com.elite.firebaseyt.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ButtomNavUpload extends Fragment implements AdapterView.OnItemSelectedListener {

    TextInputEditText name, url;
    Button submit;
    ArrayList<String> abc = new ArrayList<String>();

    public static ArrayList<String> key = new ArrayList<String>();
    public static ArrayList<String> value1 = new ArrayList<String>();
    public static ArrayList<String> value2 = new ArrayList<String>();
    public static ArrayList<String> category = new ArrayList<String>();
    public static ArrayList<String> viewcount = new ArrayList<String>();


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference2;
    Data db;


    String[] Names = {"Love", "Frienship", "Sad", "DJ", "Dance", "Dialogue"};
    int flags[] = {R.drawable.cahands, R.drawable.cafriendship, R.drawable.casad, R.drawable.cadj, R.drawable.caparty, R.drawable.cadialogue};
    String CategoryValue;

    public ButtomNavUpload() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_buttom_nav_upload, container, false);


        name = view.findViewById(R.id.name);
        url = view.findViewById(R.id.url);
        submit = view.findViewById(R.id.submit);

        Spinner spinner = (Spinner) view.findViewById(R.id.Category);
        spinner.setOnItemSelectedListener(this);

        CustomAdapter customAdapter = new CustomAdapter(getContext(), flags, Names);
        spinner.setAdapter(customAdapter);

        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Data");
        databaseReference2 = databaseReference.child("Data");
        db = new Data(name, url);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
                read();
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(parent.getContext(), "" + Names[position], Toast.LENGTH_LONG).show();
        CategoryValue = Names[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void getValues() {
        db.setName(name.getText().toString());
        db.setUrl(url.getText().toString());
        String abc = url.getText().toString();
        if (abc.matches(".*(youtube|youtu.be).*")) {
            Toast.makeText(getContext(), "Valid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Not Valid", Toast.LENGTH_SHORT).show();
        }
        db.setView("0");
        db.setCategory(CategoryValue);
    }

    public void insert() {
        String id = databaseReference.push().getKey();
        getValues();
        databaseReference.child(id).setValue(db);
        Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
    }

    public void read() {
        key.clear();
        value1.clear();
        value2.clear();
        viewcount.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                key.clear();
                value1.clear();
                value2.clear();
                viewcount.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.i("data", "Key " + child.getKey());
                    Log.i("data", "Value1 " + child.child("name").getValue());
                    Log.i("data", "name " + child.child("url").getValue());

                    key.add(child.getKey());
                    value1.add(String.valueOf(child.child("name").getValue()));
                    value2.add(String.valueOf(child.child("url").getValue()));
                    category.add(String.valueOf(child.child("category").getValue()));
                    viewcount.add(String.valueOf(child.child("view").getValue()));
                }

                Log.i("DataMain", "key " + key);
                Log.i("DataMain", "value1 " + value1);
                Log.i("DataMain", "value2 " + value2);
                Log.i("DataMain", "category " + category);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


    @Override
    public void onResume() {
        super.onResume();
        key.clear();
        value1.clear();
        value2.clear();
        viewcount.clear();
    }

    @Override
    public void onStart() {
        super.onStart();
        key.clear();
        value1.clear();
        value2.clear();
        viewcount.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
