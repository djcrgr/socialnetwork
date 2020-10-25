import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AccountServiceTest {


    @Test
    public void create() {
        AccountDao accountDao = mock(AccountDao.class);
        Account account = new Account(1, "name", "surname", 21, 1221);
        AccountService accountService = new AccountService(accountDao);
        accountService.create(account);
        verify(accountDao).createAccount(account);
    }

    @Test
    public void update() {
        AccountDao accountDao = mock(AccountDao.class);
        AccountService accountService = new AccountService(accountDao);
        Account secondAcc = new Account(1, "name2", "surname2", 21, 1221);
        accountService.update(secondAcc);
        verify(accountDao).updateAccount(any());
    }

    @Test
    public void delete() {
        AccountDao accountDao = mock(AccountDao.class);
        AccountService accountService = new AccountService(accountDao);
        Account secondAcc = new Account(1, "name2", "surname2", 21, 1221);
        accountService.delete(secondAcc);
        verify(accountDao).deleteById(secondAcc.getId());
    }

    @Test
    public void addFriend() {
        AccountDao accountDao = mock(AccountDao.class);
        AccountService accountService = new AccountService(accountDao);
        Account secondAcc = new Account(1, "name2", "surname2", 21, 1221);
        Account secondAccFriend = new Account(2, "name", "surname", 21, 122122);
        accountService.addFriend(secondAcc, secondAccFriend);
        verify(accountDao).addFriend(secondAcc, secondAccFriend);
    }

    @Test
    public void removeFriend() {
        AccountDao accountDao = mock(AccountDao.class);
        AccountService accountService = new AccountService(accountDao);
        Account secondAcc = new Account(1, "name2", "surname2", 21, 1221);
        Account secondAccFriend = new Account(2, "name", "surname", 21, 122122);
        accountService.removeFriend(secondAcc, secondAccFriend);
        verify(accountDao).removeFriend(secondAcc, secondAccFriend);
    }

    @Test
    public void showFriends() {
        AccountDao accountDao = mock(AccountDao.class);
        AccountService accountService = new AccountService(accountDao);
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(1, "name2", "surname2", 21, 1221));
        Account secondAccFriend = new Account(2, "name", "surname", 21, 122122);
        when(accountDao.showFriend(secondAccFriend)).thenReturn(accountList);
        assertArrayEquals(accountList.toArray(), accountService.showFriends(secondAccFriend).toArray());
    }

    @Test
    public void showAll() {
        AccountDao accountDao = mock(AccountDao.class);
        AccountService accountService = new AccountService(accountDao);
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(1, "name2", "surname2", 21, 1221));
        when(accountDao.showAllAccounts()).thenReturn(accountList);
        assertEquals(accountList.get(0).getName(), accountService.showAll().get(0).getName());
    }
}