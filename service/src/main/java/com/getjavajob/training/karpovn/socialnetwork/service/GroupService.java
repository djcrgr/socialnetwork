package com.getjavajob.training.karpovn.socialnetwork.service;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import com.getjavajob.training.karpovn.socialnetwork.dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

@Service
public class GroupService extends AbstractService<Group, Account>{

    private final GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao){
        this.groupDao = groupDao;
    }

    @Override
    public void create(Group group){
        groupDao.createGroup(group);

    }

    @Override
    public Group readById(int id) {
        return groupDao.readGroupById(id);
    }

    @Override
    public String getImageFromDb(int id) {
        return groupDao.getImageFromDb(id);
    }

    @Override
    public void update(Group group){
        groupDao.updateGroup(group);
    }

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

    public List<Group> showWithOffset(int resultOnPage, int countCurrentPage, String searchStr) {
        return groupDao.showGroupWithOffset(resultOnPage, countCurrentPage * 5 - 5, searchStr);
    }

    @Override
    public void loadPicture(int id, InputStream inputStream) throws SQLException {
        groupDao.loadPicture(id, inputStream);
    }

    /*@Override
    List<Group> showFriends(Group group) {
        throw new UnsupportedOperationException();
    }*/

    @Override
    public List<Group> showAll() {
        return groupDao.showAllGroups();
    }

    @Override
    Group checkExisting(String email, String password) {
        throw new UnsupportedOperationException();
    }
}