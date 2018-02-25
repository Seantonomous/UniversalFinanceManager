/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/28/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/

package ufm.universalfinancemanager.support;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by smh7 on 10/30/17.
 */

public interface ListItem {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}
