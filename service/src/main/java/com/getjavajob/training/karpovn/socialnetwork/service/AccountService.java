package com.getjavajob.training.karpovn.socialnetwork.service;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;


public class AccountService implements AbstractService<Account> {

    private AccountDao accountDao;

    public AccountService() throws SQLException, IOException, ClassNotFoundException {
        this.accountDao = new AccountDao();
    }

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void create(Account account) {
        accountDao.createAccount(account);
    }

    @Override
    public Account readById(int id) {
        return accountDao.readAccountById(id);
    }

    @Override
    public String getImageFromDb(int id) throws IOException, SQLException {
        return accountDao.getImageFromDb(id);
    }

    @Override
    public void update(Account account) {
        try {
            accountDao.updateAccount(account);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Account account) throws SQLException {
        accountDao.deleteById(account.getId());
    }

    @Override
    public void addFriend(Account account, Account friend) {
        accountDao.addFriend(account, friend);
    }

    @Override
    public void removeFriend(Account account, Account friend) {
        accountDao.removeFriend(account, friend);
    }

    @Override
    public List<Account> showWithOffset(int resultOnPage, int countCurrentPage, String searchStr, String searchStrCopy) throws SQLException {
        return accountDao.showAccWithOffset(resultOnPage, countCurrentPage * 5 - 5, searchStr,
                searchStrCopy);
    }

    @Override
    public void loadPicture(int id, InputStream inputStream) throws SQLException {
        accountDao.loadPicture(id, inputStream);
    }

    @Override
    public List<Account> showFriends(Account account) {
        return accountDao.showFriend(account);
    }

    @Override
    public List<Account> showAll() {
        return accountDao.showAllAccounts();
    }

    @Override
    public Account checkExisting(String email, String password) throws SQLException {
        return accountDao.checkForLogin(email, password);
    }
}