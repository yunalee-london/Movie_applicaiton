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

    public FilmAdapter(@NonNull Context context, @NonNull ArrayList<Film> filmArray) {
        super(context, 0, filmArray);

    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Film currentFilm = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.poster_list);
        String imageUrl = currentFilm.getImage();
        Picasso.get().load(imageUrl).into(imageView);

        TextView textTitle = (TextView) convertView.findViewById(R.id.title_list);
        textTitle.setText(currentFilm.getTitle());

        TextView subTitle = (TextView) convertView.findViewById(R.id.subtitle_list);
        subTitle.setText(currentFilm.getMain()+ " & " + currentFilm.getSupport()+", " + currentFilm.getCountry()+ ", "+currentFilm.getYear());

        return convertView;
    }
}
