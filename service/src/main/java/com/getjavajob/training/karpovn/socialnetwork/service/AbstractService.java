package com.getjavajob.training.karpovn.socialnetwork.service;

import java.util.List;

public interface AbstractService<T> {

    void create(T t);

    void update(T t);

    void delete(T t);

    void addFriend(T t, T friend);

    void removeFriend(T t, T friend);

    List<T> showFriends(T t);

    List<T> showAll();
}