
package com.example.nytimes.data;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Multimedium implements Parcelable {

    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("url")
    @Expose
    private String url;
//    @SerializedName("height")
//    @Expose
//    private Integer height;
//    @SerializedName("subtype")
//    @Expose
//    private String subtype;
//    @SerializedName("legacy")
//    @Expose
//    private Legacy legacy;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * 
     * @return
     *     The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The height
//     */
//    public Integer getHeight() {
//        return height;
//    }
//
//    /**
//     *
//     * @param height
//     *     The height
//     */
//    public void setHeight(Integer height) {
//        this.height = height;
//    }

    /**
     * 
     * @return
     *     The subtype
//     */
//    public String getSubtype() {
//        return subtype;
//    }
//
//    /**
//     *
//     * @param subtype
//     *     The subtype
//     */
//    public void setSubtype(String subtype) {
//        this.subtype = subtype;
//    }

    /**
     * 
     * @return
     *     The legacy
     */
//    public Legacy getLegacy() {
//        return legacy;
//    }
//
//    /**
//     *
//     * @param legacy
//     *     The legacy
//     */
//    public void setLegacy(Legacy legacy) {
//        this.legacy = legacy;
//    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(this.width);
        dest.writeString(this.url);
        dest.writeString(this.type);
    }

    public Multimedium() {
    }

    protected Multimedium(android.os.Parcel in) {
        this.width = (Integer) in.readValue(Integer.class.getClassLoader());
        this.url = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Multimedium> CREATOR = new Parcelable.Creator<Multimedium>() {
        @Override
        public Multimedium createFromParcel(android.os.Parcel source) {
            return new Multimedium(source);
        }

        @Override
        public Multimedium[] newArray(int size) {
            return new Multimedium[size];
        }
    };
}
