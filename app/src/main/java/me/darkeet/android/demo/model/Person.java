package me.darkeet.android.demo.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Name: Person
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/29 14:21
 * Desc: Parcelable注解框架，文档：https://github.com/sockeqwe/ParcelablePlease
 *       http://hannesdorfmann.com/android/AnnotatedAdapter/
 *       限制：不能为私有字段和私有类，具有不可见特性；
 */
@ParcelablePlease
public class Person implements Parcelable {

    String name;
    int age;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PersonParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        public Person createFromParcel(Parcel source) {
            Person target = new Person();
            PersonParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
