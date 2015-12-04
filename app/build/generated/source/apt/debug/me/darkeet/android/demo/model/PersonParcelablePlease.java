package me.darkeet.android.demo.model;

import android.os.Parcel;

/**
 * Generated class by @ParcelablePlease . Do not modify this code!
 */
public class PersonParcelablePlease {

  public static void writeToParcel(Person source, Parcel parcel, int flags) {

    parcel.writeString(source.name);

    parcel.writeInt(source.age);
  }

  public static void readFromParcel(Person target, Parcel parcel) {

    target.name = parcel.readString();

    target.age = parcel.readInt();
  }
}
