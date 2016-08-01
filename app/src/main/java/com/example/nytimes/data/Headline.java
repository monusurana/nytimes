
package com.example.nytimes.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Headline implements Parcelable {

    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("kicker")
    @Expose
    private String kicker;

    /**
     * 
     * @return
     *     The main
     */
    public String getMain() {
        return main;
    }

    /**
     * 
     * @param main
     *     The main
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     * 
     * @return
     *     The kicker
     */
    public String getKicker() {
        return kicker;
    }

    /**
     * 
     * @param kicker
     *     The kicker
     */
    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.main);
        dest.writeString(this.kicker);
    }

    public Headline() {
    }

    protected Headline(Parcel in) {
        this.main = in.readString();
        this.kicker = in.readString();
    }

    public static final Parcelable.Creator<Headline> CREATOR = new Parcelable.Creator<Headline>() {
        @Override
        public Headline createFromParcel(Parcel source) {
            return new Headline(source);
        }

        @Override
        public Headline[] newArray(int size) {
            return new Headline[size];
        }
    };
}
