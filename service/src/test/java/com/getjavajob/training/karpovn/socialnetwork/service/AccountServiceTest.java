/*
package com.getjavajob.training.karpovn.socialnetwork.service;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AccountServiceTest {


    private AccountDao accountDao;
    private Connection connection;
    private AccountService accountService;

    @Before
    public void setUp() throws SQLException, IOException, ClassNotFoundException {
        this.connection = mock(Connection.class);
        this.accountDao = new AccountDao(connection);
        this.accountService = new AccountService();
    }

    @Test
    public void create() throws SQLException, IOException, ClassNotFoundException {
        Account account = new Account(1, "name", "surname", 21);
        accountService.create(account);
        verify(accountDao).createAccount(account);
    }

    @Test
    public void update() throws SQLException, IOException, ClassNotFoundException {
        Account secondAcc = new Account(1, "name2", "surname2", 21);
        accountService.update(secondAcc);
        verify(accountDao).updateAccount(any());
    }

    @Test
    public void delete() throws SQLException, IOException, ClassNotFoundException {
        Account secondAcc = new Account(1, "name2", "surname2", 21);
        accountService.delete(secondAcc);
        verify(accountDao).deleteById(secondAcc.getId());
    }

    */
/*@Test
    public void addFriend() throws SQLException, IOException, ClassNotFoundException {
        Account secondAcc = new Account(1, "name2", "surname2", 21);
        Account secondAccFriend = new Account(2, "name", "surname", 21);
        accountService.addFriend(secondAcc, secondAccFriend);
        verify(accountDao).addFriend(secondAcc, secondAccFriend);
    }

    @Test
    public void removeFriend() throws SQLException, IOException, ClassNotFoundException {
        Account secondAcc = new Account(1, "name2", "surname2", 21);
        Account secondAccFriend = new Account(2, "name", "surname", 21);
        accountService.removeFriend(secondAcc, secondAccFriend);
        verify(accountDao).removeFriend(secondAcc, secondAccFriend);
    }*//*


    */
/*@Test
    public void showFriends() throws SQLException, IOException, ClassNotFoundException {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(1, "name2", "surname2", 21));
        Account secondAccFriend = new Account(2, "name", "surname", 21);
        when(accountDao.showFriend(secondAccFriend)).thenReturn(accountList);
        assertArrayEquals(accountList.toArray(), accountService.showFriends(secondAccFriend).toArray());
    }*//*


    @Test
    public void showAll() throws SQLException, IOException, ClassNotFoundException {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(1, "name2", "surname2", 21));
        when(accountDao.showAllAccounts()).thenReturn(accountList);
        assertEquals(accountList.get(0).getName(), accountService.showAll().get(0).getName());
    }
}*/
