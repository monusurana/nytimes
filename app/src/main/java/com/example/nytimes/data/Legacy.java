
package com.example.nytimes.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Legacy implements Parcelable {

    @SerializedName("thumbnailheight")
    @Expose
    private String thumbnailheight;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("thumbnailwidth")
    @Expose
    private String thumbnailwidth;

    /**
     * 
     * @return
     *     The thumbnailheight
     */
    public String getThumbnailheight() {
        return thumbnailheight;
    }

    /**
     * 
     * @param thumbnailheight
     *     The thumbnailheight
     */
    public void setThumbnailheight(String thumbnailheight) {
        this.thumbnailheight = thumbnailheight;
    }

    /**
     * 
     * @return
     *     The thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * 
     * @param thumbnail
     *     The thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * 
     * @return
     *     The thumbnailwidth
     */
    public String getThumbnailwidth() {
        return thumbnailwidth;
    }

    /**
     * 
     * @param thumbnailwidth
     *     The thumbnailwidth
     */
    public void setThumbnailwidth(String thumbnailwidth) {
        this.thumbnailwidth = thumbnailwidth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumbnailheight);
        dest.writeString(this.thumbnail);
        dest.writeString(this.thumbnailwidth);
    }

    public Legacy() {
    }

    protected Legacy(Parcel in) {
        this.thumbnailheight = in.readString();
        this.thumbnail = in.readString();
        this.thumbnailwidth = in.readString();
    }

    public static final Parcelable.Creator<Legacy> CREATOR = new Parcelable.Creator<Legacy>() {
        @Override
        public Legacy createFromParcel(Parcel source) {
            return new Legacy(source);
        }

        @Override
        public Legacy[] newArray(int size) {
            return new Legacy[size];
        }
    };
}
