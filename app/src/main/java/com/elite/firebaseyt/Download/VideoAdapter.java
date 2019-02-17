package com.elite.firebaseyt.Download;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.elite.firebaseyt.R;
import com.khizar1556.mkvideoplayer.MKPlayerActivity;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    Context context;
    public ArrayList<String> Download = new ArrayList<String>();

    public VideoAdapter(Context context, ArrayList<String> Download) {
        this.context = context;
        this.Download = Download;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.videocard, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        final File videoFile = new File(Download.get(i));
//        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        String filename = FilenameUtils.getName(String.valueOf(videoFile));
        myViewHolder.name_card_download.setText(filename);
        Bitmap myBitmap = ThumbnailUtils.createVideoThumbnail(videoFile.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);

        myViewHolder.image.setImageBitmap(myBitmap);
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(String.valueOf(videoFile));
                file.delete();
                Download.remove(i);
                notifyDataSetChanged();
            }
        });
        myViewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.parse(String.valueOf(videoFile)), "video/*");
//                context.startActivity(Intent.createChooser(intent, "Complete action using"));
                MKPlayerActivity.configPlayer((Activity) context).play(String.valueOf(videoFile));
            }
        });
    }

    @Override
    public int getItemCount() {
        return Download.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image, delete;
        TextView name_card_download;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_download);
            delete = itemView.findViewById(R.id.delete_download);
            name_card_download = itemView.findViewById(R.id.name_card_download);
        }
    }
}
