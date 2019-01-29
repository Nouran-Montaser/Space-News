package com.example.nouran.space.adapter;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nouran.space.Data.News;
import com.example.nouran.space.Data.Videos;
import com.example.nouran.space.NewsDetailActivity;
import com.example.nouran.space.R;
import com.example.nouran.space.VideoActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.myHolder> {

    private Context context;
    private ArrayList<Videos> exploreImgList;
    public ProgressDialog progressDialog;
    public String imgUrl;

    public ExploreAdapter(Context context, ArrayList<Videos> exploreImgList) {
        this.context = context;
        this.exploreImgList = exploreImgList;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_item, parent, false);
        return new myHolder(view);
    }


    @Override
    public void onBindViewHolder(myHolder holder, final int position) {

        Picasso.get().load(exploreImgList.get(position).getImage()).into(holder.exploreImg);

        imgUrl = exploreImgList.get(position).getImage();

        holder.exploreImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);
                alert.setTitle(exploreImgList.get(position).getNama());
                String[] items = {"Watch Video","Set As Wallpaper Picture"};
                alert.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(context, VideoActivity.class);
                            intent.putExtra("ClickedItem", exploreImgList.get(position).getId());
                            context.startActivity(intent);
                        }
                        else if(which ==1)
                        {
                            Toast.makeText(context, "prograss", Toast.LENGTH_SHORT).show();
//                            Bitmap result= null;
//                            try {
//                                result = Picasso.get().load(imgUrl).get();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
//                            try {
//                                wallpaperManager.setBitmap(result);
//                            } catch (IOException ex) {
//                                ex.printStackTrace();
//                            }
                            new SetWallpaperTask().execute(exploreImgList.get(position).getImage());
//                            Bitmap result=Picasso.with.load(exploreImgList.get(position).getImage())
//                                    .get();
//
//                            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
//                            try {
//                                wallpaperManager.setBitmap(result);
//                            } catch (IOException ex) {
//                                ex.printStackTrace();
//                            }
                            //TODO
                        }
                    }
                });
                alert.show();
                return true;

            }
        });

    }

    @Override
    public int getItemCount() {
        return exploreImgList.size();
    }
    public  class SetWallpaperTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap result= null;
            try {
                Log.i("HHHHHHHHH","here   "+imgUrl);
                result = Picasso.get().load(imgUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            try {
                wallpaperManager.setBitmap(result);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute (Bitmap result) {
            super.onPostExecute(result);

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            try {
                wallpaperManager.setBitmap(result);
//                progressDialog.dismiss();
                Toast.makeText(context, "Set wallpaper successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            Log.i("HHHHHHHHH","here   "+imgUrl);

//            progressDialog = new ProgressDialog(context);
//            progressDialog.setMessage("Please wait...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
        }
    }

    class myHolder extends RecyclerView.ViewHolder {

        ImageView exploreImg;

        public myHolder(final View itemView) {
            super(itemView);
            exploreImg = itemView.findViewById(R.id.explore_img);
        }
    }
}