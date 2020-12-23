package com.example.movieratingapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {
    private final long id;
    private final String title;
    private final String image;
    private final String country;
    private final String year;
    private final String synopsis;
    private final String release;
    private final String dir;
    private final String dirImage;
    private final String main;
    private final String mainImage;
    private final String support;
    private final String supportImage;


    public Film(long id, String filmTitle, String imageUrl, String relCountry, String relYear, String story, String relDate, String director, String dirPhoto, String mainAct, String mainPhoto, String supportAct, String supportPhoto){
        this.id = id;
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

    protected Film(Parcel in) {
        id = in.readLong();
        title = in.readString();
        image = in.readString();
        country = in.readString();
        year = in.readString();
        synopsis = in.readString();
        release = in.readString();
        dir = in.readString();
        dirImage = in.readString();
        main = in.readString();
        mainImage = in.readString();
        support = in.readString();
        supportImage = in.readString();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
    public long getId() {return id;}
    public String getTitle() {return title;}
    public String getImage() {return image;}
    public String getCountry() {return country;}
    public String getYear() {return year;}
    public String getSynopsis() {return synopsis;}
    public String getRelease() {return release;}
    public String getDir() {return dir;}
    public String getDirImage() {return dirImage;}
    public String getMain() {return main;}
    public String getMainImage() {return mainImage;}
    public String getSupport() {return support;}
    public String getSupportImage() {return supportImage;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(country);
        dest.writeString(year);
        dest.writeString(synopsis);
        dest.writeString(release);
        dest.writeString(dir);
        dest.writeString(dirImage);
        dest.writeString(main);
        dest.writeString(mainImage);
        dest.writeString(support);
        dest.writeString(supportImage);
    }
}
