/*
package com.example.movieratingapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.movieratingapplication.data.FilmContract;

public class UploadActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String requestURL = "https://my-movie-rating.herokuapp.com/";
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
        mTitleField.setOnTouchListener(mTouchListener);;
        mCountryField.setOnTouchListener(mTouchListener);;
        mDirField.setOnTouchListener(mTouchListener);;
        mMainField.setOnTouchListener(mTouchListener);;
        mSuppField.setOnTouchListener(mTouchListener);;
        mSynopField.setOnTouchListener(mTouchListener);;
        mPosterField.setOnTouchListener(mTouchListener);;
        mDirUrl.setOnTouchListener(mTouchListener);;
        mMainUrl.setOnTouchListener(mTouchListener);;
        mSupportUrl.setOnTouchListener(mTouchListener);;
        mDateField.setOnTouchListener(mTouchListener);;
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


        //Check if this is for a new film
        //and check if all fields are blank
        if(mCurrentFilmUri == null &&
                TextUtils.isEmpty(title)&&
                TextUtils.isEmpty(country)&&
                TextUtils.isEmpty(director)&&
                TextUtils.isEmpty(mainAct)&&
                TextUtils.isEmpty(supportAct)&&
                TextUtils.isEmpty(synopsis)&&
                TextUtils.isEmpty(poster)&&
                TextUtils.isEmpty(dirPic)&&
                TextUtils.isEmpty(mainPic)&&
                TextUtils.isEmpty(supportPic)&&
                TextUtils.isEmpty(releaseDate)) {
            //Since no fields were modified, we can return early without creating a new film.
            //No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        //Create ContentValues object where column name are the keys,
        //and film attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(FilmContract.FilmEntry.COLUMN_TITLE, title);
        values.put(FilmContract.FilmEntry.COLUMN_COUNTRY,country);
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
        if(mCurrentFilmUri == null) {
            //This is a new film, so insert a new film into the provider,
            //returning the content URI for the new film.
            Uri newUri = getContentResolver().insert(FilmContract.FilmEntry.CONTENT_URI, values);

            //show a toast message
            if(newUri == null) {
                Toast.makeText(this, "Adding a new film failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Film added successfully", Toast.LENGTH_SHORT).show();
            }
        } else {
            //This is an EXISTING film, so update the film with content URI:mCurrentFilmUri
            //and pass in the new ContentValues. Pass in null for the selection and selection args
            //because mCurrentFilmUri will already identify the correct row in the db that we want to modify.
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
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            //"Save" menu option
            case R.id.action_save:
                saveFilm();
                finish();//Exit activity
                return true;
            //"delete" menu option
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            //"Up" arrow button in the app bar
            case android.R.id.home:
                //If the film has not changed, continue with navigation up to parent activity to MainActivity
                if(!mFilmHasChanged) {
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

        return new CursorLoader(this, mCurrentFilmUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished (Loader<Cursor> loader, Cursor cursor) {
        //Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() <1 ) {
            return;
        }

        //Proceed with moving to the first rows of the cursor and reading data from it
        //(This should be the only row in the cursor)
        if (cursor.moveToFirst()) {

            int currentFilmId = cursor.getColumnIndex(FilmContract.FilmEntry._ID);
            int titleColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_TITLE);
            int imageUrlColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_IMAGE_URL);
            int mainColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_MAIN);
            int supportColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_SUPPORT);
            int countryColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_COUNTRY);
            int yearColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_YEAR);
            int synopColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_SYNOPSIS);
            int relColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_RELEASE);
            int dirColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_DIRECTOR);
            int dirUrlColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_DIR_URL);
            int mainUrlColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_MAIN_URL);
            int suppUrlColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_SUPPORT_URL);

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
            //mDateField
        }
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
        //mDateField.init
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
                Toast.makeText(this, "Film deleted successfully", Toast.LENGTH_SHORT).show();
            }
        }
        //close the activity
        finish();
    }
}

 */
