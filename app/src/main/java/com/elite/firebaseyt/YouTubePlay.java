package com.elite.firebaseyt;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elite.firebaseyt.Main.ButtomNavUpload;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YtFile;

import static com.elite.firebaseyt.DetailListAdapter.getkey;
import static com.elite.firebaseyt.DetailListAdapter.id;
import static com.elite.firebaseyt.Main.ButtomNavUpload.value1;
import static com.elite.firebaseyt.Main.ButtomNavUpload.value2;
import static com.elite.firebaseyt.Main.ButtomNavUpload.viewcount;

public class YouTubePlay extends YouTubeBaseActivity {

    private static final String FOLDER_NAME = "New Folder";
    YouTubePlayer.OnInitializedListener onInitializedListener;
    ImageView b1;
    Uri uri;
    String VideoName;
    ProgressDialog progressDialog;
    public static String downloadUrl;

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;

    TextView view;
    String viewresult;

    ImageView favorite, favoriteadded;
    public static ArrayList<String> favoritelist = new ArrayList<String>();
    public static String abc;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    LinearLayout more;

    //For ProgressBar
    LinearLayout abcde;
    ProgressBar progressBar1;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_play);

        //For ProgressBar
        abcde = findViewById(R.id.download_progress_layout);
        progressBar1 = findViewById(R.id.progressBar1);
        status = findViewById(R.id.output1);


        //For ProgressBar Finish
        YouTubePlayerView player = findViewById(R.id.player);
        recyclerView = findViewById(R.id.recycleview_relateddata);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        final DetailListAdapter adapter = new DetailListAdapter(YouTubePlay.this, ButtomNavUpload.key, value1, value2, viewcount);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        view = findViewById(R.id.view);

        viewresult = viewcount.get(id);
        view.setText(viewresult);

        more = findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openbuttom();
            }
        });

        favorite = findViewById(R.id.favorite);
        favoriteadded = findViewById(R.id.favoriteadded);

        final String onefavorite = getkey;
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorite.setVisibility(View.GONE);
                favoriteadded.setVisibility(View.VISIBLE);
                favoritelist.add(onefavorite);
                Log.i("YT ", "Favoritelist " + favoritelist);
            }
        });

        favoriteadded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteadded.setVisibility(View.GONE);
                favorite.setVisibility(View.VISIBLE);
                favoritelist.remove(onefavorite);
            }
        });

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                VideoName = value1.get(id);
                Log.i("YT ID", "ID " + id);
                uri = Uri.parse(value2.get(id));
                abc = uri.getLastPathSegment();
                youTubePlayer.loadVideo(abc);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        player.initialize(Apikey.key, onInitializedListener);
    }

    public void convert(View v) {

        abcde.setVisibility(View.VISIBLE);
        final String youtubeLink = String.valueOf(uri);

        if ((youtubeLink != null)
                && (youtubeLink.contains("://youtu.be/") || youtubeLink.contains("youtube.com/watch?v="))) {

            final String filename = youtubeLink;
            final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

            new at.huber.youtubeExtractor.YouTubeExtractor(YouTubePlay.this) {
                @Override
                public void onExtractionComplete(SparseArray<YtFile> sparseArray, VideoMeta videoMeta) {
                    if (sparseArray != null) {
                        int itag = 22;
                        try {
                            downloadUrl = sparseArray.get(itag).getUrl();
                            Log.i("URl", "URL Data " + downloadUrl);
//                        new RetrieveFeedTask().execute(downloadUrl);
//                        downloadFile(downloadUrl);
                            new DownloadFilesTask().execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Video Not Downloadable - 1", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }.extract(youtubeLink, true, true);

        } else {
            Context context = getApplicationContext();
            CharSequence text = "Bad URL!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadFilesTask extends AsyncTask<URL, byte[], String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
//            progressDialog = new ProgressDialog(YouTubePlay.this);
//            progressDialog.setMessage("Please Wait");
//            progressDialog.setCancelable(false);
//            progressDialog.setMax(100);
//            progressDialog.setProgress(0);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progressDialog.show();

            status.setText("Downloading..");
        }

        protected String doInBackground(URL... urls) {
            // code that will run in the background

            URL u = null;
            InputStream is = null;
            String abc = null;
            try {
                abc = Uri.decode(downloadUrl);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Video Not Downloadable - 2", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            try {
                u = new URL(abc);
                is = u.openStream();
                HttpURLConnection huc = (HttpURLConnection) u.openConnection(); //to know the size of video
                int size = huc.getContentLength();

                if (huc != null) {
                    String fileName = VideoName + ".mp4";
//                    String storagePath = Environment.getExternalStorageDirectory().toString();

                    String path = Environment.getExternalStorageDirectory() + "/" + "FireBaseYT/";
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }


                    File f = new File(path, fileName);

                    Log.i("Data", "stotagepath " + path);
                    Log.i("Data", "f  " + f);

                    FileOutputStream fos = new FileOutputStream(f);
                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    if (is != null) {
                        while ((len1 = is.read(buffer)) > 0) {
                            fos.write(buffer, 0, len1);
                        }
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }
            } catch (MalformedURLException mue) {
                mue.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException ioe) {
                    // just going to ignore this one
                }
            }
            return "xyz";
        }


        @SuppressLint("SetTextI18n")
        protected void onProgressUpdate(byte[]... values) {
//            progressDialog.setProgress(progress[0]);
            Log.i("onProgressUpdate", "onProgressUpdate");
            status.setText("Running..." + values[0]);
//            progressBar1.setProgress(progress[0]);
        }

        protected void onPostExecute(String result) {
//            progressDialog.dismiss();
            abcde.setVisibility(View.GONE);
            Toast.makeText(YouTubePlay.this, "Video Downloaded", Toast.LENGTH_SHORT).show();
            // update the UI after background processes completes
        }
    }

    private void openbuttom() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(YouTubePlay.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.buttomlayout_more, null);


        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
    }

}

