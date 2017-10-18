/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/17/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Transaction_Activity extends Fragment {

    public Transaction_Activity() {
        //Required to be empty since extends Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.transaction_list_layout, container);
        //ListView transaction_list = rootView.findViewById(R.id.transaction_list);
        return rootView;
    }
}
