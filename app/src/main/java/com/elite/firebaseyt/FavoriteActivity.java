package com.elite.firebaseyt;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.elite.firebaseyt.Main.ButtomNavUpload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.elite.firebaseyt.Main.ButtomNavUpload.value1;
import static com.elite.firebaseyt.Main.ButtomNavUpload.value2;
import static com.elite.firebaseyt.Main.ButtomNavUpload.viewcount;
import static com.elite.firebaseyt.Main.CategoryClick.catkey;
import static com.elite.firebaseyt.YouTubePlay.favoritelist;

public class FavoriteActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    final ArrayList<String> favkey = new ArrayList<String>();
    final ArrayList<String> favvalue1 = new ArrayList<String>();
    final ArrayList<String> favvalue2 = new ArrayList<String>();
    final ArrayList<String> favviewcount = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        backgroung();
        recyclerView = findViewById(R.id.recycleview_favorite);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        final DetailListAdapter adapter = new DetailListAdapter(FavoriteActivity.this, favkey, favvalue1, favvalue2,favviewcount);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void backgroung() {


        for (int i = 0; i <favoritelist.size(); i++) {
            final String abc = favoritelist.get(i);



            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Data");

            Query query = FirebaseDatabase.getInstance().getReference("Data")
                    .equalTo(abc);

            Log.i("Query","Query "+query);

            databaseReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    favkey.clear();
                    favvalue1.clear();
                    favvalue2.clear();
                    favviewcount.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Log.i("Category data", "Key " + child.getKey());
                        Log.i("Category data", "Value1 " + child.child("name").getValue());
                        Log.i("Category data", "name " + child.child("url").getValue());

                        if(child.getKey() == abc) {
                            favkey.add(child.getKey());
                            favvalue1.add(String.valueOf(child.child("name").getValue()));
                            favvalue2.add(String.valueOf(child.child("url").getValue()));
                            favviewcount.add(String.valueOf(child.child("view").getValue()));
                        }
                    }

                    gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    final DetailListAdapter adapter = new DetailListAdapter(FavoriteActivity.this, favkey, favvalue1, favvalue2, favviewcount);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("Favorite DataMain ", "key " + favkey);
                    Log.i("Favorite DataMain ", "value1 " + favvalue1);
                    Log.i("Favorite DataMain ", "value2 " + favvalue2);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }
    }
}
