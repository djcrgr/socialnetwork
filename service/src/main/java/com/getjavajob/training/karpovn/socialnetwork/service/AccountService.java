package com.getjavajob.training.karpovn.socialnetwork.service;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;
import com.getjavajob.training.karpovn.socialnetwork.dao.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

@Service
public class AccountService extends AbstractService<Account, Account> {

	private final AccountDao accountDao;
	private final PhoneDao phoneDao;

	@Autowired
	public AccountService(AccountDao accountDao, PhoneDao phoneDao) {
		this.accountDao = accountDao;
		this.phoneDao = phoneDao;
	}

	@Override
	public void create(Account account) {
		accountDao.createAccount(account);
		phoneDao.createAccPhones(account);
	}

	@Override
	public Account readById(int id) {
		Account account = accountDao.readAccountById(id);
		account.setPhoneNum(phoneDao.readAccPhones(id));
		return account;
	}

	@Override
	public String getImageFromDb(int id) throws IOException, SQLException {
		return accountDao.getImageFromDb(id);
	}

	@Override
	public void update(Account account) throws SQLException {
		accountDao.updateAccount(account);
		phoneDao.updateAccPhones(account);
	}

	@Override
	public void delete(Account account) throws SQLException {
		accountDao.deleteById(account.getId());
		phoneDao.deleteAccPhones(account);
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
		return accountDao.showAccWithOffset(resultOnPage, countCurrentPage * 5 - 5, searchStr,
				searchStrCopy);
	}

	@Override
	public void loadPicture(int id, InputStream inputStream) throws SQLException {
		accountDao.loadPicture(id, inputStream);
	}

    /*@Override
    public List<Account> showFriends(Account account) {
        return accountDao.showFriend(account);
    }*/

	@Override
	public List<Account> showAll() {
		return accountDao.showAllAccounts();
	}

	@Override
	public Account checkExisting(String email, String password) throws SQLException {
		Account account = accountDao.checkForLogin(email, password);
		account.setPhoneNum(phoneDao.readAccPhones(account.getId()));
		return account;
	}
}