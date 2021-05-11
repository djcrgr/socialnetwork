package com.getjavajob.training.karpovn.socialnetwork.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
@Entity
@Table(name = "phones")
public class Phone implements Serializable {

    @JsonProperty("number")
    @Column(name = "phoneNum")
    private String number;
    @JsonProperty("type")
    @Column(name = "phoneType")
    private String type;


    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Phone(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public Phone(String number, String type, Account account) {
        this.number = number;
        this.type = type;
        this.account = account;
    }
}
