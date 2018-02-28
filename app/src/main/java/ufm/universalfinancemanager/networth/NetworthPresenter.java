package ufm.universalfinancemanager.networth;

import javax.inject.Inject;

import ufm.universalfinancemanager.support.atomic.User;

/**
 * Created by smh7 on 2/28/18.
 */

public class NetworthPresenter implements NetworthContract.Presenter {
    NetworthContract.View mView;
    User mUser;

    @Inject
    NetworthPresenter(User user) {
        mUser = user;
    }

    private void getNetworth() {

    }

    @Override
    public void takeView(NetworthContract.View view) {
        mView = view;
        getNetworth();
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
