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
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.db.source.local.converter.FlowConverter;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.RowType;

public class Category implements Parcelable, Serializable{

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

    /*@Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if(convertView == null)
            view = inflater.inflate(R.layout.earnings_list_item, null);
        else
            view = convertView;

        //Instantiate all the textviews from the layout
        TextView categoryName = view.findViewById(R.id.category_name);
        TextView thisMonthBalance = view.findViewById(R.id.thisMonth_balance);
        TextView lastMonthBalance = view.findViewById(R.id.thisMonth_balance);

        //Set the text of each textview based on its corresponding transaction attribute
        categoryName.setText(this.name);
        thisMonthBalance.setText("Placeholder");
        lastMonthBalance.setText("Placeholder");

        return view;
    }*/

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
