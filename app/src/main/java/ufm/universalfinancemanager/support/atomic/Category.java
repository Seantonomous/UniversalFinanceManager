/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/14/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager.support.atomic;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import ufm.universalfinancemanager.db.source.local.converter.FlowConverter;
import ufm.universalfinancemanager.support.Flow;

public class Category implements Parcelable, Serializable {

    @ColumnInfo(name = "category_name")
    private String name;

    @TypeConverters(FlowConverter.class)
    @ColumnInfo(name = "category_flow")
    private Flow flow;

    public Category(String name, Flow flow) {
        this.name = name;
        this.flow = flow;
    }
    public Category(Parcel in) {

        this.name = in.readString();
        //flow = Flow.valueOf(in.readString());
    }

    public String getName() {return this.name;}
    public Flow getFlow() {return this.flow;}
    public void setName(String name) {this.name = name;}
    public void setFlow(Flow flow) {this.flow = flow;}

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    //Needed for parcelable types
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel p) {
            return new Category(p);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
