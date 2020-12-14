package com.example.movieratingapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmAdapter extends ArrayAdapter<Film> {
    private Context mContext;
    private int mResource;

    public FilmAdapter(@NonNull Context context, @NonNull ArrayList<Film> objects) {
        super(context, 0, objects);
        this.mContext = context;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Film film = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.poster_list);

        TextView textTitle = (TextView) convertView.findViewById(R.id.title_list);

        TextView subTitle = (TextView) convertView.findViewById(R.id.subtitle_list);

        String imageUrl = film.getImage();
        Picasso.get().load(imageUrl).into(imageView);

        textTitle.setText(film.getTitle());

        subTitle.setText(film.getMain()+ " & " + film.getSupport()+", " + film.getCountry()+ ", "+film.getYear());

        return convertView;
    }
}
