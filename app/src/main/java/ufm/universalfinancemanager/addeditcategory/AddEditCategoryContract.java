package ufm.universalfinancemanager.addeditcategory;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;
import ufm.universalfinancemanager.support.Flow;

/**
 * Created by smh7 on 2/7/18.
 */

public class AddEditCategoryContract {
        interface View extends BaseView<ufm.universalfinancemanager.addeditcategory.AddEditCategoryPresenter> {

            void showLastActivity(boolean success);

            void showMessage(String message);
        }

        interface Presenter extends BasePresenter<View> {
            void saveCategory(String name, Flow flow);

            void takeView(View v);

            void dropView();
        }
}
