package ufm.universalfinancemanager;

/**
 * Created by smh7 on 12/11/17.
 */

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();
}
