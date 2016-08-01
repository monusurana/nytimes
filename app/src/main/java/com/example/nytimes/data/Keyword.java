
package com.example.nytimes.data;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Keyword implements Parcelable {

    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("is_major")
    @Expose
    private String isMajor;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private String value;

    /**
     * 
     * @return
     *     The rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * 
     * @param rank
     *     The rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * 
     * @return
     *     The isMajor
     */
    public String getIsMajor() {
        return isMajor;
    }

    /**
     * 
     * @param isMajor
     *     The is_major
     */
    public void setIsMajor(String isMajor) {
        this.isMajor = isMajor;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The value
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(this.rank);
        dest.writeString(this.isMajor);
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    public Keyword() {
    }

    protected Keyword(android.os.Parcel in) {
        this.rank = in.readString();
        this.isMajor = in.readString();
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<Keyword> CREATOR = new Parcelable.Creator<Keyword>() {
        @Override
        public Keyword createFromParcel(android.os.Parcel source) {
            return new Keyword(source);
        }

        @Override
        public Keyword[] newArray(int size) {
            return new Keyword[size];
        }
    };
}
