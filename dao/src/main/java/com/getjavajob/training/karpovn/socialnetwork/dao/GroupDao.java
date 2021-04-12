package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import com.getjavajob.training.karpovn.socialnetwork.common.GroupUser;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.*;
import javax.print.attribute.standard.DateTimeAtCreation;
import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Queue;

@Repository
public class GroupDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void createGroup(Group group) {
        entityManager.persist(group);
    }

    public Group readGroupById(int id) {
       return entityManager.find(Group.class, id);
    }

    public void updateGroup(Group group) {
        entityManager.merge(group);
    }

    public void deleteGroupById(int id) {
        entityManager.remove(readGroupById(id));
    }

    public String getImageFromDb(int groupId) {
        Query query = entityManager.createQuery("Select gr.picture FROM Group gr where gr.id = ?1 ");
        query.setParameter(1, groupId);
        return query.getSingleResult().toString();
    }

    public List<Account> showAllUsers() {
        String qlString = "SELECT users.account from GroupUser users ";
        TypedQuery<Account> query = entityManager.createQuery(qlString, Account.class);
        return query.getResultList();
    }

    public void addUserToGroup(Account account, Group group) {
        GroupUser groupUser = new GroupUser();
        groupUser.setAccount(account);
        List<Group> groups = groupUser.getGroups();
        groups.add(group);
        groupUser.setGroups(groups);
        entityManager.persist(groupUser);
    }

    public void removeUserFromGroup(Account account, Group group) {
        Query query = entityManager.createQuery("select gr.account from GroupUser gr join Group where Group.id = ?1 " +
                "and gr.account.id = ?2");
        query.setParameter(1, group.getId());
        query.setParameter(2, account.getId());
        entityManager.remove(query.getResultList());
    }

    public List<Group> showAllGroups() {
        Query query = entityManager.createQuery("select groups from Group groups");
        return query.getResultList();
    }

    public void loadPicture(int groupId, InputStream inputStream) throws IOException {
        Group group = readGroupById(groupId);
        if (inputStream != null) {
            group.setPicture(IOUtils.toByteArray(inputStream));
            entityManager.merge(group);
        }
    }
}
