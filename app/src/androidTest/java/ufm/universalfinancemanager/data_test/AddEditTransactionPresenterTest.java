package ufm.universalfinancemanager.data_test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import ufm.universalfinancemanager.addedittransaction.AddEditTransactionContract;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionPresenter;
import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.TransactionRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.AccountType;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.atomic.Account;
import ufm.universalfinancemanager.support.atomic.Category;
import ufm.universalfinancemanager.support.atomic.User;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by smh7 on 1/10/18.
 */

public class AddEditTransactionPresenterTest {
    @Mock
    private TransactionRepository mTransactionRepository;

    @Mock
    User mUser;

    @Mock
    private AddEditTransactionContract.View mAddEditTaskView;

    @Captor
    private ArgumentCaptor<TransactionDataSource.GetTransactionCallback> mTransactionCallbackCaptor;

    private AddEditTransactionPresenter mPresenter;

    private String TRANSACTION_NAME = "TEST TRANSACTION 1";
    private Flow TRANSACTION_FLOW = Flow.OUTCOME;
    private double TRANSACTION_AMOUNT = 2.00;
    private String TRANSACTION_CATEGORY = "Test Category 1";
    private String TRANSACTION_FROMACCOUNT = "Checking";
    private Date TRANSACTION_DATE = new Date();

    @Before
    public void setupMocksAndView() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // The presenter wont't update the view unless it's active.
        when(mAddEditTaskView.isActive()).thenReturn(true);
    }

    @Test
    public void testGetExistingTaskFromRepository_updatesView() {

    }

    @Test
    public void testEditAmount_updatesAccountBalance() {
    }
}
