package com.getjavajob.training.karpovn.socialnetwork.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractService<T, S> {

    abstract void create(T t) throws SQLException;

    abstract T readById(int id) throws SQLException, IOException, ClassNotFoundException;

    abstract String getImageFromDb(int id) throws IOException, SQLException;

    abstract void update(T t) throws SQLException;

    abstract void delete(T t) throws SQLException;

    /*abstract void addFriend(T t, T friend);

    abstract void removeFriend(T t, T friend);*/

    abstract void loadPicture(int id, InputStream inputStream) throws SQLException;

    /*bstract List<T> showFriends(T t) ;*/

    abstract List<T> showAll() throws SQLException, IOException, ClassNotFoundException;

    abstract T checkExisting(String email, String password) throws SQLException;
}