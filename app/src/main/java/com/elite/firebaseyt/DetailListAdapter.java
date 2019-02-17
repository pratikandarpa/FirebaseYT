package com.elite.firebaseyt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.MyViewHolder> {


    Context context;
    public ArrayList<String> key = new ArrayList<String>();
    public ArrayList<String> value1 = new ArrayList<String>();
    public ArrayList<String> value2 = new ArrayList<String>();
    public ArrayList<String> viewcount = new ArrayList<String>();
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    public static String position;
    public static String token;
    public static Uri uri;
    public static int id;

    public static String getkey;

    public DetailListAdapter(DetailList detailList, ArrayList<String> key, ArrayList<String> value1, ArrayList<String> value2, ArrayList<String> viewcount) {
        this.context = detailList;
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
        this.viewcount = viewcount;
    }

    public DetailListAdapter(Context context, ArrayList<String> key, ArrayList<String> value1, ArrayList<String> value2, ArrayList<String> viewcount) {
        this.context = context;
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
        this.viewcount = viewcount;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detailcard, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        uri = Uri.parse(value2.get(i));
        token = uri.getLastPathSegment();
        Log.i("data", "dataURL" + token);

        String abc = "https://www.youtube.com/watch?v=" + token + "&feature=youtu.be";
        String imgUrl = "http://img.youtube.com/vi/" + token + "/0.jpg";


        myViewHolder.name.setText(value1.get(i));
        myViewHolder.url.setText(value2.get(i));

        RequestManager manager = Glide.with(myViewHolder.image);
        manager.load(imgUrl)
                .into(myViewHolder.image);

        myViewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = i;
                getkey = key.get(i);
                int view_result = Integer.parseInt(viewcount.get(i));
                int new_view_result = view_result + 1;
                getVideoInfo();
                notifyDataSetChanged();
                token = null;
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Data");
                databaseReference.child(key.get(i)).child("view").setValue(new_view_result);

                context.startActivity(new Intent(context, YouTubePlay.class));
            }
        });
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                key.get(i);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Data");
                databaseReference.child(key.get(i)).removeValue();

                key.remove(i);
                value1.remove(i);
                value2.remove(i);
                notifyDataSetChanged();
            }
        });

        myViewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = key.get(i);
                context.startActivity(new Intent(context, UpdateActivity.class));

//                firebaseDatabase = FirebaseDatabase.getInstance();
//                databaseReference = firebaseDatabase.getReference("Data");
//
//                databaseReference.child(key.get(i)).child("name").setValue(updated_value1);
//                databaseReference.child(key.get(i)).child("url").setValue(updated_value2);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return key.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image, delete, update;
        TextView name, url;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img);
            delete = itemView.findViewById(R.id.delete);
            update = itemView.findViewById(R.id.update);

            name = itemView.findViewById(R.id.name_card);
            url = itemView.findViewById(R.id.url_card);
        }
    }

    private void getVideoInfo() {

        // volley
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.googleapis.com/youtube/v3/videos?id=" + token + "&key=" +
                        Apikey.key +
                        "&part=snippet,contentDetails,statistics,status",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference("Data");

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("items");

                            JSONObject object = jsonArray.getJSONObject(0);
                            JSONObject snippet = object.getJSONObject("snippet");

                            String title = snippet.getString("title");
                            databaseReference.child(key.get(id)).child("name").setValue(title);
                            Log.d("stuff: ", "" + title);
                            title = null;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        queue.add(stringRequest);

    }
}
