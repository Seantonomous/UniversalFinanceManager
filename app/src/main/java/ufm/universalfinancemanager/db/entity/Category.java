/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/14/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

import ufm.universalfinancemanager.db.source.local.converter.FlowConverter;
import ufm.universalfinancemanager.support.Flow;

@Entity
public class Category {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "category_name")
    private String name;

    @TypeConverters(FlowConverter.class)
    @ColumnInfo(name = "category_flow")
    private Flow flow;

    public Category(String name, Flow flow) {
        this.name = name;
        this.flow = flow;
    }

    public String getName() {return this.name;}
    public Flow getFlow() {return this.flow;}
    public void setName(String name) {this.name = name;}
    public void setFlow(Flow flow) {this.flow = flow;}

    @Override
    public String toString() {
        return this.name;
    }
}
