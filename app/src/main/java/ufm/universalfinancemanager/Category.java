/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/14/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable{
    private String name;

    public Category(String name) {
        this.name = name;
    }
    public Category(Parcel in) {
        this.name = in.readString();
    }

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

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
