package ufm.universalfinancemanager.networth;

import javax.inject.Inject;

import ufm.universalfinancemanager.support.atomic.User;

/**
 * Created by smh7 on 2/28/18.
 */

public class NetworthPresenter implements NetworthContract.Presenter {
    private NetworthContract.View mView;
    private User mUser;

    private boolean DEBUG = true;

    @Inject
    NetworthPresenter(User user) {
        mUser = user;
    }

    private void getNetworth() {
        mView.showNetworth(mUser.getAccounts());
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
