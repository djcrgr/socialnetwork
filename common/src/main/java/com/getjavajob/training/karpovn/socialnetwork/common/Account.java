package com.getjavajob.training.karpovn.socialnetwork.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Data
@Repository
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private int id;
    @Column(name = "name")
    @JsonProperty("name")
    private String name;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("age")
    private int age;
    @JsonProperty("address")
    private String address;
    @JsonProperty("phoneNum")
    private List<Phone> phoneNum;
    @JsonProperty("password")
    private String password;
    @JsonProperty("email")
    private String email;

    public Account() {
    }

    public Account(int id, String name, String surname, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Account(int id, String name, String surname, int age, List<Phone> phoneNum) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phoneNum = phoneNum;
    }

    public Account(int id, String name, String surname, int age, String address, List<Phone> phoneNum) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public Account(int id, String name, String surname, int age, String address, List<Phone> phoneNum, String password,
                   String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = address;
        this.phoneNum = phoneNum;
        this.password = password;
        this.email = email;
    }

}