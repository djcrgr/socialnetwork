import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    //crud for account

    private static final String SELECT_ALL = "SELECT * FROM account";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE id=?";
    private static final String CREATE_ACCOUNT = "INSERT INTO account (id, name, surname, age, phoneNum, addres) values (?, ? , ?, ?, ?, ?) ";
    private static final String UPDATE_ACCOUNT = "update account set name = ?, surname = ?, age = ?, phoneNum = ?, addres = ? where id = ?";
    private static final String DELETE_BY_ID = "delete from account where id = ?";
    private static final String ADD_FRIEND = "insert into friends (id, friendid)values (? , ?)";
    private static final String REMOVE_FRIEND = "delete from friends where friendid = ? and id = ?";
    private static final String SHOW_FRIENDS = "select * from friends where id = ?";

    private Connection connection;

    public AccountDao(Connection connection) throws SQLException {
        /*ConnectionPool connectionPool = new ConnectionPool("jdbc:mysql://localhost:3306/test", "mysql", "mysql",
                10, 20, true);*/
        this.connection = connection;
    }

    public void addFriend(Account account, Account friend) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(ADD_FRIEND)) {
            preparedStatement.setInt(1, account.getId());
            preparedStatement.setInt(2, friend.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeFriend(Account account, Account friend) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(REMOVE_FRIEND)) {
            preparedStatement.setInt(1, friend.getId());
            preparedStatement.setInt(2, account.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Account> showFriend(Account account) {
        List<Account> friendList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SHOW_FRIENDS)) {
            preparedStatement.setInt(1, account.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    friendList.add(readAccountById(resultSet.getInt("friendid")));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return friendList;
    }

    public void createAccount(Account account) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(CREATE_ACCOUNT)) {
            preparedStatement.setInt(1, account.getId());
            preparedStatement.setString(2, account.getName());
            preparedStatement.setString(3, account.getSurname());
            preparedStatement.setInt(4, account.getAge());
            preparedStatement.setInt(5, account.getPhoneNum());
            preparedStatement.setString(6, account.getAddres());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void updateAccount(Account account) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT)) {
            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getSurname());
            preparedStatement.setInt(3, account.getAge());
            preparedStatement.setInt(4, account.getPhoneNum());
            preparedStatement.setString(5, account.getAddres());
            preparedStatement.setInt(6, account.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try (PreparedStatement prepareStatement = this.connection
                .prepareStatement(DELETE_BY_ID)) {
            prepareStatement.setInt(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public Account readAccountById(int id) {
        try (PreparedStatement prepareStatement = this.connection
                .prepareStatement(SELECT_BY_ID)) {
            prepareStatement.setInt(1, id);
            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createAccountFromResult(resultSet);
                }
            }
            return null;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public List<Account> showAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        try (PreparedStatement prepareStatement = this.connection
                .prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                while (resultSet.next()) {
                    accountList.add(createAccountFromResult(resultSet));
                }
            }
            return accountList;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
    }


    private Account createAccountFromResult(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setName(resultSet.getString("name"));
        account.setSurname(resultSet.getString("surname"));
        account.setAge(resultSet.getInt("age"));
        account.setPhoneNum(resultSet.getInt("phoneNum"));
        account.setAddres(resultSet.getString("addres"));
        return account;
    }
}
