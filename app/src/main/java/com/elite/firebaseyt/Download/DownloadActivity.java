package com.elite.firebaseyt.Download;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.elite.firebaseyt.R;

import java.io.File;
import java.util.ArrayList;

public class DownloadActivity extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;

    public static final String FOLDER_NAME = "/FireBaseYT/";
    public static ArrayList<String> Download = new ArrayList<String>();
    private static File[] Videofiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        context = DownloadActivity.this;

        GetVideoData();
        recyclerView = findViewById(R.id.recycleview_video);
        gridLayoutManager = new GridLayoutManager(context, 1);
        final VideoAdapter adapter = new VideoAdapter(context, Download);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public static void GetVideoData() {
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + FOLDER_NAME;
        File targetDirector = new File(targetPath);
        Videofiles = targetDirector.listFiles();

        try {
            if (Videofiles.length != 0) {
                for (int ii = 0; ii < Videofiles.length; ii++) {
                    String fileOutput = Videofiles[ii].toString();
                    Log.i("Data Download","FileOutput"+fileOutput);
                    Download.add(fileOutput);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
