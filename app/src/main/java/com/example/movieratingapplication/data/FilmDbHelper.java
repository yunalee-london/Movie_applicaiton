package com.example.movieratingapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/** Database helper for Movie app. Manages database creation and version management.*/
public class FilmDbHelper extends SQLiteOpenHelper {
    //To change the database schema, must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "film.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "
                    + FilmContract.FilmEntry.TABLE_NAME
                    + "("
                    + FilmContract.FilmEntry._ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + FilmContract.FilmEntry.COLUMN_TITLE
                    + " TEXT NOT NULL, "
                    + FilmContract.FilmEntry.COLUMN_IMDB
                    + " TEXT NOT NULL, "
                    + FilmContract.FilmEntry.COLUMN_COUNTRY
                    + " TEXT NOT NULL,"
                    + FilmContract.FilmEntry.COLUMN_IMAGE_URL
                    + " TEXT NOT NULL, "
                    + FilmContract.FilmEntry.COLUMN_YEAR
                    + " TEXT NOT NULL DEFAULT '', "
                    + FilmContract.FilmEntry.COLUMN_SYNOPSIS
                    + " TEXT NOT NULL, "
                    + FilmContract.FilmEntry.COLUMN_RELEASE
                    + " TEXT NOT NULL, "
                    + FilmContract.FilmEntry.COLUMN_DIRECTOR
                    + " TEXT NOT NULL, "
                    + FilmContract.FilmEntry.COLUMN_DIR_URL
                    + " TEXT NOT NULL, "
                    + FilmContract.FilmEntry.COLUMN_MAIN
                    + " TEXT NOT NULL, "
                    + FilmContract.FilmEntry.COLUMN_MAIN_URL
                    + " TEXT NOT NULL, "
                    + FilmContract.FilmEntry.COLUMN_SUPPORT
                    + " TEXT NOT NULL, "
                    + FilmContract.FilmEntry.COLUMN_VIDEO_ID
                    + " TEXT NOT NULL, "
                    + FilmContract.FilmEntry.COLUMN_VIDEO_URL
                    + " TEXT NOT NULL, "
                    + FilmContract.FilmEntry.COLUMN_SUPPORT_URL
                    + " TEXT NOT NULL);";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + FilmContract.FilmEntry.TABLE_NAME;

    /**
     * Constructs a new instance of {@link FilmDbHelper}.
     * @param context of the app
     */
    public FilmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //this database is only a cache for online data, so its upgrade policy is
        //to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int newVersion, int oldVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
