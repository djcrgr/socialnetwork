import java.util.List;


public class AccountService implements AbstractService<Account> {

    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void create(Account account) {
        accountDao.createAccount(account);
    }

    @Override
    public void update(Account account) {
        accountDao.updateAccount(account);
    }

    @Override
    public void delete(Account account) {
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
    public List<Account> showFriends(Account account) {
        return accountDao.showFriend(account);
    }

    @Override
    public List<Account> showAll() {
        return accountDao.showAllAccounts();
    }
}