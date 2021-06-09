package com.getjavajob.training.karpovn.socialnetwork.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public interface AbstractService<T> {

    void create(T t) throws SQLException;

    T readById(int id) throws SQLException, IOException, ClassNotFoundException;

    String getImageFromDb(int id) throws IOException, SQLException;

    void update(T t) throws SQLException;

    void delete(T t) throws SQLException;

    /*abstract void addFriend(T t, T friend);

    abstract void removeFriend(T t, T friend);*/

    void loadPicture(int id, InputStream inputStream) throws SQLException, IOException;

    /*bstract List<T> showFriends(T t) ;*/

    List<T> showAll() throws SQLException, IOException, ClassNotFoundException;

    T checkExisting(String email, String password) throws SQLException;
}