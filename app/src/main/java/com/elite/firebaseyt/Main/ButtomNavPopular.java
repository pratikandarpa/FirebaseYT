package com.elite.firebaseyt.Main;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.wang.avi.AVLoadingIndicatorView;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.elite.firebaseyt.Main.ButtomNavUpload.key;
import static com.elite.firebaseyt.Main.ButtomNavUpload.value1;
import static com.elite.firebaseyt.Main.ButtomNavUpload.value2;
import static com.elite.firebaseyt.Main.ButtomNavUpload.viewcount;


public class ButtomNavPopular extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    View view;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AVLoadingIndicatorView Loading;


    public static ArrayList<String> Popularkey = new ArrayList<String>();
    public static ArrayList<String> Popularvalue1 = new ArrayList<String>();
    public static ArrayList<String> Popularvalue2 = new ArrayList<String>();
    public static ArrayList<String> Popularcategory = new ArrayList<String>();
    public static ArrayList<String> Popularviewcount = new ArrayList<String>();

//    HashMap<String, String> meMap = new HashMap<String, String>();
//    Map<String, Integer> lMap = new HashMap<String, Integer>();

    public ButtomNavPopular() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.buttom_nav_user, container, false);

        Loading = view.findViewById(R.id.loding);
        FirebaseApp.initializeApp(getActivity());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Data");

        recyclerView = view.findViewById(R.id.recycleview_popular);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefresh);

        new DownloadFilesTask().execute();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        gridLayoutManager = new GridLayoutManager(getContext(), 1);
                        final DetailListAdapter adapter = new DetailListAdapter(getActivity(), ButtomNavUpload.key, value1, value2, viewcount);
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
            startAnim();
        }

        protected String doInBackground(URL... urls) {
            ButtomNavUpload.key.clear();
            value1.clear();
            value2.clear();
            viewcount.clear();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Data");

            Query query = FirebaseDatabase.getInstance().getReference("Artists")
                    .orderByChild("age")
                    .startAt(20);


            query.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ButtomNavUpload.key.clear();
                    value1.clear();
                    value2.clear();
                    viewcount.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Log.i("data", "Key " + child.getKey());
                        Log.i("data", "Value1 " + child.child("name").getValue());
                        Log.i("data", "name " + child.child("url").getValue());

                        ButtomNavUpload.key.add(child.getKey());
                        value1.add(String.valueOf(child.child("name").getValue()));
                        value2.add(String.valueOf(child.child("url").getValue()));
                        viewcount.add(String.valueOf(child.child("view").getValue()));

                        String newkey = String.valueOf(key);
                        String newviewcount = String.valueOf(child.child("view").getValue());

//                        int myNum = (int) Integer.parseInt(newviewcount);
//
//                        lMap.put(newkey, myNum);
                    }

//                    Log.i("lMap", "lMap " + lMap);
//                    MyComparator comp = new MyComparator(lMap);
//                    Map<String, Integer> newMap = new TreeMap(comp);
//                    newMap.putAll(lMap);
//                    Log.i("NewMap", "NewMap " + newMap);

                    gridLayoutManager = new GridLayoutManager(getContext(), 1);
                    final DetailListAdapter adapter = new DetailListAdapter(getActivity(), ButtomNavUpload.key, value1, value2, viewcount);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("DataMain", "key " + ButtomNavUpload.key);
                    Log.i("DataMain", "value1 " + value1);
                    Log.i("DataMain", "value2 " + value2);
//                    swipeRefreshLayout.setRefreshing(false);
                    stopAnim();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
            return "xyz";
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
        }
    }

    void startAnim() {
        Loading.show();
        // or avi.smoothToShow();
    }

    void stopAnim() {
        Loading.hide();
        // or avi.smoothToHide();
    }

    @Override
    public void onResume() {
        super.onResume();
        new DownloadFilesTask().execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        new DownloadFilesTask().execute();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
