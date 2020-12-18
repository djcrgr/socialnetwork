package com.getjavajob.training.karpovn.socialnetwork.service;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Phone;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;
import com.getjavajob.training.karpovn.socialnetwork.dao.ConnectionPool;
import com.getjavajob.training.karpovn.socialnetwork.dao.PhoneDao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AccountService extends AbstractService<Account, Account> {

    private final AccountDao accountDao;
    private final PhoneDao phoneDao;
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final Connection connection;

    public AccountService() throws SQLException, IOException, ClassNotFoundException {
        connection = connectionPool.getThreadConnection();
        this.accountDao = new AccountDao(connection);
        this.phoneDao = new PhoneDao(connection);
    }

    private void freeConnection() {
        connectionPool.free(connection);
    }

    @Override
    public void create(Account account) throws SQLException {
        try {
            accountDao.createAccount(account);
            phoneDao.createAccPhones(account);
            connection.commit();
        } catch (SQLException throwables) {
            connection.rollback();
        } finally {
            freeConnection();
        }
    }

    @Override
    public Account readById(int id) throws SQLException {
        Account account = null;
        try {
            account = accountDao.readAccountById(id);
            account.setPhoneNum(phoneDao.readAccPhones(id));
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            freeConnection();
        }
        return account;
    }

    @Override
    public String getImageFromDb(int id) throws IOException, SQLException {
        String imageStr = null;
        try {
            imageStr = accountDao.getImageFromDb(id);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            freeConnection();
        }
        return imageStr;
    }

    @Override
    public void update(Account account) throws SQLException {
        try {
            accountDao.updateAccount(account);
            phoneDao.updateAccPhones(account);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        } finally {
            freeConnection();
        }
    }

    @Override
    public void delete(Account account) throws SQLException {
        try {
            accountDao.deleteById(account.getId());
            phoneDao.deleteAccPhones(account);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            freeConnection();
        }
    }

    /*@Override
    public void addFriend(Account account, Account friend) {
        accountDao.addFriend(account, friend);
    }

    @Override
    public void removeFriend(Account account, Account friend) {
        accountDao.removeFriend(account, friend);
    }*/

    public List<Account> showWithOffset(int resultOnPage, int countCurrentPage, String searchStr, String searchStrCopy) throws SQLException {
        List<Account> accountList = new ArrayList<>();
        try {
            accountList = accountDao.showAccWithOffset(resultOnPage, countCurrentPage * 5 - 5, searchStr,
                    searchStrCopy);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            freeConnection();
        }
        return accountList;
    }

    @Override
    public void loadPicture(int id, InputStream inputStream) throws SQLException {
        try {
            accountDao.loadPicture(id, inputStream);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            freeConnection();
        }
    }

    /*@Override
    public List<Account> showFriends(Account account) {
        return accountDao.showFriend(account);
    }*/

    @Override
    public List<Account> showAll() throws SQLException {
        List<Account> accountList = null;
        try {
        accountList = accountDao.showAllAccounts();
        connection.commit();
        } catch (SQLException throwables) {
            connection.rollback();
        } finally {
            freeConnection();
        }
        return accountList;
    }

    @Override
    public Account checkExisting(String email, String password) throws SQLException {
        Account account = null;
        try {
            account = accountDao.checkForLogin(email, password);
            account.setPhoneNum(phoneDao.readAccPhones(account.getId()));
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            freeConnection();
        }
        return account;
    }
}