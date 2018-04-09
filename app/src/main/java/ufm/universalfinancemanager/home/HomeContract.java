package ufm.universalfinancemanager.home;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;

public interface HomeContract {

    interface View extends BaseView<Presenter> {
        // boolean isActive();
    }

    interface Presenter extends BasePresenter<View> {

        void takeView(View v);

        void dropView();
    }
}
