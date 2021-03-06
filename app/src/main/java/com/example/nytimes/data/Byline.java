
package com.example.nytimes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Byline implements Parcelable {

    @SerializedName("person")
    @Expose
    private List<Person> person = new ArrayList<Person>();
    @SerializedName("original")
    @Expose
    private String original;

    /**
     * 
     * @return
     *     The person
     */
    public List<Person> getPerson() {
        return person;
    }

    /**
     * 
     * @param person
     *     The person
     */
    public void setPerson(List<Person> person) {
        this.person = person;
    }

    /**
     * 
     * @return
     *     The original
     */
    public String getOriginal() {
        return original;
    }

    /**
     * 
     * @param original
     *     The original
     */
    public void setOriginal(String original) {
        this.original = original;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.person);
        dest.writeString(this.original);
    }

    public Byline() {
    }

    protected Byline(Parcel in) {
        this.person = new ArrayList<Person>();
        in.readList(this.person, Person.class.getClassLoader());
        this.original = in.readString();
    }

    public static final Parcelable.Creator<Byline> CREATOR = new Parcelable.Creator<Byline>() {
        @Override
        public Byline createFromParcel(Parcel source) {
            return new Byline(source);
        }

        @Override
        public Byline[] newArray(int size) {
            return new Byline[size];
        }
    };
}
