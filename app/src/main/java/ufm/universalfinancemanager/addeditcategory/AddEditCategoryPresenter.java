package ufm.universalfinancemanager.addeditcategory;

import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import ufm.universalfinancemanager.db.UserDataSource;
import ufm.universalfinancemanager.db.UserRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.db.entity.Category;
import ufm.universalfinancemanager.support.atomic.User;
import ufm.universalfinancemanager.util.EspressoIdlingResource;

/**
 * Created by smh7 on 2/7/18.
 */

public class AddEditCategoryPresenter implements AddEditCategoryContract.Presenter {

    private User mUser;
    private UserRepository mUserRepository;
    private boolean deletable;

    @Nullable
    private AddEditCategoryContract.View mAddEditCategoryview = null;

    @Nullable
    private String mCategoryName;

    @Inject
    AddEditCategoryPresenter(User user, UserRepository userRepository, @Nullable String categoryName) {
        mUser = user;
        mUserRepository = userRepository;
        mCategoryName = categoryName;
        deletable = true;
    }

    @Override
    public void saveCategory(String name, Flow flow) {
        if (isEditing()){
            if(name.length() > 25) {
                if(mAddEditCategoryview != null)
                    mAddEditCategoryview.showMessage("Category name too long!");
            }
            else {

                mUser.editCategoryName(mCategoryName, name);
                mUserRepository.updateTransactionCategories(mCategoryName, name);

                if (mAddEditCategoryview != null)
                    mAddEditCategoryview.showLastActivity(true);

            }
        }
        else {
            if(name.length() > 25) {
                if(mAddEditCategoryview != null)
                    mAddEditCategoryview.showMessage("Category name too long!");
            }else if(mUser.hasCategory(name)) {
                if (mAddEditCategoryview != null)
                    mAddEditCategoryview.showMessage("A category by that name already exists.");
            }else {
                mUser.addCategory(new Category(name, flow));
                mAddEditCategoryview.showLastActivity(true);
            }
        }


    }

    @Override
    public void deleteCategory() {

        Category c = mUser.getCategory(mCategoryName);

        checkDeletableCategory();

        if(deletable) {
            mUser.deleteCategory(c);
            if(mAddEditCategoryview != null) {
                mAddEditCategoryview.showMessage("Category deleted successfully!");
                mAddEditCategoryview.showLastActivity(true);
            }
        } else
            if(mAddEditCategoryview != null)
                mAddEditCategoryview.showMessage("Can't delete this category. Recategorize or" +
                        " delete any transactions first.");
    }

    public boolean checkDeletableCategory(){

        EspressoIdlingResource.increment();

        mUserRepository.getTransactions(new UserDataSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                checkTransactionCategoryName(transactions);
            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        });

        return deletable;
    }

    private void checkTransactionCategoryName(List<Transaction> transactions){

        for(Transaction t : transactions){
            if(t.getCategory().equals(mCategoryName)){
                deletable = false;
                return;
            }
        }

        deletable = true;
    }



    private boolean isEditing() {
        return mCategoryName != null;
    }

    @Override
    public void takeView(AddEditCategoryContract.View view) {
        mAddEditCategoryview = view;

        if(isEditing() && mAddEditCategoryview != null) {
            Category c = mUser.getCategory(mCategoryName);
            mAddEditCategoryview.populateCategoryData(c.getName(), c.getFlow());
        }
    }

    @Override
    public void dropView() {
        mAddEditCategoryview = null;
    }
}
