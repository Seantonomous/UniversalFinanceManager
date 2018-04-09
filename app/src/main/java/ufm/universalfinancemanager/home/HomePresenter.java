package ufm.universalfinancemanager.home;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import ufm.universalfinancemanager.support.atomic.User;

public class HomePresenter implements HomeContract.Presenter {

    private final User mUser;

    @Nullable
    private HomeContract.View mHomeView = null;

    @Inject
    public HomePresenter(User user) {
        mUser = user;
    }


    @Override
    public void takeView(HomeContract.View v) {
        if(v == null)
            return;

        mHomeView = v;
    }

    @Override
    public void dropView() {
        mHomeView = null;

    }
}
