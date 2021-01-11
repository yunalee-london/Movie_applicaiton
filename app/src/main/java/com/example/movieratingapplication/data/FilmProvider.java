package com.example.movieratingapplication.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FilmProvider extends ContentProvider {

    public static final String LOG_TAG = FilmProvider.class.getSimpleName();
    //make it global variable so it can be referenced from other ContentProvide methods.
    private FilmDbHelper mDbHelper;


    //create uri matcher
    private static final int FILMS = 100;
    private static final int FILM_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(FilmContract.CONTENT_AUTHORITY, FilmContract.PATH_FILMS, FILMS);
        sUriMatcher.addURI(FilmContract.CONTENT_AUTHORITY, FilmContract.PATH_FILMS + "/#", FILM_ID);

    }

    @Override
    //onCreate is called to initialize the provider and Dbhelper
    public boolean onCreate() {
        mDbHelper = new FilmDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        //1. get Database Object
        //2. URIMatcher
        //3. FILMS case or FILM_ID case
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case FILMS:
                cursor = db.query(FilmContract.FilmEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case FILM_ID:
                selection = FilmContract.FilmEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(FilmContract.FilmEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        //Set notification URI on the Cursor,
        //so we know what content URI the Cursor was created for.
        //If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FILMS:
                return FilmContract.FilmEntry.CONTENT_ITEM_TYPE;
            case FILM_ID:
                return FilmContract.FilmEntry.CONTENT_LIST_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);

        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FILMS:
                insertFilm(uri, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return uri;
    }

    private Uri insertFilm(Uri uri, ContentValues values) {

        String title = values.getAsString(FilmContract.FilmEntry.COLUMN_TITLE);
        if (title == null) {
            throw new IllegalArgumentException("film requires a title");
        }

        String imdb = values.getAsString(FilmContract.FilmEntry.COLUMN_IMDB);
        if (imdb == null) {
            throw new IllegalArgumentException("film requires a title");
        }

        String country = values.getAsString(FilmContract.FilmEntry.COLUMN_COUNTRY);
        if (country == null) {
            throw new IllegalArgumentException("film requires a country");
        }

        String year = values.getAsString(FilmContract.FilmEntry.COLUMN_YEAR);
        if (year == null) {
            throw new IllegalArgumentException("film requires a year");
        }

        String poster = values.getAsString(FilmContract.FilmEntry.COLUMN_IMAGE_URL);
        if (poster == null) {
            throw new IllegalArgumentException("film requires a poster url");
        }

        String synopsis = values.getAsString(FilmContract.FilmEntry.COLUMN_SYNOPSIS);
        if (synopsis == null) {
            throw new IllegalArgumentException("film requires a synopsis");
        }

        String releaseDate = values.getAsString(FilmContract.FilmEntry.COLUMN_RELEASE);
        if (releaseDate == null) {
            throw new IllegalArgumentException("Please pick a release date");
        }

        String director = values.getAsString(FilmContract.FilmEntry.COLUMN_DIRECTOR);
        if (director == null) {
            throw new IllegalArgumentException("film requires a director");
        }

        String mainAct = values.getAsString(FilmContract.FilmEntry.COLUMN_MAIN);
        if (mainAct == null) {
            throw new IllegalArgumentException("film requires a main act");
        }

        String supportAct = values.getAsString(FilmContract.FilmEntry.COLUMN_SUPPORT);
        if (supportAct == null) {
            throw new IllegalArgumentException("film requires a support act");
        }

        String dirPic = values.getAsString(FilmContract.FilmEntry.COLUMN_DIR_URL);
        if (dirPic == null) {
            throw new IllegalArgumentException("film requires a director url");
        }

        String mainPic = values.getAsString(FilmContract.FilmEntry.COLUMN_MAIN_URL);
        if (mainPic == null) {
            throw new IllegalArgumentException("film requires a url");
        }

        String supportPic = values.getAsString(FilmContract.FilmEntry.COLUMN_SUPPORT_URL);
        if (supportPic == null) {
            throw new IllegalArgumentException("film requires a url");
        }

        String videoId = values.getAsString(FilmContract.FilmEntry.COLUMN_VIDEO_ID);
        if (videoId == null) {
            throw new IllegalArgumentException("film requires a url");
        }



        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new film with the given values
        long id = database.insert(FilmContract.FilmEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for---------------------------- " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FILMS:
                rowsDeleted = db.delete(FilmContract.FilmEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case FILM_ID:
                selection = FilmContract.FilmEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(FilmContract.FilmEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        //if 1 or more rows were deleted, then notify all listeners that the data
        //at the given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FILMS:
                return updateFilm(uri, values, selection, selectionArgs);
            case FILM_ID:
                selection = FilmContract.FilmEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateFilm(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }

    }

    private int updateFilm(@NonNull Uri uri, @Nullable ContentValues values,
                           @Nullable String selection, @Nullable String[] selectionArgs) {

        if (values.containsKey(FilmContract.FilmEntry.COLUMN_TITLE)) {
            String title = values.getAsString(FilmContract.FilmEntry.COLUMN_TITLE);
            if (title == null) {
                throw new IllegalArgumentException("film requires a title");
            }
        }

        if (values.containsKey(FilmContract.FilmEntry.COLUMN_COUNTRY)) {
            String title = values.getAsString(FilmContract.FilmEntry.COLUMN_COUNTRY);
            if (title == null) {
                throw new IllegalArgumentException("film requires a country");
            }
        }

        if (values.containsKey(FilmContract.FilmEntry.COLUMN_IMAGE_URL)) {
            String title = values.getAsString(FilmContract.FilmEntry.COLUMN_IMAGE_URL);
            if (title == null) {
                throw new IllegalArgumentException("film requires a poster url");
            }
        }

        if (values.containsKey(FilmContract.FilmEntry.COLUMN_SYNOPSIS)) {
            String title = values.getAsString(FilmContract.FilmEntry.COLUMN_SYNOPSIS);
            if (title == null) {
                throw new IllegalArgumentException("film requires a synopsis");
            }
        }

        if (values.containsKey(FilmContract.FilmEntry.COLUMN_RELEASE)) {
            String title = values.getAsString(FilmContract.FilmEntry.COLUMN_RELEASE);
            if (title == null) {
                throw new IllegalArgumentException("Please pick a release date");
            }
        }

        if (values.containsKey(FilmContract.FilmEntry.COLUMN_DIRECTOR)) {
            String title = values.getAsString(FilmContract.FilmEntry.COLUMN_DIRECTOR);
            if (title == null) {
                throw new IllegalArgumentException("film requires a director");
            }
        }

        if (values.containsKey(FilmContract.FilmEntry.COLUMN_MAIN)) {
            String title = values.getAsString(FilmContract.FilmEntry.COLUMN_MAIN);
            if (title == null) {
                throw new IllegalArgumentException("film requires a main act");
            }
        }

        if (values.containsKey(FilmContract.FilmEntry.COLUMN_SUPPORT)) {
            String title = values.getAsString(FilmContract.FilmEntry.COLUMN_SUPPORT);
            if (title == null) {
                throw new IllegalArgumentException("film requires a support act");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Perform the update on the db and get the number of rows affected
        int rowsUpdated = db.update(FilmContract.FilmEntry.TABLE_NAME, values, selection,
                selectionArgs);

        // if 1 or more rows were updated, then notify all listeners that the data
        // at the given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        //return the number of rows affected
        return rowsUpdated;
    }
}
