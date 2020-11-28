package com.getjavajob.training.karpovn.socialnetwork.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public interface AbstractService<T> {

    void create(T t);

    T readById(int id);

    String getImageFromDb(int id) throws IOException, SQLException;

    void update(T t);

    void delete(T t) throws SQLException;

    void addFriend(T t, T friend);

    void removeFriend(T t, T friend);

    List<T> showWithOffset(int resultOnPage, int countCurrentPage, String searchStr, String searchStrCopy) throws SQLException;

    void loadPicture(int id, InputStream inputStream) throws SQLException;

    List<T> showFriends(T t);

    List<T> showAll();

    T checkExisting(String email, String password) throws SQLException;
}