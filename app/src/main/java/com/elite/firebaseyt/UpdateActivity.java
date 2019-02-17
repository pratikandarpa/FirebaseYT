package com.elite.firebaseyt;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;
import java.util.Objects;

import static com.elite.firebaseyt.DetailListAdapter.position;

public class UpdateActivity extends AppCompatActivity {

    EditText name_update, url_update;
    Button submit_update;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_update = (EditText) findViewById(R.id.name_update);
        url_update = (EditText) findViewById(R.id.url_update);
        submit_update = findViewById(R.id.submit_update);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Data");

        Uri uri = Uri.parse("https://youtu.be/DYYtuKyMtY8");
        String token = uri.getLastPathSegment();

        String abc = "https://www.youtube.com/watch?v="+token+"&feature=youtu.be";
        Log.i("data","URL"+abc);
        String imgUrl = "http://img.youtube.com/vi/"+token + "/0.jpg";
        Log.i("data","URL"+imgUrl);

        submit_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(position).child("name").setValue(name_update.getText().toString());
                databaseReference.child(position).child("url").setValue(url_update.getText().toString());
                startActivity(new Intent(UpdateActivity.this, DetailList.class));
            }
        });
    }
}
