
package com.example.nytimes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopStories implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("num_results")
    @Expose
    private Long numResults;
    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList<Result>();

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The copyright
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * 
     * @param copyright
     *     The copyright
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * 
     * @return
     *     The section
     */
    public String getSection() {
        return section;
    }

    /**
     * 
     * @param section
     *     The section
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * 
     * @return
     *     The lastUpdated
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * 
     * @param lastUpdated
     *     The last_updated
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * 
     * @return
     *     The numResults
     */
    public Long getNumResults() {
        return numResults;
    }

    /**
     * 
     * @param numResults
     *     The num_results
     */
    public void setNumResults(Long numResults) {
        this.numResults = numResults;
    }

    /**
     * 
     * @return
     *     The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * 
     * @param results
     *     The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.copyright);
        dest.writeString(this.section);
        dest.writeString(this.lastUpdated);
        dest.writeValue(this.numResults);
        dest.writeTypedList(this.results);
    }

    public TopStories() {
    }

    protected TopStories(Parcel in) {
        this.status = in.readString();
        this.copyright = in.readString();
        this.section = in.readString();
        this.lastUpdated = in.readString();
        this.numResults = (Long) in.readValue(Long.class.getClassLoader());
        this.results = in.createTypedArrayList(Result.CREATOR);
    }

    public static final Parcelable.Creator<TopStories> CREATOR = new Parcelable.Creator<TopStories>() {
        @Override
        public TopStories createFromParcel(Parcel source) {
            return new TopStories(source);
        }

        @Override
        public TopStories[] newArray(int size) {
            return new TopStories[size];
        }
    };
}
