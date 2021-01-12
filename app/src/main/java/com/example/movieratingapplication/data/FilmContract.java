package com.example.movieratingapplication.data;
/*What is a contract class?
1. Define schema and have a convention for where to find database constants
2. pre-empty typo mistakes
3. easy of updating database schema*/

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

//1.What is the name of table?
//2. what are names and data types of the columns?
public class FilmContract {
    /*1. outer class named BlankContract
     * 2. Inner class named BlankEntry for each table in the db.
     *    Each of these inner classes should implement a class called BaseColumns.
     * 3. String constants for table name and for each of the headings*/
    //To prevent someone from accidentally instantiating the contract class,
    //give it an empty constructor
    private FilmContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.movieratingapplication";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FILMS = "films";


    public static final class FilmEntry implements BaseColumns {
        //Possible path appended to base content URI for possible URI's
        //Content://com.example.movieratingapplication/films/
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FILMS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FILMS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single film.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FILMS;

        public final static String TABLE_NAME = "films";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_IMDB = "imdb";
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_COUNTRY = "country";
        public final static String COLUMN_IMAGE_URL = "imageURL";
        public final static String COLUMN_YEAR = "year";
        public final static String COLUMN_SYNOPSIS = "synopsis";
        public final static String COLUMN_RELEASE = "release";
        public final static String COLUMN_DIRECTOR = "director";
        public final static String COLUMN_DIR_URL = "dirURL";
        public final static String COLUMN_MAIN = "main";
        public final static String COLUMN_MAIN_URL = "mainURL";
        public final static String COLUMN_SUPPORT = "support";
        public final static String COLUMN_SUPPORT_URL = "supportUrl";
        public final static String COLUMN_VIDEO_ID = "videoId";
        public final static String COLUMN_VIDEO_URL = "videoUrl";

    }
}
