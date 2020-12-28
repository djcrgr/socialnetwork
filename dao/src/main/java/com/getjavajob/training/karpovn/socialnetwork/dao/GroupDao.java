package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;

import javax.print.attribute.standard.DateTimeAtCreation;
import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Repository
@ContextConfiguration(locations = {"classpath:applicationContextDao.xml"})
public class GroupDao {

    private static final String SELECT_ALL = "SELECT * FROM mgroup";
    private static final String SELECT_ALL_GROUP_ID = "select id from mgroup";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE id=?";
    private static final String CREATE_GROUP = "INSERT INTO mgroup (id, name, description, ownerId, creationDate) values (?, ?, " +
            "? , ?, ?)";
    private static final String UPDATE_GROUP = "update mgroup set name = ?, description = ?, ownerId = ? where id = ?";
    private static final String DELETE_BY_ID = "delete from mgroup where id = ?";
    private static final String UPDATE_GROUP_PHOTO = "update mgroup set picture = ? where id = ?";
    private static final String GET_PHOTO = "select picture from mgroup where id = ?";
    private static final String GET_ACC_ID_FROM_GROUP_WITH_ID = "select userId from groupusers where groupId = ?";
    private static final String GET_ACC_ID_FROM_GROUP = "select userId from groupusers";
    private static final String SHOW_ALL_GROUPS_ID = "select id from mgroup";
    private static final String ADD_USER_TO_GROUP = "insert into groupusers (userId, groupId) values (? , ?)";
    private static final String DELETE_USER_FROM_GROUP = "delete from groupusers where userId = ? and groupId = ?";
    private static final String QUERY_FOR_OFFSET =
            "select * from mgroup where name like CONCAT( '%',?,'%') limit ? " + "offset ?";

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final AccountDao accountDao;

    @Autowired
    public GroupDao(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = new AccountDao(dataSource, jdbcTemplate);
    }

    public void createGroup(Group group) {
        this.jdbcTemplate.update(CREATE_GROUP, group.getId(), group.getName(), group.getDescription(),
                group.getOwner().getId(), new Date(System.currentTimeMillis()));
    }

    public Group readGroupById(int id) {
        return this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id},
                (resultSet, i) -> createGroupFromResult(resultSet));
    }

    public void updateGroup(Group group) {
        this.jdbcTemplate.update(UPDATE_GROUP, group.getName(), group.getDescription(), group.getOwner().getId(),
                group.getId());
    }

    public void deleteGroupById(int id) {
        this.jdbcTemplate.update(DELETE_BY_ID, id);
    }

    public String getImageFromDb(int groupId) {
        Blob result;
        result = this.jdbcTemplate.queryForObject(GET_PHOTO, Blob.class, groupId);
        if (result == null) {
            return getImageFromDb(1);
        } else {
            return getString(result);
        }
    }

    public List<Account> showAllUsers() {
        return this.jdbcTemplate.query(GET_ACC_ID_FROM_GROUP,
                ((resultSet, i) -> accountDao.readAccountById(resultSet.getInt("userId"))));
    }

    public void addUserToGroup(Account account, Group group) {
        this.jdbcTemplate.update(ADD_USER_TO_GROUP, account.getId(), group.getId());
    }

    public void removeUserFromGroup(Account account, Group group) {
        this.jdbcTemplate.update(DELETE_USER_FROM_GROUP, account.getId(), group.getId());
    }

    public List<Group> showAllGroups() {
        return this.jdbcTemplate.query(SELECT_ALL_GROUP_ID,
                ((resultSet, i) -> readGroupById(resultSet.getInt("id"))));
    }

    public List<Group> showGroupWithOffset(int limit, int offset, String subStringName) {
        List<Group> groupList  = new ArrayList<>();
        SqlRowSet rowSet = this.jdbcTemplate.queryForRowSet(QUERY_FOR_OFFSET, new Object[] {subStringName, limit,
                offset}, new int[] {Types.VARCHAR, Types.INTEGER, Types.INTEGER});
        while (rowSet.next()) {
            groupList.add(createGroupFromRowSet(rowSet));
        }
        return groupList;
    }

    public void loadPicture(int groupId, InputStream inputStream) {
        this.jdbcTemplate.update(UPDATE_GROUP_PHOTO, inputStream, groupId);
    }

    private Group createGroupFromResult(ResultSet resultSet) throws SQLException{
        Group group = new Group();
        AccountDao accountDao = new AccountDao(dataSource, jdbcTemplate);
        group.setId(resultSet.getInt("id"));
        group.setName(resultSet.getString("name"));
        group.setDescription(resultSet.getString("description"));
        group.setOwner(accountDao.readAccountById(resultSet.getInt("ownerId")));
        group.setCreationDate(resultSet.getDate("creationDate"));
        return group;
    }

    private Group createGroupFromRowSet(SqlRowSet rowSet) {
        Group group = new Group();
        AccountDao accountDao = new AccountDao(dataSource, jdbcTemplate);
        group.setId(rowSet.getInt("id"));
        group.setName(rowSet.getString("name"));
        group.setDescription(rowSet.getString("description"));
        group.setOwner(accountDao.readAccountById(rowSet.getInt("ownerId")));
        group.setCreationDate(rowSet.getDate("creationDate"));
        return group;
    }

    private String getString(Blob blob) {
        InputStream inputStream = null;
        try {
            inputStream = blob.getBinaryStream();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while (true) {
            try {
                if (!((bytesRead = inputStream.read(buffer)) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
