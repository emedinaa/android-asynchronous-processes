package com.emedinaa.appasync.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.emedinaa.appasync.R;
import com.emedinaa.appasync.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by emedinaa on 5/08/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>  {

    private final String ASSET_PATH="file:///android_asset/";
    private final List<Movie> data;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_movie_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.imageViewMovie
        String fileName = ASSET_PATH+"images/" + data.get(position).getImage();
        Picasso.with(holder.imageViewMovie.getContext()).load(fileName).into(holder.imageViewMovie);
    }

    public MovieAdapter(List<Movie> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewMovie;
        public ViewHolder(View view) {
            super(view);
            imageViewMovie = view.findViewById(R.id.imageViewMovie);
        }
    }


}
