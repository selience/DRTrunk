package me.darkeet.android.demo.model;

import android.os.Parcel;

import java.util.List;

import me.darkeet.android.cache.CachedModel;

/**
 * Name: TestObject
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2016/3/24 14:18
 * Desc:
 */
public class TestObject extends CachedModel {

    private String testString;
    private List<News> dataList;

    public TestObject() {
    }

    protected TestObject(Parcel in) {
        super(in);
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public void setDataList(List<News> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(testString);
        dest.writeList(dataList);
    }

    @Override
    public void readFromParcel(Parcel source) {
        testString = source.readString();
        dataList = source.createTypedArrayList(News.CREATOR);
    }

    public static final Creator<TestObject> CREATOR = new Creator<TestObject>() {
        @Override
        public TestObject createFromParcel(Parcel in) {
            return new TestObject(in);
        }

        @Override
        public TestObject[] newArray(int size) {
            return new TestObject[size];
        }
    };
}
