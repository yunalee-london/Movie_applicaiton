package com.example.movieratingapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.movieratingapplication.data.FilmContract;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    //private static final String requestURL = "https://my-movie-rating.herokuapp.com/";
    private static final String requestURL = "http://10.0.2.2:3001/";
    private static final int EXISTING_FILM_LOADER = 0;
    private Uri mCurrentFilmUri;
    private EditText mTitleField;
    private EditText mCountryField;
    private EditText mDirField;
    private EditText mMainField;
    private EditText mSuppField;
    private EditText mSynopField;
    private EditText mPosterField;
    private EditText mDirUrl;
    private EditText mMainUrl;
    private EditText mSupportUrl;
    private DatePicker mDateField;
    private boolean mFilmHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mFilmHasChanged = true;
            return false;
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Intent intent = getIntent();
        Uri currentFilmUri = intent.getData();

        if (currentFilmUri == null) {
            setTitle("Add a new film");

        } else {
            setTitle("Edit film");

            //Initialize a loader to read the film data from the db
            //and display the current values in the editor
            mCurrentFilmUri = currentFilmUri;
            LoaderManager.getInstance(this).initLoader(EXISTING_FILM_LOADER, null, this);

        }

        //Find all relevent views that we will need to read user input from
        mTitleField = (EditText) findViewById(R.id.title_field);
        mCountryField = (EditText) findViewById(R.id.country_field);
        mDirField = (EditText) findViewById(R.id.director_field);
        mMainField = (EditText) findViewById(R.id.main_field);
        mSuppField = (EditText) findViewById(R.id.support_field);
        mSynopField = (EditText) findViewById(R.id.synopsis_field);
        mPosterField = (EditText) findViewById(R.id.poster_url_field);
        mDirUrl = (EditText) findViewById(R.id.dir_url_field);
        mMainUrl = (EditText) findViewById(R.id.main_url_field);
        mSupportUrl = (EditText) findViewById(R.id.support_url_field);
        mDateField = (DatePicker) findViewById(R.id.date_field);

        //setup OnTouchListeners on all the input fields, so we can determine
        //if the user has touched or modified them. This will let us know if there
        //are unsaved changes or not, if the user tries to leave the editor without saving.
        mTitleField.setOnTouchListener(mTouchListener);
        mCountryField.setOnTouchListener(mTouchListener);
        mDirField.setOnTouchListener(mTouchListener);
        mMainField.setOnTouchListener(mTouchListener);
        mSuppField.setOnTouchListener(mTouchListener);
        mSynopField.setOnTouchListener(mTouchListener);
        mPosterField.setOnTouchListener(mTouchListener);
        mDirUrl.setOnTouchListener(mTouchListener);
        mMainUrl.setOnTouchListener(mTouchListener);
        mSupportUrl.setOnTouchListener(mTouchListener);
        mDateField.setOnTouchListener(mTouchListener);

    }

    //Get user input from editor and save film in to db.
    private void saveFilm() {
        //Read from input fields
        //Use trim to eliminate leading or trailing white space
        String title = mTitleField.getText().toString().trim();
        String country = mCountryField.getText().toString().trim();
        String director = mDirField.getText().toString().trim();
        String mainAct = mMainField.getText().toString().trim();
        String supportAct = mSuppField.getText().toString().trim();
        String synopsis = mSynopField.getText().toString().trim();
        String poster = mPosterField.getText().toString().trim();
        String dirPic = mDirUrl.getText().toString().trim();
        String mainPic = mMainUrl.getText().toString().trim();
        String supportPic = mSupportUrl.getText().toString().trim();
        String year = Integer.toString(mDateField.getYear()).trim();
        String month = Integer.toString(mDateField.getMonth()).trim();
        String date = Integer.toString(mDateField.getDayOfMonth()).trim();
        String releaseDate = year + "." + month + "." + date;


        //Create ContentValues object where column name are the keys,
        //and film attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(FilmContract.FilmEntry.COLUMN_TITLE, title);
        values.put(FilmContract.FilmEntry.COLUMN_COUNTRY, country);
        values.put(FilmContract.FilmEntry.COLUMN_IMAGE_URL, poster);
        values.put(FilmContract.FilmEntry.COLUMN_YEAR, year);
        values.put(FilmContract.FilmEntry.COLUMN_SYNOPSIS, synopsis);
        values.put(FilmContract.FilmEntry.COLUMN_RELEASE, releaseDate);
        values.put(FilmContract.FilmEntry.COLUMN_DIRECTOR, director);
        values.put(FilmContract.FilmEntry.COLUMN_DIR_URL, dirPic);
        values.put(FilmContract.FilmEntry.COLUMN_MAIN, mainAct);
        values.put(FilmContract.FilmEntry.COLUMN_MAIN_URL, mainPic);
        values.put(FilmContract.FilmEntry.COLUMN_SUPPORT, supportAct);
        values.put(FilmContract.FilmEntry.COLUMN_SUPPORT_URL, supportPic);

        //Determine if this is a new or existing film
        // by checking if mCurrentFilmUri is null or not
        if (mCurrentFilmUri == null) {
            //This is a new film, so insert a new film into the provider,M
            //returning the content URI for the new film.
            System.out.print(values);
            Uri newUri = getContentResolver().insert(FilmContract.FilmEntry.CONTENT_URI, values);

            //show a toast message
            if (newUri == null) {
                Toast.makeText(this, "Adding a new film failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Film added successfully", Toast.LENGTH_SHORT).show();
            }
        } else {
            //This is an EXISTING film, so update the film with content URI:mCurrentFilmUri
            //and pass in the new ContentValues. Pass in null for the selection and selection args
            //because mCurrentFilmUri will already identify the correct row in the db that we
            // want to modify.
            int rowsAffected = getContentResolver().update(mCurrentFilmUri, values, null, null);

            //Toast message
            if (rowsAffected == 0) {
                Toast.makeText(this, "Updating film failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Updating film successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            //"Save" menu option
            case R.id.action_save:
                //saveFilm();
                if (mCurrentFilmUri == null) {
                onButtonClickHttpPost httpPost = new onButtonClickHttpPost();
                httpPost.sendPost();
                } else {
                    onButtonClickHttpPut httpPut = new onButtonClickHttpPut();
                }

                finish();//Exit activity
                return true;
            //"delete" menu option
            case R.id.action_cancel:
                showCancelConfirmationDialog();

                return true;
            //"Up" arrow button in the app bar
            case android.R.id.home:
                //If the film has not changed, continue with navigation up to parent activity to
                // MainActivity
                if (!mFilmHasChanged) {
                    NavUtils.navigateUpFromSameTask(UploadActivity.this);
                    return true;
                }
                //If there are unsaved changes, setup a dialog to warn the user.
                //Create a click listener to handle the user confirming that
                //changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                //User clicked "Discard" Button, navigate to the parent activity.
                                NavUtils.navigateUpFromSameTask(UploadActivity.this);
                            }
                        };
                //Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
        }
        return super.onOptionsItemSelected(item);

    }

    //This method is called when the back button is pressed.
    @Override
    public void onBackPressed() {
        //If the film hasn't changed, continue with handling back button press
        if (!mFilmHasChanged) {
            super.onBackPressed();
            return;
        }

        //otherwise set up a dialog to warn the user.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };
        //Show a dialog that notifies the user they have unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                FilmContract.FilmEntry._ID,
                FilmContract.FilmEntry.COLUMN_TITLE,
                FilmContract.FilmEntry.COLUMN_COUNTRY,
                FilmContract.FilmEntry.COLUMN_IMAGE_URL,
                FilmContract.FilmEntry.COLUMN_YEAR,
                FilmContract.FilmEntry.COLUMN_SYNOPSIS,
                FilmContract.FilmEntry.COLUMN_RELEASE,
                FilmContract.FilmEntry.COLUMN_DIRECTOR,
                FilmContract.FilmEntry.COLUMN_DIR_URL,
                FilmContract.FilmEntry.COLUMN_MAIN,
                FilmContract.FilmEntry.COLUMN_MAIN_URL,
                FilmContract.FilmEntry.COLUMN_SUPPORT,
                FilmContract.FilmEntry.COLUMN_SUPPORT_URL
        };
        Log.e("upload", "-----------------------------------" + mCurrentFilmUri);

        return new CursorLoader(this, mCurrentFilmUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        //Proceed with moving to the first rows of the cursor and reading data from it
        //(This should be the only row in the cursor)
        if (cursor.moveToFirst()) {

            int currentFilmId = cursor.getColumnIndex(FilmContract.FilmEntry._ID);
            int titleColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_TITLE);
            int imageUrlColumnIndex =
                    cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_IMAGE_URL);
            int mainColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_MAIN);
            int supportColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_SUPPORT);
            int countryColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_COUNTRY);
            int yearColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_YEAR);
            int synopColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_SYNOPSIS);
            int relColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_RELEASE);
            int dirColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_DIRECTOR);
            int dirUrlColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_DIR_URL);
            int mainUrlColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_MAIN_URL);
            int suppUrlColumnIndex =
                    cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_SUPPORT_URL);

            //Extract out the value from the Cursor for the given column index
            long filmId = cursor.getLong(currentFilmId);
            String filmTitle = cursor.getString(titleColumnIndex);
            String filmImageUrl = cursor.getString(imageUrlColumnIndex);
            String filmMain = cursor.getString(mainColumnIndex);
            String filmSupport = cursor.getString(supportColumnIndex);
            String filmCountry = cursor.getString(countryColumnIndex);
            String filmYear = cursor.getString(yearColumnIndex);
            String filmRel = cursor.getString(relColumnIndex);
            String filmDir = cursor.getString(dirColumnIndex);
            String filmSynopsis = cursor.getString(synopColumnIndex);
            String filmDirUrl = cursor.getString(dirUrlColumnIndex);
            String filmMainUrl = cursor.getString(mainUrlColumnIndex);
            String filmSuppUrl = cursor.getString(suppUrlColumnIndex);


            //Update the views on the screen with the values from the db
            mTitleField.setText(filmTitle);
            mCountryField.setText(filmCountry);
            mDirField.setText(filmDir);
            mMainField.setText(filmMain);
            mSuppField.setText(filmSupport);
            mSynopField.setText(filmSynopsis);
            mPosterField.setText(filmImageUrl);
            mDirUrl.setText(filmDirUrl);
            mMainUrl.setText(filmMainUrl);
            mSupportUrl.setText(filmSuppUrl);
            mDateField.updateDate(getRelYear(filmRel), getRelMonth(filmRel), getRelDate(filmRel));
        }
    }

    public int getRelYear(String dateString) {
        String[] arrSplit = dateString.split("\\.");
        for (int i = 0; i < arrSplit.length; i++) {
            System.out.println(arrSplit[i]);
        }
        return Integer.parseInt(arrSplit[0]);
    }

    public int getRelMonth(String dateString) {
        String[] arrSplit = dateString.split("\\.");
        for (int i = 0; i < arrSplit.length; i++) {
            System.out.println(arrSplit[i]);
        }
        return Integer.parseInt(arrSplit[1]);
    }

    public int getRelDate(String dateString) {
        String[] arrSplit = dateString.split("\\.");
        for (int i = 0; i < arrSplit.length; i++) {
            System.out.println(arrSplit[i]);
        }
        return Integer.parseInt(arrSplit[2]);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //if the loader is invalidated, clear out all the data from the input fields.
        mTitleField.setText("");
        mCountryField.setText("");
        mDirField.setText("");
        mMainField.setText("");
        mSuppField.setText("");
        mSynopField.setText("");
        mPosterField.setText("");
        mDirUrl.setText("");
        mMainUrl.setText("");
        mSupportUrl.setText("");
        mDateField.updateDate(2021, 1, 1);
    }


    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        //Create an AlterDialog.Builder and set the message, and click listeners
        //for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //User clicked the "Keep editing" button, so dismiss the dialog
                //and continue editing the film.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        //Create and show the AlertDialog.
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Prompt the user to confirm that they want to delete this film.
    private void showDeleteConfirmationDialog() {
        //Create an AlertDialog.builder and set the message and click listeners
        //for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_message);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                deleteFilm();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        //Create and show the AlertDialog.
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showCancelConfirmationDialog() {
        //Create an AlertDialog.builder and set the message and click listeners
        //for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cancel Editing?");
        builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                deleteFilm();
            }
        });
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        //Create and show the AlertDialog.
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //perform the deletion of the film in the db
    private void deleteFilm() {
        //Only perform the delete if this is an existing film.
        if (mCurrentFilmUri != null) {
            //call the contentResolver to delete at the given content uri.
            //pass in null for the selection and selection args because the mCurrentFilmUri
            //content URI already indentifies the film that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentFilmUri, null, null);

            //Toast message
            if (rowsDeleted == 0) {
                Toast.makeText(this, "Failed in deleting the film", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Updating cancelled", Toast.LENGTH_SHORT).show();
            }
        }
        //close the activity
        finish();
    }


    //Below code is used to connect the http server when contents.

    public class onButtonClickHttpPut {
        public void sendPut() {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(requestURL);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("PUT");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);


                        Gson gson = new Gson();
                        String json = gson.toJson(submitFilm());

                        OutputStreamWriter out = new OutputStreamWriter(
                                conn.getOutputStream());
                        out.write("Resource content");
                        out.close();
                        conn.getInputStream();

                        Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                        Log.i("MSG", conn.getResponseMessage());

                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Log.v(UploadActivity.class.getSimpleName(), "Put completed");
            thread.start();
        }
    }

    public class onButtonClickHttpPost {
        public void sendPost() {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(requestURL);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);

                        Gson gson = new Gson();
                        String json = gson.toJson(submitFilm());

                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                        os.writeBytes(json);


                        os.flush();
                        os.close();

                        Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                        Log.i("MSG", conn.getResponseMessage());

                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Log.v(UploadActivity.class.getSimpleName(), "Post completed");
            thread.start();
        }
    }



    private Film submitFilm() {
        int id = 0;
        String title = mTitleField.getText().toString().trim();
        String poster = mPosterField.getText().toString().trim();
        String country = mCountryField.getText().toString().trim();
        String director = mDirField.getText().toString().trim();
        String mainAct = mMainField.getText().toString().trim();
        String supportAct = mSuppField.getText().toString().trim();
        String synopsis = mSynopField.getText().toString().trim();
        String imdb = "";

        String dirPic = mDirUrl.getText().toString().trim();
        String mainPic = mMainUrl.getText().toString().trim();
        String supportPic = mSupportUrl.getText().toString().trim();
        String year = Integer.toString(mDateField.getYear()).trim();
        String month = Integer.toString(mDateField.getMonth()).trim();
        String date = Integer.toString(mDateField.getDayOfMonth()).trim();
        String releaseDate = year + "." + month + "." + date;

        Film newFilm = new Film(id, imdb, title, poster, country, year, synopsis, releaseDate,
                director, dirPic, mainAct, mainPic, supportAct, supportPic);
        return newFilm;
    }


    }
