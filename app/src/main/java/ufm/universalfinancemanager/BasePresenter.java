package ufm.universalfinancemanager;

/**
 * Created by smh7 on 12/11/17.
 * The interface used by all presenters, at the very least should have the
 * following two methods defined to take and drop its corresponding view
 */

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();
}
