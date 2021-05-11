package com.getjavajob.training.karpovn.socialnetwork.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Repository
@Entity
@Table(name = "account")
public class Account implements Serializable {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("address")
    private String address;

    @OneToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @ToString.Exclude
    private List<Phone> phoneNum;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    private byte[] photo;

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