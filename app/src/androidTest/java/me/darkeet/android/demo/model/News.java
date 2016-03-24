package me.darkeet.android.demo.model;

import android.os.Parcel;

import me.darkeet.android.cache.CachedModel;

/**
 * Name: News
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2016/3/24 17:37
 * Desc:
 */
public class News extends CachedModel {

    private int id;
    private String title;

    public News() {
    }

    public News(int id, String title) {
        this.id = id;
        this.title = title;
    }

    protected News(Parcel source) {
        super(source);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.title = source.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
