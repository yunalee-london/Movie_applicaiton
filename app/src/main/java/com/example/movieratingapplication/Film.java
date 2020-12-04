package com.example.movieratingapplication;

import android.text.format.DateFormat;

public class Film {
    private final String title;
    private final String image;
    private final String country;
    private final long year;
    private final String synopsis;
    private final String release;
    private final String dir;
    private final String main;
    private final String support;


    public Film(String filmTitle, String imageUrl, String relCountry, long relYear, String story, String relDate, String director, String mainAct, String supportAct){
        title = filmTitle;
        image = imageUrl;
        country = relCountry;
        year = relYear;
        synopsis = story;
        release = relDate;
        dir = director;
        main = mainAct;
        support = supportAct;
     }
    public String getTitle() {return title;}
    public String getImage() {return image;}
    public String getCountry() {return country;}
    public long getYear() {return year;}
    public String getSynopsis() {return synopsis;}
    public String getRelease() {return release;}
    public String getDir() {return dir;}
    public String getMain() {return main;}
    public String getSupport() {return support;}


}
