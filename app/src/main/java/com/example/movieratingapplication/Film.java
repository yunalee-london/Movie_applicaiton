package com.example.movieratingapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {
    private final long id;
    private final String imdb;
    private final String title;
    private final String image;
    private final String country;
    private final String year;
    private final String synopsis;
    private final String release;
    private final String director;
    private final String dirImage;
    private final String main;
    private final String mainImage;
    private final String support;
    private final String supportImage;
    private final String videoId;
    private final String videoUrl;


    public Film(long id, String imdb, String filmTitle, String imageUrl, String relCountry, String relYear,
                String story, String relDate, String director, String dirPhoto, String mainAct,
                String mainPhoto, String supportAct, String supportPhoto, String videoId,
                String videoUrl) {
        this.id = id;
        this.imdb = imdb;
        title = filmTitle;
        image = imageUrl;
        country = relCountry;
        year = relYear;
        synopsis = story;
        release = relDate;
        this.director = director;
        dirImage = dirPhoto;
        main = mainAct;
        mainImage = mainPhoto;
        support = supportAct;
        supportImage = supportPhoto;
        this.videoId = videoId;
        this.videoUrl = videoUrl;
    }

    protected Film(Parcel in) {
        id = in.readLong();
        imdb = in.readString();
        title = in.readString();
        image = in.readString();
        country = in.readString();
        year = in.readString();
        synopsis = in.readString();
        release = in.readString();
        director = in.readString();
        dirImage = in.readString();
        main = in.readString();
        mainImage = in.readString();
        support = in.readString();
        supportImage = in.readString();
        videoId = in.readString();
        videoUrl = in.readString();

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

    public String getImdb() {return imdb;}

    public String getTitle() {return title;}

    public String getImage() {return image;}

    public String getCountry() {return country;}

    public String getYear() {return year;}

    public String getSynopsis() {return synopsis;}

    public String getRelease() {return release;}

    public String getDirector() {return director;}

    public String getDirImage() {return dirImage;}

    public String getMain() {return main;}

    public String getMainImage() {return mainImage;}

    public String getSupport() {return support;}

    public String getSupportImage() {return supportImage;}

    public String getVideoId() {return videoId;}

    public String getVideoUrl() {return videoUrl;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(imdb);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(country);
        dest.writeString(year);
        dest.writeString(synopsis);
        dest.writeString(release);
        dest.writeString(director);
        dest.writeString(dirImage);
        dest.writeString(main);
        dest.writeString(mainImage);
        dest.writeString(support);
        dest.writeString(supportImage);
        dest.writeString(videoId);
        dest.writeString(videoUrl);
    }
}
