package ufm.universalfinancemanager.di;

/**
 * Created by smh7 on 12/11/17.
 */

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.addeditaccount.AddEditAccountActivity;
import ufm.universalfinancemanager.addeditaccount.AddEditAccountModule;
import ufm.universalfinancemanager.addeditbudget.AddEditBudgetActivity;
import ufm.universalfinancemanager.addeditbudget.AddEditBudgetModule;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryActivity;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryModule;
import ufm.universalfinancemanager.addeditreminder.AddEditReminderActivity;
import ufm.universalfinancemanager.addeditreminder.AddEditReminderModule;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionActivity;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionModule;
import ufm.universalfinancemanager.budgetoverview.BudgetActivity;
import ufm.universalfinancemanager.budgetoverview.BudgetModule;
import ufm.universalfinancemanager.earningshistory.EarningsHistoryActivity;
import ufm.universalfinancemanager.earningshistory.EarningsHistoryModule;
import ufm.universalfinancemanager.home.HomeActivity;
import ufm.universalfinancemanager.home.HomeModule;
import ufm.universalfinancemanager.login.LoginActivity;
import ufm.universalfinancemanager.login.LoginModule;
import ufm.universalfinancemanager.networth.NetworthActivity;
import ufm.universalfinancemanager.networth.NetworthModule;
import ufm.universalfinancemanager.reminderhistory.ReminderHistoryActivity;
import ufm.universalfinancemanager.transactionhistory.TransactionHistoryActivity;
import ufm.universalfinancemanager.transactionhistory.TransactionHistoryModule;
import ufm.universalfinancemanager.reminderhistory.ReminderHistoryModule;
/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and be aware of a scope annotation @ActivityScoped
 * When Dagger.Android annotation processor runs it will create 4 subcomponents for us.
 */
@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = TransactionHistoryModule.class)
    abstract TransactionHistoryActivity transactionHistoryActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddEditTransactionModule.class)
    abstract AddEditTransactionActivity addEditTransactionActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddEditAccountModule.class)
    abstract AddEditAccountActivity addEditAccountActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddEditCategoryModule.class)
    abstract AddEditCategoryActivity addEditCategoryActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddEditReminderModule.class)
    abstract AddEditReminderActivity addEditReminderActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddEditBudgetModule.class)
    abstract AddEditBudgetActivity addEditBudgetActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = BudgetModule.class)
    abstract BudgetActivity BudgetActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeActivity homeActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = NetworthModule.class)
    abstract NetworthActivity networthActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = EarningsHistoryModule.class)
    abstract EarningsHistoryActivity earningsHistoryActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ReminderHistoryModule.class)
    abstract ReminderHistoryActivity reminderHistoryActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivity();

}
