package com.getjavajob.training.karpovn.socialnetwork.service;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import com.getjavajob.training.karpovn.socialnetwork.dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

@Service
public class GroupService extends AbstractService<Group>{

    private final GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao){
        this.groupDao = groupDao;
    }

    @Transactional
    @Override
    public void create(Group group){
        groupDao.createGroup(group);
    }

    @Transactional
    @Override
    public Group readById(int id) throws SQLException, ClassNotFoundException {
        return groupDao.readGroupById(id);
    }

    @Transactional
    @Override
    public String getImageFromDb(int id) throws SQLException{
        return groupDao.getImageFromDb(id);
    }

    @Transactional
    @Override
    public void update(Group group){
        groupDao.updateGroup(group);
    }

    @Transactional
    @Override
    public void delete(Group group){
        groupDao.deleteGroupById(group.getId());
    }

   /* @Override
    void addFriend(Group group, Group friend) {
        throw new UnsupportedOperationException();
    }

    @Override
    void removeFriend(Group group, Group friend) {
        throw new UnsupportedOperationException();
    }*/

  /*  public List<Group> showWithOffset(int resultOnPage, int countCurrentPage, String searchStr) {
        return groupDao.showGroupWithOffset(resultOnPage, countCurrentPage * 5 - 5, searchStr);
    }*/

    @Transactional
    @Override
    public void loadPicture(int id, InputStream inputStream) throws SQLException, IOException {
        groupDao.loadPicture(id, inputStream);
    }

    /*@Override
    List<Group> showFriends(Group group) {
        throw new UnsupportedOperationException();
    }*/

    @Transactional
    @Override
    public List<Group> showAll() throws SQLException, ClassNotFoundException {
        return groupDao.showAllGroups();
    }

    @Transactional
    @Override
    Group checkExisting(String email, String password) {
        throw new UnsupportedOperationException();
    }
}