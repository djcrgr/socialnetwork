package com.getjavajob.training.karpovn.socialnetwork.common;

import java.sql.Date;
import java.util.List;

public class Group {

    private int id;
    private String name;
    private String description;
    private Account owner;
    private List<Account> userList;
    private Date creationDate;

    public Group() {
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", userList=" + userList +
                ", creationDate=" + creationDate +
                '}';
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public List<Account> getUserList() {
        return userList;
    }

    public void setUserList(List<Account> userList) {
        this.userList = userList;
    }
}