/*private void insertFilm() {
        FilmDbHelper filmDbHelper = new FilmDbHelper(this);
        //get the database in write mode
        SQLiteDatabase db = filmDbHelper.getWritableDatabase();

        //Read from input fields
        //use trim to eliminate leading or trailing white space
        EditText titleField = (EditText) findViewById(R.id.title_field);
        String title = titleField.getText().toString().trim();

        EditText countryField = (EditText) findViewById(R.id.country_field);
        String country = countryField.getText().toString().trim();

        EditText dirField = (EditText) findViewById(R.id.director_field);
        String director = dirField.getText().toString().trim();

        EditText mainField = (EditText) findViewById(R.id.main_field);
        String mainAct = mainField.getText().toString().trim();

        EditText suppField = (EditText) findViewById(R.id.support_field);
        String supportAct = suppField.getText().toString().trim();

        EditText synopField = (EditText) findViewById(R.id.synopsis_field);
        String synopsis = synopField.getText().toString().trim();

        EditText posterField = (EditText) findViewById(R.id.poster_url_field);
        String poster = posterField.getText().toString().trim();

        EditText dirUrl = (EditText) findViewById(R.id.dir_url_field);
        String dirPic = dirUrl.getText().toString().trim();

        EditText mainUrl = (EditText) findViewById(R.id.main_url_field);
        String mainPic = mainUrl.getText().toString().trim();

        EditText supportUrl = (EditText) findViewById(R.id.support_url_field);
        String supportPic = supportUrl.getText().toString().trim();

        DatePicker dateField = (DatePicker) findViewById(R.id.date_field);
        String year = Integer.toString(dateField.getYear()).trim();
        String month = Integer.toString(dateField.getMonth()).trim();
        String date = Integer.toString(dateField.getDayOfMonth()).trim();
        String releaseDate = year + "." + month + "." + date;



        //create ContentValues object
        ContentValues values = new ContentValues();
        values.put(FilmContract.FilmEntry.COLUMN_TITLE, title);
        values.put(FilmContract.FilmEntry.COLUMN_COUNTRY,country);
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

        Uri newUri = getContentResolver().insert(FilmContract.FilmEntry.CONTENT_URI, values);

        //create Toast message
        if (newUri == null) {
            Toast.makeText(this, getString(R.string.editor_insert_film_successful), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.editor_insert_film_successful), Toast.LENGTH_SHORT).show();
        }

    }*//*



//Below code is used to connect the http server when contents.
       */
/* Button add_button = (Button)findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClickHttpPost httpPost= new onButtonClickHttpPost();
                httpPost.sendPost();

                Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                startActivity(intent);
        }
    });

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
                        Log.i("MSG" , conn.getResponseMessage());

                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }

        private Film submitFilm() {
            EditText titleField = (EditText) findViewById(R.id.title_field);
            String title = titleField.getText().toString();
            Log.v("SearchActivity", "Title: " + title);

            EditText countryField = (EditText) findViewById(R.id.country_field);
            String country = countryField.getText().toString();

            EditText dirField = (EditText) findViewById(R.id.director_field);
            String director = dirField.getText().toString();

            EditText mainField = (EditText) findViewById(R.id.main_field);
            String mainAct = mainField.getText().toString();

            EditText suppField = (EditText) findViewById(R.id.support_field);
            String supportAct = suppField.getText().toString();

            EditText synopField = (EditText) findViewById(R.id.synopsis_field);
            String synopsis = synopField.getText().toString();

            EditText posterField = (EditText) findViewById(R.id.poster_url_field);
            String poster = posterField.getText().toString();

            EditText dirUrl = (EditText) findViewById(R.id.dir_url_field);
            String dirPic = dirUrl.getText().toString();

            EditText mainUrl = (EditText) findViewById(R.id.main_url_field);
            String mainPic = mainUrl.getText().toString();

            EditText supportUrl = (EditText) findViewById(R.id.support_url_field);
            String supportPic = supportUrl.getText().toString();

            DatePicker dateField = (DatePicker) findViewById(R.id.date_field);
            String year = Integer.toString(dateField.getYear());
            String month = Integer.toString(dateField.getMonth());
            String date = Integer.toString(dateField.getDayOfMonth());
            String releaseDate = year + "." + month + "." + date;

            Film newFilm = new Film(title, poster, country, year, synopsis, releaseDate, director, dirPic, mainAct, mainPic, supportAct, supportPic);
            return newFilm;
        }
    }
}*/
