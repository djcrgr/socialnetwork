package com.getjavajob.training.karpovn.socialnetwork.common;

public class Account {

    private int id;
    private String name;
    private String surname;
    private int age;
    private String address;
    private int phoneNum;
    private String password;
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account() {
    }

    public Account(int id, String name, String surname, int age, int phoneNum) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phoneNum = phoneNum;
    }

    public Account(int id, String name, String surname, int age, String address, int phoneNum) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public Account(int id, String name, String surname, int age, String address, int phoneNum, String password, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = address;
        this.phoneNum = phoneNum;
        this.password = password;
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phoneNum=" + phoneNum +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}