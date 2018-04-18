package ufm.universalfinancemanager.home;

import java.util.Comparator;

import ufm.universalfinancemanager.support.AccountType;

enum AcctType { DEBT, ASSET;}

public class HomeAccountData {


    protected String acctName;
    protected AcctType acctType;
    protected double acctBal;

    HomeAccountData () {};

    HomeAccountData (String acctName, AcctType acctType, double acctBal) {
        this.acctName = acctName;
        this.acctType = acctType;
        this.acctBal = acctBal;
    }

    public static Comparator<HomeAccountData> COMPARE_BY_NAME = new Comparator<HomeAccountData>() {
        public int compare(HomeAccountData one, HomeAccountData other) {
            return one.acctName.compareTo(other.acctName);
        }
    };

}
