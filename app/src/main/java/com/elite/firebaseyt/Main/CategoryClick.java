package com.elite.firebaseyt.Main;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elite.firebaseyt.DetailListAdapter;
import com.elite.firebaseyt.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;
import java.util.ArrayList;

import static com.elite.firebaseyt.Main.ButtomNavCategory.onclickposition;
import static com.elite.firebaseyt.Main.ButtomNavUpload.value1;
import static com.elite.firebaseyt.Main.ButtomNavUpload.value2;

public class CategoryClick extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    View view;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference2;

    public static ArrayList<String> catkey = new ArrayList<String>();
    public static ArrayList<String> catvalue1 = new ArrayList<String>();
    public static ArrayList<String> catvalue2 = new ArrayList<String>();
    public static ArrayList<String> catviewcount = new ArrayList<String>();

    String Categoryvalue;

    public CategoryClick() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category_click, container, false);

        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Data");


        new DownloadFilesTask().execute();

        recyclerView = view.findViewById(R.id.recycleview_category);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefresh_category);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        gridLayoutManager = new GridLayoutManager(getContext(), 1);
                        final DetailListAdapter adapter = new DetailListAdapter(getActivity(), ButtomNavUpload.key, value1, value2,catviewcount);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                }, 1000);
            }
        });

        return view;


    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadFilesTask extends AsyncTask<URL, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance

        }

        protected String doInBackground(URL... urls) {
            ButtomNavUpload.key.clear();
            value1.clear();
            value2.clear();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Data");


            if (onclickposition.equals("Love")) {
                Categoryvalue = "Love";
            } else if (onclickposition.equals("Sad")) {
                Categoryvalue = "Sad";
            } else if (onclickposition.equals("Frienship")) {
                Categoryvalue = "Frienship";
            } else if (onclickposition.equals("DJ")) {
                Categoryvalue = "DJ";
            } else if (onclickposition.equals("Dance")) {
                Categoryvalue = "Dance";
            } else if (onclickposition.equals("Dialogue")) {
                Categoryvalue = "Dialogue";
            }


            Query query = FirebaseDatabase.getInstance().getReference("Data")
                    .orderByChild("category")
                    .equalTo(Categoryvalue);

            query.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    catkey.clear();
                    catvalue1.clear();
                    catvalue2.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Log.i("Category data", "Key " + child.getKey());
                        Log.i("Category data", "Value1 " + child.child("name").getValue());
                        Log.i("Category data", "name " + child.child("url").getValue());

                        catkey.add(child.getKey());
                        catvalue1.add(String.valueOf(child.child("name").getValue()));
                        catvalue2.add(String.valueOf(child.child("url").getValue()));
                        catviewcount.add(String.valueOf(child.child("view").getValue()));
                    }

                    gridLayoutManager = new GridLayoutManager(getContext(), 1);
                    final DetailListAdapter adapter = new DetailListAdapter(getActivity(), catkey, catvalue1, catvalue2,catviewcount);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("Category DataMain ", "key " + catkey);
                    Log.i("Category DataMain ", "value1 " + catvalue1);
                    Log.i("Category DataMain ", "value2 " + catvalue2);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
            return "xyz";
        }

        protected void onProgressUpdate(Integer... progress) {
            // receive progress updates from doInBackground
        }

        protected void onPostExecute(String result) {

        }
    }

}
