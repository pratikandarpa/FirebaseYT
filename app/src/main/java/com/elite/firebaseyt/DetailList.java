package com.elite.firebaseyt;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.elite.firebaseyt.Main.ButtomNavUpload.value1;
import static com.elite.firebaseyt.Main.ButtomNavUpload.value2;

public class DetailList extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        context = DetailList.this;

        recyclerView = findViewById(R.id.recycleview_detail);
        gridLayoutManager = new GridLayoutManager(DetailList.this, 1);
//        final DetailListAdapter adapter = new DetailListAdapter(DetailList.this, MainActivity.key, value1, value2);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setAdapter(adapter);
//
//        Log.i("Data", "Key " + key);
//        Log.i("Data", "value1 " + value1);
//        Log.i("Data", "value2 " + value2);
//        new DownloadFilesTask().execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadFilesTask extends AsyncTask<URL, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(DetailList.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        protected String doInBackground(URL... urls) {
//            gridLayoutManager = new GridLayoutManager(DetailList.this, 1);
//            final DetailListAdapter adapter = new DetailListAdapter(DetailList.this, MainActivity.key, value1, value2);
//            recyclerView.setLayoutManager(gridLayoutManager);
//            recyclerView.setAdapter(adapter);
            return "xyz";
        }


        protected void onProgressUpdate(Integer... progress) {
            // receive progress updates from doInBackground
        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            // update the UI after background processes completes
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("data", "OnBackPress DetailList ");
//        key.clear();
//        value1.clear();
//        value2.clear();
    }
}
