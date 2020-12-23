package com.example.movieratingapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieratingapplication.data.FilmContract;
import com.squareup.picasso.Picasso;

public class FilmCursorAdapter extends CursorAdapter {


    public FilmCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //find individual views that we want to modify in the list item layout
        ImageView imageView = (ImageView) view.findViewById(R.id.poster_list);
        TextView textTitle = (TextView) view.findViewById(R.id.title_list);
        TextView subTitle = (TextView) view.findViewById(R.id.subtitle_list);

        //Find the columns of film attributes that we are interested in.
        int titleColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_TITLE);
        int imageUrlColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_IMAGE_URL);
        int mainColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_MAIN);
        int supportColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_SUPPORT);
        int countryColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_COUNTRY);
        int yearColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_YEAR);
        /*int synopsisColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_SYNOPSIS);
        int relColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_RELEASE);
        int dirColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_DIRECTOR);
        int dirUrlColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_DIR_URL);
        int mainUrlColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_MAIN_URL);
        int suppUrlColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_SUPPORT_URL);*/

        //Read the film attributes from the Cursor for current film
        String filmTitle = cursor.getString(titleColumnIndex);
        String filmImageUrl = cursor.getString(imageUrlColumnIndex);
        String filmMain = cursor.getString(mainColumnIndex);
        String filmSupport = cursor.getString(supportColumnIndex);
        String filmCountry = cursor.getString(countryColumnIndex);
        String filmYear = cursor.getString(yearColumnIndex);
        /*String filmSynopsis = cursor.getString(synopsisColumnIndex);
        String filmRelease = cursor.getString(relColumnIndex);
        String filmDir = cursor.getString(dirColumnIndex);
        String filmDirUrl = cursor.getString(dirUrlColumnIndex);
        String filmMainUrl = cursor.getString(mainUrlColumnIndex);
        String filmSuppUrl = cursor.getString(suppUrlColumnIndex);*/

        //update the Views with the attributes for the current film
        textTitle.setText(filmTitle);
        Picasso.get().load(filmImageUrl).into(imageView);
        subTitle.setText(filmMain+ " & " + filmSupport+", " + filmCountry+ ", "+ filmYear);

    }
}
