package com.example.movieratingapplication;

public class Film {
    private final String title;
    private final String image;
    private final String country;
    private final int year;
    private final String synopsis;
    private final String release;
    private final String dir;
    private final String dirImage;
    private final String main;
    private final String mainImage;
    private final String support;
    private final String supportImage;


    public Film(String filmTitle, String imageUrl, String relCountry, int relYear, String story, String relDate, String director, String dirPhoto, String mainAct, String mainPhoto, String supportAct, String supportPhoto){
        title = filmTitle;
        image = imageUrl;
        country = relCountry;
        year = relYear;
        synopsis = story;
        release = relDate;
        dir = director;
        dirImage = dirPhoto;
        main = mainAct;
        mainImage = mainPhoto;
        support = supportAct;
        supportImage = supportPhoto;
     }
    public String getTitle() {return title;}
    public String getImage() {return image;}
    public String getCountry() {return country;}
    public int getYear() {return year;}
    public String getSynopsis() {return synopsis;}
    public String getRelease() {return release;}
    public String getDir() {return dir;}
    public String getDirImage() {return dirImage;}
    public String getMain() {return main;}
    public String getMainImage() {return mainImage;}
    public String getSupport() {return support;}
    public String getSupportImage() {return supportImage;}


}
