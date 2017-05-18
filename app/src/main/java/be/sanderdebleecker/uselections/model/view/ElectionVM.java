package be.sanderdebleecker.uselections.model.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for returned data from Google Civic Api
 * Created by Sander De Bleecker on 08/05/2017.
 */
public class ElectionVM implements Parcelable {
    private long id;
    private String name;
    private String electionDay;
    private String ocdDivisionId;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getElectionDay() {
        return electionDay;
    }
    public void setElectionDay(String electionDay) {
        this.electionDay = electionDay;
    }
    public String getOcdDivisionId() {
        return ocdDivisionId;
    }
    public void setOcdDivisionId(String ocdDivisionId) {
        this.ocdDivisionId = ocdDivisionId;
    }

    //Parcelable implementation
    @Override
    public int describeContents () {
        return 0;
    }
    @Override
    public void writeToParcel (Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(electionDay);
        dest.writeString(ocdDivisionId);
    }
    //
    public static transient final Parcelable.Creator<ElectionVM> CREATOR = new Parcelable.Creator<ElectionVM>() {
        @Override
        public ElectionVM createFromParcel (Parcel source) {
            ElectionVM reconstruction = new ElectionVM();
            reconstruction.setId(source.readLong());
            reconstruction.setName(source.readString());
            reconstruction.setElectionDay(source.readString());
            reconstruction.setOcdDivisionId(source.readString());
            return reconstruction;
        }
        @Override
        public ElectionVM[] newArray (int size) {
            return new ElectionVM[size];
        }
    };
}
