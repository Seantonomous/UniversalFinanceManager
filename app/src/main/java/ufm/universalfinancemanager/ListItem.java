package ufm.universalfinancemanager;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by smh7 on 10/30/17.
 */

public interface ListItem {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}
